/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Bloque;
import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author slimbbok
 */
public class DAOBloques extends AbstractDAO{
    
    public DAOBloques (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public java.util.List<Bloque> consultarBloques(String nombre ,String grado, String descripcion){
        java.util.List<Bloque> resultado = new java.util.ArrayList();
        
        Bloque bloqueActual;
        Connection con;
        PreparedStatement stmBloques=null;
        ResultSet rsBloques;
        con=this.getConexion();
        
        String consulta = "select * " +
                             "from bloque as bl "+
                             "where nombre like ?"+
                             "  and grado like ?"
                            + " and descripcion like ?";
        
        try  {
             stmBloques = con.prepareStatement(consulta);
             stmBloques.setString(1, "%"+nombre+"%");
             stmBloques.setString(2, "%"+grado+"%");
             stmBloques.setString(3, "%"+descripcion+"%");
             rsBloques = stmBloques.executeQuery();
        while (rsBloques.next())
        {
            bloqueActual = new Bloque(rsBloques.getString("nombre"),rsBloques.getString("grado"),rsBloques.getString("descripcion"));
            
            resultado.add(bloqueActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmBloques.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
}
