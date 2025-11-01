/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.listaenlazada;

/**
 *
 * @author brandon
 */
public class ListaEnlazadaDoble<T> {

    private NodoListaEnlazadaDoble<T> inicio;
    private NodoListaEnlazadaDoble<T> fin;
    private int tamanio = 0;
    
    /**
     * Metodo que almacena valores al final de la lista
     * @param nuevoValor valor a ingresar a la lista
     */
    public void agregarValorAlFinal(T nuevoValor){
        NodoListaEnlazadaDoble<T> nuevo = new NodoListaEnlazadaDoble<>(nuevoValor, tamanio);
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setAnterior(fin);
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
        if (inicio != null) {
            inicio.setAnterior(null);
        } else {
            fin = null;
        }
        this.tamanio--;
        return dato;
    }
    
    public void eliminarLista(){
        this.inicio = null;
        this.fin = null;
        this.tamanio = 0;
    }
    
    // Elimina un elemento específico
    public boolean eliminarValor(T dato) {
        NodoListaEnlazadaDoble<T> actual = inicio;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (actual.getAnterior() != null) {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                } else {
                    inicio = actual.getSiguiente();
                }

                if (actual.getSiguiente() != null) {
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                } else {
                    fin = actual.getAnterior();
                }
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    public boolean estaVacia(){
        return inicio == null;
    }
    
    public NodoListaEnlazadaDoble<T> getInicio() {
        return inicio;
    }

    public void setInicio(NodoListaEnlazadaDoble<T> inicio) {
        this.inicio = inicio;
    }

    public NodoListaEnlazadaDoble<T> getFin() {
        return fin;
    }

    public void setFin(NodoListaEnlazadaDoble<T> fin) {
        this.fin = fin;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
}
