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
public class Asignatura {
    private String nombre;
    private String codigo;
    private String bloque;
    private String grado;
    private int creditos;
    private String tipo;
    private int curso;
    private int numeroAlumnos;

    public Asignatura(String nombre, String codigo, String bloque, String grado, int creditos, String tipo, int curso, int numeroAlumnos) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.bloque = bloque;
        this.grado = grado;
        this.creditos = creditos;
        this.tipo = tipo;
        this.curso = curso;
        this.numeroAlumnos = numeroAlumnos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public int getNumeroAlumnos() {
        return numeroAlumnos;
    }

    public void setNumeroAlumnos(int numeroAlumnos) {
        this.numeroAlumnos = numeroAlumnos;
    }
}
