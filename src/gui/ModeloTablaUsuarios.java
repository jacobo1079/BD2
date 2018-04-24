/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.*;

/**
 *
 * @author slimbbok
 */
public class ModeloTablaUsuarios extends javax.swing.table.AbstractTableModel{
    
    private java.util.List<Usuario> usuarios;
    
     public ModeloTablaUsuarios(){
        this.usuarios=new java.util.ArrayList<Usuario>();
    }

    public int getColumnCount (){
        return 4;
    }

    public int getRowCount(){
        return usuarios.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "D.N.I."; break;
            case 1: nombre= "Nombre"; break;
            case 2: nombre="Correo"; break;
            case 3: nombre="Rol"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        return java.lang.String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            case 0: resultado= usuarios.get(row).getDni(); break;
            case 1: resultado= usuarios.get(row).getNombre(); break;
            case 2: resultado= usuarios.get(row).getCorreo();break;
            case 3:
                if(usuarios.get(row) instanceof Alumno) resultado = "Alumno";
                if(usuarios.get(row) instanceof Profesor) resultado = "Profesor";
                if(usuarios.get(row) instanceof Administrador) resultado = "Administrador";
                break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<Usuario> usuarios){
        this.usuarios=usuarios;
        fireTableDataChanged();
    }

    public Usuario obtenerUsuario(int i){
        return this.usuarios.get(i);
    }

    public void anhadirFila(Usuario usr){
        if(usr!=null) usuarios.add(usr);
        fireTableDataChanged();
    }
    
    public void eliminarFila(int fila){
        if(fila>0&&fila<usuarios.size()){
            usuarios.remove(fila);
            fireTableDataChanged();
        }
    }
}
