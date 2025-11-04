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
public class NodoAVL {

    private Libro libro;
    private NodoAVL izquierda;
    private NodoAVL derecha;
    private int altura;
    private int factorEquilibrio;

    public NodoAVL(Libro libro) {
        this.libro = libro;
        this.altura = 1; // nuevo nodo → altura = 1
    }

    public Libro getLibro() {
        return libro;
    }

    public NodoAVL getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoAVL izquierda) {
        this.izquierda = izquierda;
    }

    public NodoAVL getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoAVL derecha) {
        this.derecha = derecha;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getFactorEquilibrio() {
        return factorEquilibrio;
    }

    public void setFactorEquilibrio(int factorEquilibrio) {
        this.factorEquilibrio = factorEquilibrio;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
    
    
}