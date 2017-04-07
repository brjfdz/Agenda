package agenda;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar de la Cuesta - www.palentino.es
 */
public class Contacto {
    private String nombre;
    private List<Integer> telefono=new ArrayList<>();

    public Contacto()
    {
    this.nombre=null;
    }
    public Contacto(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefono.add(telefono);
    }
    public void set_nombre(String nomb){        
        this.nombre=nomb.toUpperCase();
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<Integer> getTelefono() {
        return telefono;
    }
    
    public void set_telefono(int tl, int nuevo){
        for(Integer tle:telefono){
            if(tle==tl)
                tle=nuevo;
                break;
            
        }
      
    }
    
    public void aniadirTelefono(int tel){
        if(!telefono.contains(tel))
            telefono.add(tel);
    }
    public void MostrarTel(){
        for(Integer tl:telefono){
            System.out.println(tl);
        }
        
    }
    
}