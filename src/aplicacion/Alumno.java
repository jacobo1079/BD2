/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.sql.Timestamp;

/**
 *
 * @author slimbbok
 */
public class Alumno extends Usuario{
    
    private String grado;
    
    public Alumno(String nombre, String dni, String correo, Timestamp fechaNacimiento,String grado) {
        super(nombre, dni, correo, fechaNacimiento);
        this.grado=grado;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    @Override
    public String toString(){
        return "Alumno";
    }
    
}
