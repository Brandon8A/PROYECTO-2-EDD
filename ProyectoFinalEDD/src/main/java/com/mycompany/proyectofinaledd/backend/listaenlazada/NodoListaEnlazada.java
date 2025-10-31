/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.listaenlazada;

/**
 *
 * @author brandon
 */
public class NodoListaEnlazada<T> {

    private T dato;
    private NodoListaEnlazada<T> siguiente;

    public NodoListaEnlazada(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoListaEnlazada<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaEnlazada<T> siguiente) {
        this.siguiente = siguiente;
    }
}