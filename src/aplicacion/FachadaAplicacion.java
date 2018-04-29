/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

/**
 *
 * @author slimbbok
 */
public class FachadaAplicacion {

    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionMensaje cm;

    public FachadaAplicacion() {
        fbd= new baseDatos.FachadaBaseDatos(this);
        fgui = new gui.FachadaGui(this);
        cm = new GestionMensaje(fgui,fbd);
    }

    public static void main(String[] args) {

        FachadaAplicacion fa;

        fa = new FachadaAplicacion();
        fa.iniciaInterfazUsuario();
    }

    public void iniciaInterfazUsuario() {
        fgui.iniciaVista();
    }
    
    public void muestraExcepcion(String e){
        fgui.muestraExcepcion(e);
    }
    
    public java.util.List<Grado> consultarGrados(String codigo,String nombre){
        return fbd.obtenerGrados(codigo, nombre);
    }
    
    public java.util.List<Usuario> consultarUsuarios(String nombre,String dni,String correo,String rol){
        return fbd.obtenerUsuarios(nombre, dni, correo, rol);
    }
    
    public boolean insertarGrado(Grado gr){
        return fbd.insertarGrado(gr);
    }
    
    public boolean borrarGrado(Grado gr){
        return fbd.borrarGrado(gr);
    }
    
    public boolean modificarGrado(Grado gr){
        return fbd.modificarGrado(gr);
    }
    
    public boolean insertarAdministrador(Administrador us){
        return fbd.insertarAdministrador(us);
    }
    
    public boolean insertarAlumno(Alumno us){
        return fbd.insertarAlumno(us);
    }
    
    public boolean insertarProfesro(Profesor us){
        return fbd.insertarProfesor(us);
    }
    
    public java.util.List<Bloque> ConsultarBloques(String nombre, String grado,String descripcion){
        return fbd.consultarBloques(nombre, grado, descripcion);
    }
    
    public boolean eliminarUsuario(Usuario us){
        return fbd.eliminarUsuario(us);
    }
    
    public void abrirCorreo(String correo){
        cm.abrirCorreo(correo);
    }
    
    public Usuario esClaveCorrecta(String correo, String clave){
        return fbd.esClaveCorrecta(correo, clave);
    }
    
    public java.util.List<Mensaje> consultarMensajes(String correo,String emisor,String asunto){
         return fbd.consultarMensajes(correo,emisor,asunto);
    }
    
    public boolean modificarLeidoMensaje(Mensaje mg){
        boolean a = fbd.modificarLeidoMensaje(mg);
        return a;
    }
    
    public void nuevoMensaje(String remitente, String destinatario,String asunto){
        cm.nuevoMensaje(remitente, destinatario,asunto);
    }
    
    public void enviarMensaje(Mensaje mensaje){
        cm.enviarMensaje(mensaje);
    }
    
    public boolean inserrtarGradoAsistente(Grado gr,java.util.List<Bloque> bloques){
        return fbd.inserrtarGradoAsistente(gr, bloques);
    }
    
    public boolean clausuaraAnoAcademico(){
        return fbd.clausuaraAnoAcademico();
    }
}


