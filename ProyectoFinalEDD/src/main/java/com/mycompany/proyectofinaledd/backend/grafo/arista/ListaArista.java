/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo.arista;

/**
 *
 * @author brandon
 */
public class ListaArista {
    private NodoArista inicio;
    
    /**
     * Metodo que agrega una nueva arista a la lista
     * @param nuevaArista 
     */
    public void agregarArista(Arista nuevaArista){
        NodoArista  nuevo = new NodoArista(nuevaArista);
        if (this.inicio == null) {
            this.inicio = nuevo;
        } else {
            NodoArista curArista = inicio;
            while (curArista.getSiguiente() != null) {                
                curArista = curArista.getSiguiente();
                curArista.setSiguiente(nuevo);
            }
        }
    }

    public NodoArista getInicio() {
        return inicio;
    }
    
    
}
