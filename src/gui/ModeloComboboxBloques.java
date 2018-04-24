/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Bloque;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author slimbbok
 */
public class ModeloComboboxBloques implements ComboBoxModel{
    
    private java.util.List<Bloque> bloques;
    private int selectedItem;

    @Override
    public void setSelectedItem(Object anItem) {
        if(bloques.lastIndexOf(anItem)!=-1) selectedItem= bloques.lastIndexOf(anItem)+1;
        else selectedItem = 0;
    }

    @Override
    public Object getSelectedItem() {
        if(selectedItem==0) return "";
        else return bloques.get(selectedItem-1).toString();
    }

    public ModeloComboboxBloques(java.util.List<Bloque> bloques) {
        if(bloques!=null) this.bloques=bloques;
        else bloques=new java.util.ArrayList();
        selectedItem=0;
    }

    @Override
    public int getSize() {
        return bloques.size()+1;
    }

    @Override
    public Object getElementAt(int index) {
        if(index==0) return "";
        else if(index<bloques.size()&&index>0) return bloques.get(index);
        return null;
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
