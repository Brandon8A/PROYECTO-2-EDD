/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolB;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author brandon
 * @param <K>
 * @param <V> 
 */
class NodoB<K extends Comparable<K>, V> {
    private List<K> claves;          // Lista de claves ordenadas (ej: años)
    private List<List<V>> valores;   // Cada clave tiene su lista de valores (ej: libros del mismo año)
    private List<NodoB<K, V>> hijos; // Enlaces a los hijos (solo si no es hoja)
    private boolean esHoja;          // Indica si el nodo es hoja

    public NodoB(boolean esHoja) {
        this.esHoja = esHoja;
        this.claves = new ArrayList<>();
        this.valores = new ArrayList<>();
        this.hijos = new ArrayList<>();
    }

    // ----------------------------
    // Métodos Getters y Setters
    // ----------------------------
    public boolean esHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public List<K> getClaves() {
        return claves;
    }

    public List<List<V>> getValores() {
        return valores;
    }

    public List<NodoB<K, V>> getHijos() {
        return hijos;
    }

    // ----------------------------
    // Métodos auxiliares
    // ----------------------------

    /**
     * Agrega una nueva clave y su valor al nodo en la posición adecuada (manteniendo orden).
     */
    public void insertarClaveOrdenada(K clave, V valor) {
        int i = 0;
        while (i < claves.size() && clave.compareTo(claves.get(i)) > 0) {
            i++;
        }

        // Si la clave ya existe, agregar el valor a su lista
        if (i < claves.size() && clave.compareTo(claves.get(i)) == 0) {
            valores.get(i).add(valor);
            return;
        }

        // Insertar nueva clave y lista de valores
        claves.add(i, clave);
        List<V> listaValores = new ArrayList<>();
        listaValores.add(valor);
        valores.add(i, listaValores);
    }

    /**
     * Retorna el número de claves almacenadas en el nodo.
     */
    public int getNumeroClaves() {
        return claves.size();
    }

    /**
     * Imprime las claves y sus valores asociados (para depuración).
     */
    public void imprimirNodo() {
        System.out.print("[");
        for (int i = 0; i < claves.size(); i++) {
            System.out.print(claves.get(i) + " -> " + valores.get(i).size() + " libros");
            if (i < claves.size() - 1) System.out.print(" | ");
        }
        System.out.println("]");
    }
}
