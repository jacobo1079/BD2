
package aplicacion;

/**
 *
 * @author slimbbok
 */
public class Grado {
    private String nombre;
    private String codigo;
    
    //////////////////////////////////////////////   CONSTRUCTORES
    public Grado(String nombre,String codigo){
        this.nombre=nombre;
        this.codigo=codigo;
    }
    //////////////////////////////////////////////   GETTES
    public String getNombre(){
        return nombre;
    }
    
    public String getCodigo(){
        return codigo;
    }
    //////////////////////////////////////////////   SETTERS
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public void setCodigo(String codigo){
        this.codigo=codigo;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
