/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author slimbbok
 */
public class FachadaBaseDatos {
    
    private aplicacion.FachadaAplicacion fa;
    java.sql.Connection conexion;
    private DAOGrados daoGrados;
    private DAOUsuarios daoUsuarios;
    private DAOBloques daoBloques;
    private DAOMensajes daoMensajes;

    public FachadaBaseDatos(FachadaAplicacion fa) {
        Properties configuracion = new Properties();
        this.fa = fa;
        FileInputStream arqConfiguracion;
        
        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();
     

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion=java.sql.DriverManager.getConnection("jdbc:"+gestor+"://"+
                    configuracion.getProperty("servidor")+":"+
                    configuracion.getProperty("puerto")+"/"+
                    configuracion.getProperty("baseDatos"),
                    usuario);

            daoGrados = new DAOGrados(conexion, fa);
            daoUsuarios = new DAOUsuarios(conexion, fa);
            daoBloques = new DAOBloques(conexion,fa);
            daoMensajes = new DAOMensajes(conexion,fa);


        } catch (FileNotFoundException f){
            System.out.println(f.getMessage());
            fa.muestraExcepcion(f.getMessage());
        } catch (IOException i){
            System.out.println(i.getMessage());
            fa.muestraExcepcion(i.getMessage());
        } 
        catch (java.sql.SQLException e){
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        }
    }
    
    public java.util.List<Grado> obtenerGrados(String codigo,String nombre){
        return daoGrados.consultarGrados(codigo, nombre);
    }
    public java.util.List<Usuario> obtenerUsuarios(String nombre,String dni,String correo,String rol){
        return daoUsuarios.consultarUsuarios(nombre, dni, correo, rol);
    }
    public boolean insertarGrado(Grado gr){
        return daoGrados.insertarGrado(gr);
    }
    public boolean borrarGrado(Grado gr){
        return daoGrados.borrarGrado(gr);
    }
    public boolean modificarGrado(Grado gr){
        return daoGrados.modificarGrado(gr);
    }
    public boolean insertarAdministrador(Administrador us){
        return daoUsuarios.insertarAdministrador(us);
    }
    public boolean insertarAlumno(Alumno us){
        return daoUsuarios.insertarAlumno(us);
    }
    public boolean insertarProfesor(Profesor us){
        return daoUsuarios.insertarProfesor(us);
    }
    public java.util.List<Bloque> consultarBloques(String nombre, String grado,String descripcion){
        return daoBloques.consultarBloques(nombre, grado, descripcion);
    }
    public boolean eliminarAlumno(Alumno us){
        return daoUsuarios.eliminarAlumno(us);
    }
    public java.util.List<Mensaje> consultarMensajes(String correo,String emisor,String asunto){
         return daoMensajes.consultarMensajes(correo,emisor,asunto);
    }
    public Usuario esClaveCorrecta(String correo, String clave){
        return daoUsuarios.esClaveCorrecta(correo, clave);
    }
}
