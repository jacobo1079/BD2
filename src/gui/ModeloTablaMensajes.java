/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Mensaje;

/**
 *
 * @author slimbbok
 */
public class ModeloTablaMensajes extends javax.swing.table.AbstractTableModel{
    private java.util.List<Mensaje> mensajes;
    
    public ModeloTablaMensajes(){
        this.mensajes=new java.util.ArrayList();
    }
    
    @Override
    public int getColumnCount (){
        return 4;
    }

    @Override
    public int getRowCount(){
        return mensajes.size();
    }
    
    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Leído"; break;
            case 1: nombre= "Asunto"; break;
            case 2: nombre="Emisor"; break;
            case 3: nombre="Fecha"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.Boolean.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase=java.lang.String.class; break;
            case 3: clase=java.sql.Timestamp.class; break;
        }
        return clase;
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        if(col==0) return true;
        return false;
    }
    
    @Override
    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            case 0: resultado= mensajes.get(row).getLeido(); break;
            case 1: resultado= mensajes.get(row).getAsunto(); break;
            case 2: resultado= mensajes.get(row).getCorreoRemitente(); break;
            case 3: resultado= mensajes.get(row).getFecha(); break;
        }
        return resultado;
    }
    
    public void setFilas(java.util.List<Mensaje> mensaje){
        this.mensajes=mensaje;
        fireTableDataChanged();
    }

    public Mensaje obtenerGrado(int i){
        return this.mensajes.get(i);
    }

    public void anhadirFila(Mensaje mensaje){
        if(mensaje!=null) mensajes.add(mensaje);
        fireTableDataChanged();
    }
    
    public void eliminarFila(int fila){
        if(fila>0&&fila<mensajes.size()){
            mensajes.remove(fila);
            fireTableDataChanged();
        }
    }
}
