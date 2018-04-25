/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;

/**
 *
 * @author alumnogreibd
 */
public class GestionMensaje {
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionMensaje(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public void abrirCorreo(String correo){
        java.util.List<Mensaje> mensajes = new java.util.ArrayList<> ();
        
        mensajes = fbd.consultarMensajes(correo,"","");
        
        
        fgui.abrirMensaje(mensajes);
        
    }
    public void nuevoMensaje(String remitente, String destinatario,String asunto){
        fgui.nuevoMensaje(remitente,destinatario,asunto);
    }
    
    public void enviarMensaje(Mensaje mensaje){
        fbd.enviarMensaje(mensaje);
    }
}
