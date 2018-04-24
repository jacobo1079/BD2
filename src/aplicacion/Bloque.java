/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

/**
 *
 * @author slimbbok
 */
public class Bloque {
    private String nombre;
    private String grado;
    private String descripcion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bloque(String nombre, String grado, String descripcion) {
        this.nombre = nombre;
        this.grado = grado;
        this.descripcion = descripcion;
    }
    @Override
    public String toString(){
        return nombre;
    }
}
