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
public class Administrador extends Usuario{
    
    public Administrador(String nombre, String dni, String correo, Timestamp fechaNacimiento, String clave) {
        super(nombre, dni, correo, fechaNacimiento, clave);
    }
    
    @Override
    public String toString(){
        return "Administrador";
    }
}
