/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Grado;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author slimbbok
 */
public class ModeloComboboxGrados implements ComboBoxModel{
    
    private java.util.List<Grado> grados;
    private int selectedItem;

    @Override
    public void setSelectedItem(Object anItem) {
        if(grados.lastIndexOf(anItem)!=-1) selectedItem= grados.lastIndexOf(anItem)+1;
        else selectedItem = 0;
    }

    @Override
    public Object getSelectedItem() {
        if(selectedItem==0) return "";
        else return grados.get(selectedItem-1).toString();
    }

    public ModeloComboboxGrados(java.util.List<Grado> brados) {
        if(brados!=null) this.grados=brados;
        else brados=new java.util.ArrayList();
        selectedItem=0;
    }

    @Override
    public int getSize() {
        return grados.size()+1;
    }

    @Override
    public Object getElementAt(int index) {
        if(index==0) return "";
        else if(index<grados.size()&&index>0) return grados.get(index);
        return null;
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        
    }

}
