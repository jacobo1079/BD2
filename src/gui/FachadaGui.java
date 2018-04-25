/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Mensaje;

/**
 *
 * @author alumnogreibd
 */
public class FachadaGui {

    aplicacion.FachadaAplicacion fa;
    gui.VprincipalAdministrador vp;
    

    public FachadaGui(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
        this.vp = new gui.VprincipalAdministrador(fa,fa.consultarGrados("", ""),fa.consultarUsuarios("", "", "", ""));
    }
    
    public void iniciaVista() {
        Vautenticacion va;
        va = new Vautenticacion(vp, true, fa);
        vp.setVisible(true);
        va.setVisible(true);
    }
    
    public void muestraExcepcion(String txtExcepcion){
       VAviso va;
       
       va = new VAviso(vp, true, txtExcepcion);
       va.setVisible(true);
    }
    public void abrirMensaje(java.util.List<Mensaje> mensajes){
        Vcorreo correo;
        
        correo = new Vcorreo(vp,true,fa,mensajes);
        correo.setVisible(true);
    }
    
    public void nuevoMensaje(String remitente, String destinatario,String asunto){
        VenviarMensaje enviarmensaje;
        
        enviarmensaje = new VenviarMensaje(vp, true, fa, remitente, destinatario,asunto);
        enviarmensaje.setVisible(true);
    }
}
