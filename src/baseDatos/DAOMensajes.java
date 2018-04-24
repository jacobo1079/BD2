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
 * @author alumnogreibd
 */
public class DAOMensajes extends AbstractDAO {

    public DAOMensajes(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<Mensaje> consultarMensajes(String correo) {
        java.util.List<Mensaje> resultado = new java.util.ArrayList();
        Mensaje mensajeActual;
        Connection con;
        PreparedStatement stmMensaje=null;
        ResultSet rsMensaje;
        con=this.getConexion();
        
        String consulta = "select *"
                                + " from enviarmensaje"
                                + " where correodestinatario like ? ";
        
        try  {
             stmMensaje = con.prepareStatement(consulta);
             stmMensaje.setString(1, "%"+correo+"%"); 
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
}
