package agenda;

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
     
    public void guardarDatos() throws IOException{
        String sFichero = "Agenda.txt";
        File fichero = new File(sFichero);
        BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));  
        for(Contacto con:lista_contactos){
            bw.write("- "+con.getNombre()+"\r\n");
            for(Integer tel:con.getTelefono())
                bw.write(tel+"\r\n");
            bw.write("----\r\n");
        }
       bw.close();
    }
    
    public void cargarDatos(String ruta) throws FileNotFoundException {
        List<Contacto> con = new ArrayList<>();
       
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
                    }
                   else {
                      Integer telefono = Integer.parseInt(linea);
                      this.Anadir(nombre, telefono);
                   }
                }
            }
            fr.close();
        }
        catch(Exception e) {
            System.out.println("Excepcion leyendo fichero "+ ruta + ": " + e);
        }
  
    }

    

    public boolean topeMinimo(int telefono){
        String valor=Integer.toString(telefono);
        if(valor.length()>=3){
            return true;
        }
        return false;
    }
    
    public boolean topeMaximo(int telefono){
        String valor=Integer.toString(telefono);
        if(valor.length()<=10){
            return true;
        }
        return false;
    }
    public boolean Consultar(String nombre, int telefono) {
        for (Contacto con:lista_contactos) {

            if (nombre.equals(con.getNombre())) {
                for(Integer tel:con.getTelefono()){
                    if(tel==telefono){
                       System.out.println("Ya existe un contacto con ese nombre");
                       return true;
                    }
                   
                }
                 return false;
                
            }
        }
        return false;
    }

    public void Anadir(String nombre, int telefono) throws IOException {
        if(topeMinimo(telefono)&&topeMaximo(telefono)){
        
            if(!this.Consultar(nombre, telefono)){
                if(nombre.equals("")){
                    nombre=Integer.toString(telefono);
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
             this.guardarDatos();
            }

        } else{
          System.out.println("El telefono no es valido");
        }
    }

    public boolean Buscar(String nombre) {
        boolean encontrado = false;

        for (Contacto con: this.lista_contactos){
            if (nombre.equalsIgnoreCase(con.getNombre())) {
                System.out.println(con.getNombre() + "-" + "Tf:" + con.getTelefono());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Contacto inexistente");
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
                System.out.println(con.getNombre() + "-" + "Tfs:");
                con.MostrarTel();
            }
        }
    }

    public List<Contacto> getListaContactos(){
        return this.lista_contactos;
    }
    public void Vaciar() {
        try {
            System.out.println("Se eliminarán todos los contactos");
            System.out.println("¿Estas seguro (S/N)?");
            String respuesta;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            respuesta = teclado.readLine();
            respuesta = respuesta.toUpperCase();
            if (respuesta.equals("S")) {

                //Lo hago por mera formalidad porque java se encarga de liberar los objetos no referenciados creados. El garbage collector
                this.lista_contactos.removeAll(lista_contactos);
                contador_contactos = 0;
                System.out.println("Agenda vaciada correctamente");
            } else {
                System.out.println("Acción cancelada");
            }
        } catch (IOException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Eliminar() {
        try {
            boolean encontrado = false;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Nombre de contacto a eliminar:");
            String eliminar = teclado.readLine().toUpperCase();
            if (contador_contactos == 0) {
                System.out.println("No hay contactos");
            } else {
                    int i=0;
                for(Contacto con:this.lista_contactos){
                    i+=1;
                    if (eliminar.equals(con.getNombre())) {
                        System.out.println(con.getNombre() + "-" + "Tf:" + con.getTelefono());
                        encontrado = true;
                    }
                }
                if (encontrado) {
 
                    System.out.println("¿Estas seguro (S/N)?");
                    String respuesta;
                    respuesta = teclado.readLine();
                    respuesta = respuesta.toUpperCase();
                    if (respuesta.equals("S")) {
                        
                       lista_contactos.remove(i);
                       this.contador_contactos--;
                       System.out.println("Contacto eliminado correctamente");
                       

                    }

                } else {
                    System.out.println("Lo siento, No se ha encontrado el nombre");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Modificar() {
        try {
            boolean encontrado = false;
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Nombre de contacto a modificar:");
            String eliminar = teclado.readLine().toUpperCase();
            if (contador_contactos == 0) {
                System.out.println("No hay contactos");
            } else {
                int i=0;
                for (Contacto con:lista_contactos) {
                    i+=1;
                    if (eliminar.equals(con.getNombre())) {
                        System.out.println(con.getNombre() + "-" + "Tf:");
                        con.MostrarTel();
                        
                        encontrado = true;
                        
                    }
                }
                if (encontrado) {
                    System.out.println("Introduce nombre:");
                    String nombre_nuevo = teclado.readLine(); 
                    System.out.println("Introduce telefono a modificar:");
                    int telefono = Integer.parseInt(teclado.readLine());
                    System.out.println("Introduce teléfono, formato numerico:");
                    int telefono_nuevo = Integer.parseInt(teclado.readLine());
                    this.lista_contactos.get(i).set_nombre(nombre_nuevo);
                    this.lista_contactos.get(i).set_telefono(telefono,telefono_nuevo);
                    
                } else {
                    System.out.println("No hay contactos con ese nombre");
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}