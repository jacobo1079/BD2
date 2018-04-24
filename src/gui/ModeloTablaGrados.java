/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Grado;

/**
 *
 * @author slimbbok
 */
public class ModeloTablaGrados extends javax.swing.table.AbstractTableModel{
    
    private java.util.List<Grado> grados;
    
    public ModeloTablaGrados(){
        this.grados=new java.util.ArrayList();
    }
    
    @Override
    public int getColumnCount (){
        return 2;
    }

    @Override
    public int getRowCount(){
        return grados.size();
    }
    
    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Id"; break;
            case 1: nombre= "Nombre"; break;
            case 2: nombre="e-mail"; break;
            case 3: nombre="Tipo"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.Integer.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase=java.lang.String.class; break;
            case 3: clase=java.lang.String.class; break;
        }
        return clase;
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }
    
    @Override
    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            case 0: resultado= grados.get(row).getCodigo(); break;
            case 1: resultado= grados.get(row).getNombre(); break;
        }
        return resultado;
    }
    
    public void setFilas(java.util.List<Grado> grados){
        this.grados=grados;
        fireTableDataChanged();
    }

    public Grado obtenerGrado(int i){
        return this.grados.get(i);
    }

    public void anhadirFila(Grado grado){
        if(grado!=null) grados.add(grado);
        fireTableDataChanged();
    }
    
    public void eliminarFila(int fila){
        if(fila>0&&fila<grados.size()){
            grados.remove(fila);
            fireTableDataChanged();
        }
    }
}
