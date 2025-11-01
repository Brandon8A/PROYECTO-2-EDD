/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.listaenlazada;

/**
 *
 * @author brandon
 */
public class NodoListaEnlazadaDoble<T> {

    private T dato;
    private NodoListaEnlazadaDoble<T> siguiente;
    private NodoListaEnlazadaDoble<T> anterior;
    private int posicion;

    public NodoListaEnlazadaDoble(T dato, int posicion) {
        this.dato = dato;
        this.posicion = posicion;
        this.siguiente = null;
        this.anterior = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoListaEnlazadaDoble<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaEnlazadaDoble<T> siguiente) {
        this.siguiente = siguiente;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public NodoListaEnlazadaDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoListaEnlazadaDoble<T> anterior) {
        this.anterior = anterior;
    }
    
    
}