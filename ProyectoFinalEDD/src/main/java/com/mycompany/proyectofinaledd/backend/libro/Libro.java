/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.libro;

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

    public Libro(String titulo, String ISBN, String genero, int anio, String autor, TypeEstado estado) {
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.genero = genero;
        this.anio = anio;
        this.autor = autor;
        this.estado = estado;
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
    
    
}
