/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolBPrueba;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.util.ArrayList;
import java.util.List;

class NodoB {

    private List<Integer> claves;        // Años
    private List<List<Libro>> valores;   // Varios libros pueden tener mismo año
    private List<NodoB> hijos;

    boolean hoja;

    public NodoB(boolean hoja) {
        this.hoja = hoja;
        this.claves = new ArrayList<>();
        this.valores = new ArrayList<>();
        this.hijos = new ArrayList<>();
    }

    public boolean esHoja() {
        return hoja;
    }

    public List<Integer> getClaves() {
        return claves;
    }

    public void setClaves(List<Integer> claves) {
        this.claves = claves;
    }

    public List<List<Libro>> getValores() {
        return valores;
    }

    public void setValores(List<List<Libro>> valores) {
        this.valores = valores;
    }

    public List<NodoB> getHijos() {
        return hijos;
    }

    public void setHijos(List<NodoB> hijos) {
        this.hijos = hijos;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }
    
    
}