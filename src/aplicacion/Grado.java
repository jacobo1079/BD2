
package aplicacion;

import java.util.List;

/**
 *
 * @author slimbbok
 */
public class Grado {
    private String nombre;
    private String codigo;
    private java.util.List<Asignatura> asignaturas;

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
    
    //////////////////////////////////////////////   CONSTRUCTORES
    public Grado(String nombre,String codigo,java.util.List<Asignatura> asignaturas){
        this.nombre=nombre;
        this.codigo=codigo;
        this.asignaturas = asignaturas;
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
