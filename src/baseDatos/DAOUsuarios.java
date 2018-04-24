/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author slimbbok
 */
public class DAOUsuarios extends AbstractDAO{
    
    public DAOUsuarios (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public java.util.List<Alumno> consultarAlumnos(String nombre,String dni,String correo){
        java.util.List<Alumno> resultado = new java.util.ArrayList();
        Alumno usuarioActual;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;
        con=this.getConexion();
        
        String consultaGeneral = "select u.* , a.grado"
                                      + " from usuario as u, alumno as a "
                                        + " where u.correo=a.correo "
                                        + " and u.nombre like ? and u.dni like ?"
                                        + " and u.correo like ?";
        
        try  {
             stmUsuarios = con.prepareStatement(consultaGeneral);
             stmUsuarios.setString(1, "%"+nombre+"%"); //si da errores de null pointer poner un if(!dni.isEmpty()) +ese codigo
             stmUsuarios.setString(2, "%"+dni+"%");
             stmUsuarios.setString(3, "%"+correo+"%");
             rsUsuarios = stmUsuarios.executeQuery();
             
        while (rsUsuarios.next())
        {
            usuarioActual = new Alumno(rsUsuarios.getString("nombre"), rsUsuarios.getString("dni"),rsUsuarios.getString("correo"),Timestamp.valueOf(rsUsuarios.getString("fecha_nac")),rsUsuarios.getString("grado"));
            
            
            resultado.add(usuarioActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuarios.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public java.util.List<Profesor> consultaProfesores(String nombre,String dni,String correo){
        java.util.List<Profesor> resultado = new java.util.ArrayList();
        Profesor usuarioActual;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;
        con=this.getConexion();
        
        String consultaGeneral = "select u.*,p.gradoasociado,p.bloqueasociado "
                                      + " from usuario as u , profesor as p "
                                        + " where u.correo=p.correo "
                                        + " and u.nombre like ? and u.dni like ?"
                                        + " and u.correo like ?";
        
        try  {
             stmUsuarios = con.prepareStatement(consultaGeneral);
             stmUsuarios.setString(1, "%"+nombre+"%"); //si da errores de null pointer poner un if(!dni.isEmpty()) +ese codigo
             stmUsuarios.setString(2, "%"+dni+"%");
             stmUsuarios.setString(3, "%"+correo+"%");
             rsUsuarios = stmUsuarios.executeQuery();
             
        while (rsUsuarios.next())
        {
            usuarioActual = new Profesor(rsUsuarios.getString("nombre"), rsUsuarios.getString("dni"),rsUsuarios.getString("correo"),Timestamp.valueOf(rsUsuarios.getString("fecha_nac")),rsUsuarios.getString("gradoasociado"),rsUsuarios.getString("bloqueAsociado"));
            
            resultado.add(usuarioActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuarios.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public java.util.List<Administrador> consultaAdministradores(String nombre,String dni,String correo){
        java.util.List<Administrador> resultado = new java.util.ArrayList();
        Administrador usuarioActual;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;
        con=this.getConexion();
        
        String consultaGeneral = "select u.*"
                                      + "from usuario as u, administrador as a "
                                        + "where u.correo=a.correo "
                                        + "and u.nombre like ? and u.dni like ?"
                                        + "and u.correo like ?";
        
        try  {
             stmUsuarios = con.prepareStatement(consultaGeneral);
             stmUsuarios.setString(1, "%"+nombre+"%"); //si da errores de null pointer poner un if(!dni.isEmpty()) +ese codigo
             stmUsuarios.setString(2, "%"+dni+"%");
             stmUsuarios.setString(3, "%"+correo+"%");
             rsUsuarios = stmUsuarios.executeQuery();
             
        while (rsUsuarios.next())
        {
            usuarioActual = new Administrador(rsUsuarios.getString("nombre"), rsUsuarios.getString("dni"),rsUsuarios.getString("correo"),Timestamp.valueOf(rsUsuarios.getString("fecha_nac")));
            
            
            resultado.add(usuarioActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuarios.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public java.util.List<Usuario> consultarUsuarios(String nombre,String dni,String correo,String rol){
        java.util.List<Usuario> resultado = new java.util.ArrayList();
        
        if(!rol.isEmpty()){
            switch(rol.toLowerCase()){
                case "administrador":
                    resultado.addAll(this.consultaAdministradores(nombre, dni, correo));
                    break;
                case "profesor":
                    resultado.addAll(this.consultaProfesores(nombre, dni, correo));
                    break;
                case "alumno":
                    resultado.addAll(this.consultarAlumnos(nombre, dni, correo));
                    break;
            }
        }else{
            resultado.addAll(this.consultaAdministradores(nombre, dni, correo));
            resultado.addAll(this.consultaProfesores(nombre, dni, correo));
            resultado.addAll(this.consultarAlumnos(nombre, dni, correo));
        }
        
        return resultado;
    }
    
    private boolean insertarTablaUsuarios(Usuario us){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        
        try {
            stmUsuario=con.prepareStatement("insert into usuario(correo, dni, nombre, fecha_nac) "+
                                  "values (?,?,?,?)");
            stmUsuario.setString(1, us.getCorreo());
            stmUsuario.setString(2, us.getDni());
            stmUsuario.setString(3, us.getNombre());
            stmUsuario.setTimestamp(4, us.getFechaNacimiento());
            stmUsuario.executeUpdate();
            retorno=true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    private boolean eliminarTablaUsuarios(Usuario us){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        
        try {
            stmUsuario=con.prepareStatement("delete from usuario where correo = ?");
            stmUsuario.setString(1, us.getCorreo());
            stmUsuario.executeUpdate();
            retorno=true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean insertarAdministrador(Administrador ad){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmRol=null;

        con=super.getConexion();

        
        if(!this.insertarTablaUsuarios(ad)){
            if(!this.eliminarTablaUsuarios(ad)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.eliminarTablaUsuarios(ad);
                return false;
            }
        }
        
        try {
            stmRol=con.prepareStatement("insert into administrador(correo) "+
                                          "values (?)");
            stmRol.setString(1, ad.getCorreo());
            stmRol.executeUpdate();
            retorno=true;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            if(!this.eliminarTablaUsuarios(ad)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.eliminarTablaUsuarios(ad);
                return false;
            }
        }finally{
          try {stmRol.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean insertarAlumno(Alumno al){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmRol=null;

        con=super.getConexion();

        
        if(!this.insertarTablaUsuarios(al)){
            if(!this.eliminarTablaUsuarios(al)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.eliminarTablaUsuarios(al);
                return false;
            }
        }
        
        try {
            stmRol=con.prepareStatement("insert into alumno(correo,grado) "+
                                          "values (?,?)");
            stmRol.setString(1, al.getCorreo());
            stmRol.setString(2, al.getGrado());
            stmRol.executeUpdate();
            retorno=true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
          if(!this.eliminarTablaUsuarios(al)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.eliminarTablaUsuarios(al);
                return false;
            }
        }finally{
          try {stmRol.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean insertarProfesor(Profesor prof){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmRol=null;

        con=super.getConexion();

        
        if(!this.insertarTablaUsuarios(prof)){
            if(!this.eliminarTablaUsuarios(prof)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.eliminarTablaUsuarios(prof);
                return false;
            }
        }
        
        try {
            stmRol=con.prepareStatement("insert into profesor(correo,gradoasociado,bloqueasociado) "+
                                          "values (?,?,?)");
            stmRol.setString(1, prof.getCorreo());
            stmRol.setString(2, prof.getGradoAsociado());
            stmRol.setString(3, prof.getBloqueAsociado());
            stmRol.executeUpdate();
            retorno=true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
          if(!this.eliminarTablaUsuarios(prof)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.eliminarTablaUsuarios(prof);
                return false;
            }
        }finally{
          try {stmRol.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }

    public Usuario esClaveCorrecta(String correo, String clave){
        Administrador admin;
        Connection con;
        PreparedStatement stmAutenticacion=null;
        PreparedStatement stmGet=null;
        PreparedStatement stmAdministrador = null;
        ResultSet rsAutenticacion;
        ResultSet rsUsuario;
        ResultSet rsAdmin;

        con=super.getConexion();

        try {
        stmAutenticacion=con.prepareStatement("select is_pass_correct( ? ,(select clave from usuario where correo = ? )) as pass");
        stmAutenticacion.setString(1, clave);
        stmAutenticacion.setString(2, correo);
        rsAutenticacion = stmAutenticacion.executeQuery();
        rsAutenticacion.next();
        if(rsAutenticacion.getBoolean("pass")){
            String getAdministrador = "select * from usuario where correo = ?";
            try{
                stmGet = con.prepareStatement(getAdministrador);
                stmGet.setString(1, correo);
                rsUsuario = stmGet.executeQuery();
                rsUsuario.next();
                admin = new Administrador(rsUsuario.getString("nombre"),rsUsuario.getString("dni"),rsUsuario.getString("correo"),rsUsuario.getTimestamp("fecha_nac"));
                
                
                String esAdmin = "select (exists(select correo from administrador where correo = ?)) as respuesta";
                
                try{
                    stmAdministrador = con.prepareStatement(esAdmin);
                    stmAdministrador.setString(1, correo);
                    rsAdmin = stmAdministrador.executeQuery();
                    rsAdmin.next();
                    if(rsAdmin.getBoolean("respuesta")) return admin;
                    else return new Alumno("", "", "", new Timestamp(System.currentTimeMillis()), "");
                }catch (SQLException e){
                  System.out.println(e.getMessage());
                  this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                }finally{
                  try {stmAdministrador.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                }
                
                
                return admin;
            }catch (SQLException e){
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }finally{
              try {stmGet.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }
        }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmAutenticacion.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return null;
    }
    
    public boolean eliminarAlumno(Alumno al){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        
        try {
            stmUsuario=con.prepareStatement("delete from alumno where correo = ?");
            stmUsuario.setString(1, al.getCorreo());
            stmUsuario.executeUpdate();
            if(!this.eliminarTablaUsuarios(al)){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(!this.eliminarTablaUsuarios(al)){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DAOUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PreparedStatement stmInsertar=null;
                    String insert = "insert into alumno(correo,grado) values (?,?)";
                    try{
                        stmInsertar = con.prepareStatement(insert);
                        stmInsertar.setString(1, al.getCorreo());
                        stmInsertar.setString(2, al.getGrado());
                    }catch (SQLException e){
                      System.out.println(e.getMessage());
                      this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    }finally{
                      try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                    }
                    return false;
                }
                return true;
            }
            retorno=true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
}
