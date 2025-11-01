/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolAVL;

import com.mycompany.proyectofinaledd.backend.libro.Libro;

/**
 *
 * @author brandon
 */
public class NodoAVL{

    private Libro libro;
    private int altura;
    private NodoAVL nodoIzquierdo;
    private NodoAVL nodoDerecho;
    private int factorEquilibrio;
    
    public NodoAVL(Libro libro) {
        this.libro = libro;
        this.altura = 1;
        this.nodoIzquierdo = null;
        this.nodoDerecho = null;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVL getNodoIzquierdo() {
        return nodoIzquierdo;
    }

    public void setNodoIzquierdo(NodoAVL nodoIzquierdo) {
        this.nodoIzquierdo = nodoIzquierdo;
    }

    public NodoAVL getNodoDerecho() {
        return nodoDerecho;
    }

    public void setNodoDerecho(NodoAVL nodoDerecho) {
        this.nodoDerecho = nodoDerecho;
    }

    public int getFactorEquilibrio() {
        return factorEquilibrio;
    }

    public void setFactorEquilibrio(int factorEquilibrio) {
        this.factorEquilibrio = factorEquilibrio;
    }
    
    
    
}