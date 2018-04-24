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
public class Profesor extends Usuario{
    
    private String gradoAsociado;
    private String bloqueAsociado;
    
    public Profesor(String nombre, String dni, String correo, Timestamp fechaNacimiento,String gradoAsociado,String bloqueAsociado) {
        super(nombre, dni, correo, fechaNacimiento);
        this.gradoAsociado = gradoAsociado;
        this.bloqueAsociado = bloqueAsociado;
    }

    public String getGradoAsociado() {
        return gradoAsociado;
    }

    public void setGradoAsociado(String gradoAsociado) {
        this.gradoAsociado = gradoAsociado;
    }

    public String getBloqueAsociado() {
        return bloqueAsociado;
    }

    public void setBloqueAsociado(String bloqueAsociado) {
        this.bloqueAsociado = bloqueAsociado;
    }
    
    @Override
    public String toString(){
        return "Profesor";
    }
}
