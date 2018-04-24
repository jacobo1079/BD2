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
public abstract class Usuario {
    
    private String dni;
    private String nombre;
    private String correo;
    private Timestamp fechaNacimiento;
    private String clave;
    /////////////////////////////////////////////////      CONSTRUCTORES

    public Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public Usuario(String nombre,String dni,String correo,Timestamp fechaNacimiento,String clave){
        this.nombre=nombre;
        this.dni=dni;
        this.correo=correo;
        this.fechaNacimiento=fechaNacimiento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    /////////////////////////////////////////////////        GETTERS
    public String getDni(){
        return dni;
    }
    public String getNombre(){
        return nombre;
    }
    public String getCorreo(){
        return correo;
    }
    /////////////////////////////////////////////////        SETTERS
    public void setDni(String dni){
        this.dni=dni;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setCorreo(String correo){
        this.correo=correo;
    }
}
