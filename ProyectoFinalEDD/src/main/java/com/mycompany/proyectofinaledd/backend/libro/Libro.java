/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.libro;

import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;

/**
 *
 * @author brandon
 */
public class Libro {

    private String titulo;
    private String ISBN;
    private String genero;
    private int anio;
    private String autor;
    private TypeEstado estado;
    private boolean prioridadTiempo;
    private Biblioteca bibliotecaOrigen;
    private Biblioteca bibliotecaDestino;
    private String prioridad;

    public Libro(String titulo, String ISBN, String genero, int anio, String autor, TypeEstado estado) {
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.genero = genero;
        this.anio = anio;
        this.autor = autor;
        this.estado = estado;
    }

    public Libro(String titulo, String ISBN, String genero, int anio, String autor, TypeEstado estado, boolean prioridadTiempo) {
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.genero = genero;
        this.anio = anio;
        this.autor = autor;
        this.estado = estado;
        this.prioridadTiempo = prioridadTiempo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEstado() {
        return estado.toString();
    }

    public void setEstado(TypeEstado estado) {
        this.estado = estado;
    }

    public boolean isPrioridadTiempo() {
        return prioridadTiempo;
    }

    public void setPrioridadTiempo(boolean prioridadTiempo) {
        this.prioridadTiempo = prioridadTiempo;
    }

    public Biblioteca getBibliotecaDestino() {
        return bibliotecaDestino;
    }

    public void setBibliotecaDestino(Biblioteca bibliotecaDestino) {
        this.bibliotecaDestino = bibliotecaDestino;
    }

    public Biblioteca getBibliotecaOrigen() {
        return bibliotecaOrigen;
    }

    public void setBibliotecaOrigen(Biblioteca bibliotecaOrigen) {
        this.bibliotecaOrigen = bibliotecaOrigen;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    
    
    @Override
    public String toString() {
        // Escapar comillas para evitar errores en Graphviz
        String tituloLimpio = titulo != null ? titulo.replace("\"", "") : "Sin título";
        String generoLimpio = genero != null ? genero.replace("\"", "") : "Sin género";
        String isbnLimpio = ISBN != null ? ISBN.replace("\"", "") : "Sin ISBN";

        return tituloLimpio + "\\nISBN: " + isbnLimpio + "\\nAño: " + anio + "\\nGénero: " + generoLimpio;
    }
}
