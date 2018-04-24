/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Bloque;

/**
 *
 * @author slimbbok
 */
public class ModeloListaBloques extends javax.swing.AbstractListModel{
    java.util.List<Bloque> elementos;
    
    public ModeloListaBloques(){
        elementos=new java.util.ArrayList();
    }
    public int getSize(){
        return this.elementos.size();
    }

    public Bloque getElementAt(int i){
        return elementos.get(i);
    }

    public void nuevoElemento(Bloque e){
        this.elementos.add(e);
        fireIntervalAdded(this, this.elementos.size()-1, this.elementos.size()-1);
    }

    public void borrarElemento(int i){
        Bloque e;
        e=this.elementos.get(i);
        this.elementos.remove(i);
        fireIntervalRemoved(this,i,i);
    }

    public void setElementos(java.util.List<Bloque> elementos){
        this.elementos=elementos;
        fireContentsChanged(this, 0, elementos.size()-1);
    }

    public java.util.List<Bloque> getElementos(){
        return this.elementos;
    }
}
