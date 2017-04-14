package agenda;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 
 * @author Oscar de la Cuesta Campillo. www.palentino.es
 */
public class Agenda {

    List<Contacto> lista_contactos;
    private int contador_contactos; // Contador de objetos creados. Variable muy importante.
    
    public Agenda(String ruta) throws FileNotFoundException{
       lista_contactos=new ArrayList<>();
       contador_contactos=0;
       this.cargarDatos(ruta);
    }
     public Agenda() {
         lista_contactos=new ArrayList<>();
         contador_contactos=0;
 
    }
    public void exportar(File fich) throws IOException{
         BufferedWriter bw = new BufferedWriter(new FileWriter(fich+".csv"));  
        for(Contacto con:lista_contactos){
            bw.write(con.getNombre()+",");
            for(String tel:con.getTelefono())
                bw.write(tel+",");
            bw.write("\r\n");
        }
       bw.close();
    }
    
    public boolean importar(File fich) throws IOException{
       
        try {
            FileReader fr = new FileReader(fich);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while((linea = br.readLine()) != null){
                String[] trozos=linea.split(",");
                for(int i=1;i<trozos.length;i++)
                    this.Anadir(trozos[0],trozos[i]);              
            }
            fr.close();
        }
        catch(Exception e) {
            return false;
        }
  return true;
        
    }
        
    
    public void guardarDatos() throws IOException{
        String sFichero = "Agenda.txt";
        File fichero = new File(sFichero);
        BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));  
        for(Contacto con:lista_contactos){
            bw.write("- "+con.getNombre()+"\r\n");
            for(String tel:con.getTelefono())
                bw.write(tel+"\r\n");
            bw.write("----\r\n");
        }
       bw.close();
    }
    
    public void cargarDatos(String ruta) throws FileNotFoundException {
        try {
            FileReader fr = new FileReader(ruta);
            BufferedReader br = new BufferedReader(fr);
            String nombre = null;
            String linea;
            while((linea = br.readLine()) != null){
                if(!linea.equals("----")){
                   if((linea.charAt(0)=='-')&&(linea.charAt(1)==' ')){
                       String [] trozo=linea.split("- ");
                       nombre = trozo[1];
                       this.AñadirNombre(nombre);
                    }
                   else {
                      this.Anadir(nombre, linea);
                   }
                }
            }
            fr.close();
        }
        catch(Exception e) {
            System.out.println("Excepcion leyendo fichero "+ ruta + ": " + e);
        }
  
    }

    public Contacto buscarContacto(String nombre){
       
        for(int i=0;i<lista_contactos.size();i++){
            if(lista_contactos.get(i).getNombre().equalsIgnoreCase(nombre))
                return lista_contactos.get(i);
        }
        return null;
    }

    
    public void eliminarTelefono(String nombre,String telefono){
        Contacto con=buscarContacto(nombre);
        con.eliminarTelefono(telefono);
                
    }
    
    private boolean topeMinimo(String telefono){
       if(telefono.length()>=3){
            return true;
        }
        return false;
    }
    
  
    public boolean Consultar(String nombre, String telefono) {
        for (Contacto con:lista_contactos) {

            if (nombre.equals(con.getNombre())) {
                for(String tel:con.getTelefono()){
                    if(tel.equals(telefono)){
                       //System.out.println("Ya existe un contacto con ese nombre");
                       return true;
                    }
                   
                }
                 return false;
                
            }
        }
        return false;
    }
    
    public void AñadirNombre(String nombre){
        Contacto con = new Contacto(nombre);
        this.lista_contactos.add(con);
        this.contador_contactos++;
        Ordenar();
        
    }

    public void Anadir(String nombre, String telefono) throws IOException, NumberParseException {
         PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
         Phonenumber.PhoneNumber number = phoneUtil.parse(telefono, "ES");
      
        if(topeMinimo(telefono)){
        
            if(!this.Consultar(nombre, telefono)){
                if(nombre.equals("")){
                    nombre=telefono;
                }
                if(this.Buscar(nombre)){
                  for(Contacto cont:lista_contactos){
                    if(cont.getNombre().equalsIgnoreCase(nombre)){
                       cont.aniadirTelefono(telefono);
                       break;
                      }
                }


            }
                else {
                Contacto con = new Contacto(nombre,telefono);
                this.lista_contactos.add(con);
                this.contador_contactos++;
                Ordenar();
                }
           
            }

        } else if(phoneUtil.isValidNumber(number)){
            String num= phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            String [] trozos=num.split(" ");
            num="";
            for(int i=0;i<trozos.length;i++)
               num+=trozos[i]; 
            
            if(!this.Consultar(nombre, num)){
                if(nombre.equals("")){
                    nombre=num;
                }
                if(this.Buscar(nombre)){
                  for(Contacto cont:lista_contactos){
                    if(cont.getNombre().equalsIgnoreCase(nombre)){
                       cont.aniadirTelefono(num);
                       break;
                      }
                }


            }
                else {
                Contacto con = new Contacto(nombre,num);
                this.lista_contactos.add(con);
                this.contador_contactos++;
                Ordenar();
                }
           
            }
            
            
            
        } else{
         // System.out.println("El telefono no es valido");
        }
    }

    public boolean Buscar(String nombre) {
        boolean encontrado = false;

        for (Contacto con: this.lista_contactos){
            if (nombre.equalsIgnoreCase(con.getNombre())) {
                //System.out.println(con.getNombre() + "-" + "Tf:" + con.getTelefono());
                encontrado = true;
            }
        }
        if (!encontrado) {
            //System.out.println("Contacto inexistente");
        }
        return encontrado;
    }

    public void Ordenar() {
        
       
        Collections.sort(lista_contactos,new Comparator(){

            @Override
            public int compare(Object o1, Object o2) {
                Contacto con1 = (Contacto) o1;
                Contacto con2 = (Contacto) o2;
                return con1.getNombre().compareTo(con2.getNombre());
                }
      
    });
    }

    public void Mostrar() {
        if (this.contador_contactos == 0) {
           System.out.println("No hay contactos");
        } else {
            for(Contacto con:this.lista_contactos) {
                // Es necesario por precaución usar el this para el metodo, puesto que si se ejecuta muchas veces la referencias a memoria pueden fallar.
                //System.out.println(con.getNombre() + "-" + "Tfs:");
                con.MostrarTel();
            }
        }
    }

    public List<Contacto> getListaContactos(){
        return this.lista_contactos;
    }
    public void Vaciar() {
        //Lo hago por mera formalidad porque java se encarga de liberar los objetos no referenciados creados. El garbage collector
        this.lista_contactos.removeAll(lista_contactos);
        contador_contactos = 0;
        //System.out.println("Agenda vaciada correctamente");

    }


    public void EliminarContacto(String nombre) {
        boolean encontrado = false;
        
        if (contador_contactos == 0) {
            //System.out.println("No hay contactos");
        } else {
            int i=0;
            for(Contacto con:this.lista_contactos){
                
                if (nombre.equals(con.getNombre())) {
                   // System.out.println(con.getNombre() + "-" + "Tf:" + con.getTelefono());
                    encontrado = true;
                    break;
                }
                i+=1;
            }
            if (encontrado) {
                
                
                lista_contactos.remove(i);
                this.contador_contactos--;
                //System.out.println("Contacto eliminado correctamente");
                
                
                
                
            } else {
                //System.out.println("Lo siento, No se ha encontrado el nombre");
            }
        }
    }

    
  
    public Contacto Modificar(String nombreOrigen,String telefonoOrigen, String telefono) {
        boolean encontrado = false;
       
        if (contador_contactos == 0) {
           // System.out.println("No hay contactos");
        } else {
            int i=0;
            for (Contacto con:lista_contactos) {
                
                if (nombreOrigen.equals(con.getNombre())) {
                   // System.out.println(con.getNombre() + "-" + "Tf:");
                    con.MostrarTel();
                    
                    encontrado = true;
                    break;
                    
                }
                i+=1;
            }
            if (encontrado) {
                if(telefonoOrigen.equals(nombreOrigen)){
                    this.lista_contactos.get(i).set_nombre(telefono);
                }
                
                this.lista_contactos.get(i).set_telefono(telefonoOrigen,telefono);
                return this.lista_contactos.get(i);
                
            } else {
               // System.out.println("No hay contactos con ese nombre");
            }
            
        }
        return null;
    }
    
    public String quitarPrefijo(String tel){
        String tele="";
        if(tel.charAt(0)=='+'&&tel.charAt(1)=='3'&&tel.charAt(2)=='4' ){
            for(int i=3;i<tel.length();i++)
                tele+=tel.charAt(i);
        }
        return tele;
    }

    public void cargarDatos(File abre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}