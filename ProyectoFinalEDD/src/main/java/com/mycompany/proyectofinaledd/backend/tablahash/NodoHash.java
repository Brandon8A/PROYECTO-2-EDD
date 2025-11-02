/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.tablahash;

import com.mycompany.proyectofinaledd.backend.libro.Libro;

/**
 *
 * @author brandon
 */
public class NodoHash {
    private Libro libro;
    private NodoHash siguiente;

    public NodoHash(Libro libro) {
        this.libro = libro;
        this.siguiente = null;
    }

    public Libro getLibro() {
        return libro;
    }

    public NodoHash getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoHash siguiente) {
        this.siguiente = siguiente;
    }
}
