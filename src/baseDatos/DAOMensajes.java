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
 * @author alumnogreibd
 */
public class DAOMensajes extends AbstractDAO {

    public DAOMensajes(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<Mensaje> consultarMensajes(String correo,String emisor, String asunto) {
        java.util.List<Mensaje> resultado = new java.util.ArrayList();
        Mensaje mensajeActual;
        Connection con;
        PreparedStatement stmMensaje=null;
        ResultSet rsMensaje;
        con=this.getConexion();
        
        String consulta = "select *"
                                + " from enviarmensaje"
                                + " where correodestinatario like ? and correoremitente like ? and asunto like ?";
        
        try  {
             stmMensaje = con.prepareStatement(consulta);
             stmMensaje.setString(1, "%"+correo+"%");
             stmMensaje.setString(2, "%"+emisor+"%");
             stmMensaje.setString(3, "%"+asunto+"%");
             rsMensaje = stmMensaje.executeQuery();
             
        while (rsMensaje.next())
        {
            mensajeActual = new Mensaje(rsMensaje.getString("correoremitente"), rsMensaje.getString("correodestinatario"),Timestamp.valueOf(rsMensaje.getString("fecha")),rsMensaje.getString("asunto"), rsMensaje.getBoolean("leido"),rsMensaje.getString("texto"));
            
            
            resultado.add(mensajeActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmMensaje.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
                                
    }
    
    public boolean modificarLeidoMensaje(Mensaje mg){
        boolean retorno = false;
        
        PreparedStatement stmMensaje=null;
        Connection con;
        con=this.getConexion();
        
        
        String modificacion = "update enviarmensaje set leido = ?"
                            + " where correoremitente = ? and correodestinatario = ? and fecha = ?";
        
        try{
            stmMensaje = con.prepareStatement(modificacion);
            stmMensaje.setBoolean(1, mg.getLeido());
            stmMensaje.setString(2, mg.getCorreoRemitente());
            stmMensaje.setString(3, mg.getCorreoDestinatario());
            stmMensaje.setTimestamp(4, mg.getFecha());
            stmMensaje.executeUpdate();
            retorno =  true;
        }catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmMensaje.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public void enviarMensaje(Mensaje mensaje) {

        PreparedStatement stmMensaje = null;
        Connection con;
        con = this.getConexion();
        
        String insercion = "INSERT INTO enviarmensaje(correoremitente, correodestinatario,"+
                            "fecha, texto, leido, asunto)" +
                            " VALUES (?, ?, ?, ?, ?, ?);";
        
        try {
            stmMensaje = con.prepareStatement(insercion);
            stmMensaje.setString(1, mensaje.getCorreoRemitente());
            stmMensaje.setString(2, mensaje.getCorreoDestinatario());
            stmMensaje.setTimestamp(3, mensaje.getFecha());
            stmMensaje.setString(4, mensaje.getTexto());
            stmMensaje.setBoolean(5, mensaje.getLeido());
            stmMensaje.setString(6, mensaje.getAsunto());
            stmMensaje.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmMensaje.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
}
