/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Grado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author slimbbok
 */
public class DAOGrados extends AbstractDAO{
    
    public DAOGrados (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public java.util.List<Grado> consultarGrados(String codigo, String nombre){
        
        java.util.List<Grado> resultado = new java.util.ArrayList<Grado>();
        Grado gradoActual;
        Connection con;
        PreparedStatement stmGrados=null;
        ResultSet rsGrados;
        con=this.getConexion();
        
        String consulta = "select codigo,nombre " +
                             "from grado as gr "+
                             "where codigo like ?"+
                             "  and nombre like ?";
        
        try  {
             stmGrados = con.prepareStatement(consulta);
             stmGrados.setString(1, "%"+codigo+"%");
             stmGrados.setString(2, "%"+nombre+"%");
             rsGrados = stmGrados.executeQuery();
        while (rsGrados.next())
        {
            gradoActual = new Grado(rsGrados.getString("nombre"), rsGrados.getString("codigo"));
            
            //si hacemos lo de los alumnos de grado mirar el daoLibros de la biblioteca funcion obtener catalogo
            
            resultado.add(gradoActual);
        }

        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmGrados.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    public boolean modificarGrado(Grado gr){
        
        boolean retorno=false;
        Connection con;
        PreparedStatement stmGrados=null;
        con=this.getConexion();
        
        String update = "update grado " +
                             "set nombre=? "+
                             "where codigo=?";
        
        try  {
             stmGrados = con.prepareStatement(update);
             stmGrados.setString(1, gr.getNombre());
             stmGrados.setString(2, gr.getCodigo());
             stmGrados.executeUpdate();
             retorno = true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmGrados.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean borrarGrado(Grado gr){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmGrado=null;

        con=super.getConexion();

        try {
        stmGrado=con.prepareStatement("delete from grado where codigo = ?");
        stmGrado.setString(1, gr.getCodigo());
        stmGrado.executeUpdate();
        retorno = true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmGrado.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
    public boolean insertarGrado(Grado gr){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmGrado=null;

        con=super.getConexion();

        try {
        stmGrado=con.prepareStatement("insert into grado(codigo, nombre) "+
                                      "values (?,?)");
        stmGrado.setString(1, gr.getCodigo());
        stmGrado.setString(2, gr.getNombre());
        stmGrado.executeUpdate();
        retorno=true;
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmGrado.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
}
