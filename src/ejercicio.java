


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Oscar de la Cuesta - www.palentino.es
 */
public class ejercicio {
    
    
    public static void main(String[] args) {
        try {
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String texto="";
            char opcion='1';
            Agenda mi_agenda= new Agenda();
            while ((opcion=='1') || (opcion=='2') || (opcion=='3') || (opcion=='4')|| (opcion=='5') || (opcion=='6') )
            {
                System.out.println("......................");
                System.out.println("AGENDA");
                System.out.println("......................");
                System.out.println("1-Nuevo contacto");
                System.out.println("2-Buscar contacto");
                System.out.println("3-Modificar contacto");
                System.out.println("4-Eliminar contacto");
                System.out.println("5-Listado de contactos");
                System.out.println("6-Vaciar agenda");
                System.out.println("0-Salir");
                System.out.println("......................");
                texto=teclado.readLine();
                opcion= texto.charAt(0);
                System.out.println("Opción: ");
                System.out.println("......................");
                
                switch(opcion){
                    case '1':
                        String nombre;
                        String telefono;
                        boolean validar;
                        System.out.println("Por favor introduzca el nombre:");
                        nombre=teclado.readLine();
                        System.out.println("Por favor introduzca el teléfono(numero):");
                        telefono=teclado.readLine();
                        validar=esNumerica(telefono);
                        if (validar){
                             int telefono_entero= Integer.parseInt(telefono);
                             mi_agenda.Consultar(nombre, telefono_entero);
                             mi_agenda.Anadir(nombre, telefono_entero);
                        }
                        else{
                             System.out.println("No es un número, formato de telefono incorrecto.");}
                        
                       
                        break;
                       
                                
                    case '2':
                        System.out.println("Nombre a buscar:");
                        nombre=teclado.readLine().toUpperCase();
                        mi_agenda.Buscar(nombre);
                        break;
                    case '3':
                        mi_agenda.Modificar();
                         break;
                    case '4':
                        mi_agenda.Eliminar();
                        break;
                    case '5':
                        mi_agenda.Mostrar();
                        break;
                    case '6':
                       mi_agenda.Vaciar();
                        break;
                    case '0':
                        System.out.println("Ha salido del programa");
                        break;
                     
                    default:
                        System.out.println("Opción incorrecta ...");
                        opcion='1';
                     
                }
                
            }    
            
                    } catch (IOException ex) {
            Logger.getLogger(ejercicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static boolean esNumerica(String str)
{
    for (char c : str.toCharArray())
    {
        if (!Character.isDigit(c)) return false;
    }
    return true;
}
}