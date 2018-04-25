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
    
    public boolean insertarAdministrador(Administrador ad){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmAdministrador=null;

        con=super.getConexion();

        try {
            con.setAutoCommit(false);
            
            stmAdministrador=con.prepareStatement("insert into usuario(correo, dni, nombre, fecha_nac) "+
                                  "values (?,?,?,?)");
            stmAdministrador.setString(1, ad.getCorreo());
            stmAdministrador.setString(2, ad.getDni());
            stmAdministrador.setString(3, ad.getNombre());
            stmAdministrador.setTimestamp(4, ad.getFechaNacimiento());
            stmAdministrador.executeUpdate();
            
            
            stmAdministrador=con.prepareStatement("insert into administrador(correo) "+
                                          "values (?)");
            stmAdministrador.setString(1, ad.getCorreo());
            stmAdministrador.executeUpdate();
            
            con.commit();
            retorno=true;
            
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
        }finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
              System.out.println(ex.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }
          try {stmAdministrador.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean insertarAlumno(Alumno al){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmAlumno=null;

        con=super.getConexion();
        
        try {
            con.setAutoCommit(false);
            
            stmAlumno=con.prepareStatement("insert into usuario(correo, dni, nombre, fecha_nac) "+
                                  "values (?,?,?,?)");
            stmAlumno.setString(1, al.getCorreo());
            stmAlumno.setString(2, al.getDni());
            stmAlumno.setString(3, al.getNombre());
            stmAlumno.setTimestamp(4, al.getFechaNacimiento());
            stmAlumno.executeUpdate();
            
            stmAlumno=con.prepareStatement("insert into alumno(correo,grado) "+
                                          "values (?,?)");
            stmAlumno.setString(1, al.getCorreo());
            stmAlumno.setString(2, al.getGrado());
            stmAlumno.executeUpdate();
            con.commit();
            retorno=true;
            
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
        }finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
              System.out.println(ex.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }
          try {stmAlumno.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean insertarProfesor(Profesor prof){ //hacerlo sin el autocommit
        boolean retorno=false;
        Connection con;
        PreparedStatement stmProf=null;

        con=super.getConexion();
        
        try {
            con.setAutoCommit(false);
            
            stmProf=con.prepareStatement("insert into usuario(correo, dni, nombre, fecha_nac) "+
                                  "values (?,?,?,?)");
            stmProf.setString(1, prof.getCorreo());
            stmProf.setString(2, prof.getDni());
            stmProf.setString(3, prof.getNombre());
            stmProf.setTimestamp(4, prof.getFechaNacimiento());
            stmProf.executeUpdate();
            
            stmProf=con.prepareStatement("insert into profesor(correo,gradoasociado,bloqueasociado) "+
                                          "values (?,?,?)");
            stmProf.setString(1, prof.getCorreo());
            stmProf.setString(2, prof.getGradoAsociado());
            stmProf.setString(3, prof.getBloqueAsociado());
            stmProf.executeUpdate();
            con.commit();
            retorno=true;
            
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
        }finally{
            try {
                con.setAutoCommit(true);
            } catch (SQLException ex) {
              System.out.println(ex.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }
          try {stmProf.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
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
    
    private boolean eliminarAlumno(Alumno al){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        try {
            con.setAutoCommit(false);
            
            stmUsuario=con.prepareStatement("delete from alumno where correo = ?");
            stmUsuario.setString(1, al.getCorreo());
            stmUsuario.executeUpdate();
            
            
            stmUsuario=con.prepareStatement("delete from usuario where correo = ?");
            stmUsuario.setString(1, al.getCorreo());
            stmUsuario.executeUpdate();
            
            
            con.commit();
            retorno=true;
            
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            try{
              con.rollback();
            }catch(SQLException ex){
              System.out.println(ex.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }
        }finally{
            try{
            con.setAutoCommit(true);
            }catch(SQLException e){
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    private boolean eliminarProfesor(Profesor prof){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        try {
            con.setAutoCommit(false);
            
            stmUsuario=con.prepareStatement("delete from profesor where correo = ?");
            stmUsuario.setString(1, prof.getCorreo());
            stmUsuario.executeUpdate();
            
            
            stmUsuario=con.prepareStatement("delete from usuario where correo = ?");
            stmUsuario.setString(1, prof.getCorreo());
            stmUsuario.executeUpdate();
            
            
            con.commit();
            retorno=true;
            
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            try{
              con.rollback();
            }catch(SQLException ex){
              System.out.println(ex.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }
        }finally{
            try{
            con.setAutoCommit(true);
            }catch(SQLException e){
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    private boolean eliminarAdministrador(Administrador ad){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        try {
            con.setAutoCommit(false);
            
            stmUsuario=con.prepareStatement("delete from administrador where correo = ?");
            stmUsuario.setString(1, ad.getCorreo());
            stmUsuario.executeUpdate();
            
            
            stmUsuario=con.prepareStatement("delete from usuario where correo = ?");
            stmUsuario.setString(1, ad.getCorreo());
            stmUsuario.executeUpdate();
            
            
            con.commit();
            retorno=true;
            
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            try{
              con.rollback();
            }catch(SQLException ex){
              System.out.println(ex.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
            }
        }finally{
            try{
            con.setAutoCommit(true);
            }catch(SQLException e){
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean eliminarUsuario(Usuario us){
        
        if(us instanceof Alumno) return eliminarAlumno(((Alumno) us));
        if(us instanceof Profesor) return eliminarProfesor(((Profesor) us));
        if(us instanceof Administrador) return eliminarAdministrador(((Administrador) us));
        return false;
    }
}
