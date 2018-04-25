/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.*;
import java.sql.*;

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
        java.util.List<Asignatura> asignaturasGradoactual  = new java.util.ArrayList();
        Grado gradoActual;
        Asignatura asignaturaActual;
        Connection con;
        PreparedStatement stmGrados=null;
        PreparedStatement stmAsignaturas=null;
        PreparedStatement stmMatriculas=null;
        ResultSet rsGrados;
        ResultSet rsAsignaturas;
        ResultSet rsMatriculas;
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
            
            String consultaAsignaturas = "select codigo,bloque,nombre,creditos,grado,tipo,curso from asignatura where grado = ?";
            
            try{
                stmAsignaturas = con.prepareStatement(consultaAsignaturas);
                stmAsignaturas.setString(1, rsGrados.getString("codigo"));
                rsAsignaturas = stmAsignaturas.executeQuery();
                asignaturasGradoactual = new java.util.ArrayList();
                
                
                
                while(rsAsignaturas.next()){
                
                    String consultaMatriculados = "select count(estado) from (select estado from solicitarmatricula" +
                                                    " where estado = 'aprobada' and codigoasignatura = ?) as matriculas";
                     try{
                         stmMatriculas = con.prepareStatement(consultaMatriculados);
                         stmMatriculas.setString(1, rsAsignaturas.getString("codigo"));
                         rsMatriculas = stmMatriculas.executeQuery();
                         rsMatriculas.next();
                        asignaturaActual = new Asignatura( rsAsignaturas.getString("nombre"), rsAsignaturas.getString("codigo"), rsAsignaturas.getString("bloque"), rsAsignaturas.getString("grado"),rsAsignaturas.getInt("creditos") , rsAsignaturas.getString("tipo"), rsAsignaturas.getInt("curso"), rsMatriculas.getInt("count"));
                        asignaturasGradoactual.add(asignaturaActual);
                     }catch (SQLException e){
                      System.out.println(e.getMessage());
                      this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
                    }finally{
                      try {stmMatriculas.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                    }
                }
            }catch (SQLException e){
              System.out.println(e.getMessage());
              this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }finally{
              try {stmAsignaturas.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
            }

            gradoActual = new Grado(rsGrados.getString("nombre"), rsGrados.getString("codigo"),asignaturasGradoactual);
            
            
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
        ResultSet rs;
        ResultSet rsDos;
        con=super.getConexion();

        try {
            
            stmGrado=con.prepareStatement("select exists(select * from asignatura where grado = ?) as tiene");
            stmGrado.setString(1, gr.getCodigo());
            rs = stmGrado.executeQuery();
            rs.next();
            
            stmGrado=con.prepareStatement("select exists(select * from bloque where grado = ?) as tiene");
            stmGrado.setString(1, gr.getCodigo());
            rsDos = stmGrado.executeQuery();
            rsDos.next();
            
            con.setAutoCommit(false);
            
            if(rs.getBoolean("tiene")){
                stmGrado=con.prepareStatement("delete from asignatura where grado = ?");
                stmGrado.setString(1, gr.getCodigo());
                stmGrado.executeUpdate();
            }
            
            if(rsDos.getBoolean("tiene")){
                stmGrado=con.prepareStatement("delete from bloque where grado = ?");
                stmGrado.setString(1, gr.getCodigo());
                stmGrado.executeUpdate();
            }
            
            stmGrado=con.prepareStatement("delete from grado where codigo = ?");
            stmGrado.setString(1, gr.getCodigo());
            stmGrado.executeUpdate();
            
            con.commit();
            retorno = true;
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
    
    public boolean inserrtarGradoAsistente(Grado gr,java.util.List<Bloque> bloques){
        boolean retorno=false;
        Connection con;
        PreparedStatement stmGrados=null;
        
        java.util.List<Asignatura> asignaturas = gr.getAsignaturas();

        con=super.getConexion();

        try {
            con.setAutoCommit(false);
            
            stmGrados=con.prepareStatement("insert into grado(codigo, nombre) "+
                                  "values (?,?)");
            stmGrados.setString(1, gr.getCodigo());
            stmGrados.setString(2, gr.getNombre());
            stmGrados.executeUpdate();
            
            for(Bloque b:bloques){
                
                stmGrados=con.prepareStatement("insert into bloque(nombre ,grado ,descripcion) "+
                                              "values (?,?,?)");
                stmGrados.setString(1, b.getNombre());
                stmGrados.setString(2, b.getGrado());
                stmGrados.setString(3, b.getDescripcion());
                stmGrados.executeUpdate();
                
//                asignaturasBloqueActual.clear();
//                for(Asignatura a: gr.getAsignaturas())
//                    if(a.getBloque().equals(b.getNombre()))
//                        asignaturasBloqueActual.add(a);
//                    insertar las asignaturas despues del bloque permite poner un label y hacer un rollback solo hasta aqui
            }
            
            for(Asignatura a:asignaturas){
                
                stmGrados=con.prepareStatement("insert into asignatura(codigo ,bloque ,grado, nombre, creditos, tipo, curso) "+
                                              "values (?,?,?,?,?,?,?)");
                stmGrados.setString(1, a.getCodigo());
                stmGrados.setString(2, a.getBloque());
                stmGrados.setString(3, a.getGrado());
                stmGrados.setString(4, a.getNombre());
                stmGrados.setInt(5, a.getCreditos());
                stmGrados.setString(6, a.getTipo());
                stmGrados.setInt(7, a.getCurso());
                stmGrados.executeUpdate();
                
            }
            
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
          try {stmGrados.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return retorno;
    }
    
}
