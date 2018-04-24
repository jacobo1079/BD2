/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Asignatura;

/**
 *
 * @author slimbbok
 */
public class ModeloTablaAsignaturas extends javax.swing.table.AbstractTableModel{
    
    private java.util.List<Asignatura> asignaturas;
    
    public ModeloTablaAsignaturas(){
        this.asignaturas=new java.util.ArrayList();
    }
    
    @Override
    public int getColumnCount (){
        return 7;
    }

    @Override
    public int getRowCount(){
        return asignaturas.size();
    }
    
    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Asignatura"; break;
            case 1: nombre= "Código"; break;
            case 2: nombre="Créditos"; break;
            case 3: nombre="Tipo"; break;
            case 4: nombre="Bloque"; break;
            case 5: nombre="Curso"; break;
            case 6: nombre="Nº Alumnos"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase=java.lang.Integer.class; break;
            case 3: clase=java.lang.String.class; break;
            case 4: clase=java.lang.String.class; break;
            case 5: clase=java.lang.Integer.class; break;
            case 6: clase=java.lang.String.class; break;
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
            case 0: resultado= asignaturas.get(row).getNombre(); break;
            case 1: resultado= asignaturas.get(row).getCodigo(); break;
            case 2: resultado= asignaturas.get(row).getCreditos(); break;
            case 3: resultado= asignaturas.get(row).getTipo(); break;
            case 4: resultado= asignaturas.get(row).getBloque(); break;
            case 5: resultado= asignaturas.get(row).getCurso(); break;
            case 6: resultado= asignaturas.get(row).getNumeroAlumnos(); break;
        }
        return resultado;
    }
    
    public void setFilas(java.util.List<Asignatura> asignatura){
        this.asignaturas=asignatura;
        fireTableDataChanged();
    }

    public Asignatura obtenerGrado(int i){
        return this.asignaturas.get(i);
    }

    public void anhadirFila(Asignatura asignatura){
        if(asignatura!=null) asignaturas.add(asignatura);
        fireTableDataChanged();
    }
    
    public void eliminarFila(int fila){
        if(fila>0&&fila<asignaturas.size()){
            asignaturas.remove(fila);
            fireTableDataChanged();
        }
    }
}
