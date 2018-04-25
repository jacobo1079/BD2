/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;
import java.sql.Timestamp;

/**
 *
 * @author slimbbok
 */
public class Mensaje {
    private String correoRemitente;
    private String correoDestinatario;
    private Timestamp fecha;
    private String asunto;
    private boolean leido;
    private String texto;

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public boolean getLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Mensaje(String correoRemitente, String correoDestinatario, Timestamp fecha, String asunto, boolean leido, String texto) {
        this.correoRemitente = correoRemitente;
        this.correoDestinatario = correoDestinatario;
        this.fecha = fecha;
        this.asunto = asunto;
        this.leido = leido;
        this.texto = texto;
    }
    
    public Mensaje() {
        this.correoRemitente = null;
        this.correoDestinatario = null;
        this.fecha = null;
        this.asunto = null;
        this.leido = false;
        this.texto = null;
    }
}
