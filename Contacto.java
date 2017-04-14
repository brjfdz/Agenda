package agenda;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Oscar de la Cuesta - www.palentino.es
 */
public class Contacto {
    private String nombre;
    private List<String> telefono=new ArrayList<>();

    public Contacto()
    {
    this.nombre=null;
    }
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono.add(telefono);
    }

    Contacto(String nombre) {
       this.nombre=nombre;
    }
    public void set_nombre(String nomb){        
        this.nombre=nomb;
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<String> getTelefono() {
        return telefono;
    }
    
    public void set_telefono(String tl, String nuevo){
        int i=0;
        for(String tle:telefono){
            if(tle.equals(tl)){
              telefono.set(i, nuevo);
              break;
            }
            i+=1;
            
        }
      
    }
    
    public int buscarTelefono(String tel){
        for(int i=0;i<=telefono.size();i++ ){
            if(telefono.get(i).equals(tel))
                return i;
        }
       return -1;
    }
    
    public void eliminarTelefono(String tel){
        int val=buscarTelefono(tel);
        if(val!=-1)
            telefono.remove(val);
    }
    
    public void aniadirTelefono(String tel){
        if(!telefono.contains(tel))
            telefono.add(tel);
    }
    public void MostrarTel(){
        for(String tl:telefono){
            System.out.println(tl);
        }
        
    }
    

    
}