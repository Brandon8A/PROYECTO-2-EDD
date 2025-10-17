/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo.arista;

/**
 *
 * @author brandon
 */
public class NodoArista {
    private Arista dato;
    private NodoArista siguiente;

    public NodoArista(Arista dato) {
        this.dato = dato;
    }

    public Arista getDato() {
        return dato;
    }

    public void setDato(Arista dato) {
        this.dato = dato;
    }

    public NodoArista getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoArista siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
