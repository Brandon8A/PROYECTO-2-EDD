/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

/**
 *
 * @author brandon
 */
public class Biblioteca {
    private String nombre;
    double tiempoIngreso, tiempoPreparacion, intervaloDespacho;

    public Biblioteca(String nombre, double tiempoIngreso, double tiempoPreparacion, double intervaloDespacho) {
        this.nombre = nombre;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoPreparacion = tiempoPreparacion;
        this.intervaloDespacho = intervaloDespacho;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String toString(){ 
        return nombre; 
    }
}
