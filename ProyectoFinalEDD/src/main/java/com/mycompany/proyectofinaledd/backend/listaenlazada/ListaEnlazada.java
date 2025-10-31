/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.listaenlazada;

/**
 *
 * @author brandon
 */
public class ListaEnlazada<T> {

    private NodoListaEnlazada<T> inicio;
    private NodoListaEnlazada<T> fin;
    private int tamanio;
    
    /**
     * Metodo que almacena valores al final de la lista
     * @param nuevoValor valor a ingresar a la lista
     */
    public void agregarValorAlFinal(T nuevoValor){
        NodoListaEnlazada<T> nuevo = new NodoListaEnlazada<>(nuevoValor);
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    /**
     * Funcion que se encarga de quitar elementos de enfrente FIFO
     * @return returna el valor de enfrente de la lista
     */
    public T quitarElementoEnfrente(){
        if (estaVacia()) {
            return null;
        }
        T dato = inicio.getDato();
        inicio = inicio.getSiguiente();
        if (inicio == null) {
            this.fin = null;
        }
        this.tamanio--;
        return dato;
    }
    
    public void eliminarLista(){
        this.inicio = null;
        this.tamanio = 0;
    }
    
    public boolean estaVacia(){
        return inicio == null;
    }
    
    public NodoListaEnlazada<T> getInicio() {
        return inicio;
    }

    public void setInicio(NodoListaEnlazada<T> inicio) {
        this.inicio = inicio;
    }

    public NodoListaEnlazada<T> getFin() {
        return fin;
    }

    public void setFin(NodoListaEnlazada<T> fin) {
        this.fin = fin;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
}
