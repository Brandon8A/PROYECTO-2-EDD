/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble;

/**
 *
 * @author brandon
 */
public class Biblioteca {
    
    private String id;
    private String nombre;
    private String ubicacion;
    private int tiempoIngreso;
    private int tiempoTraspaso;
    private int tiempoPreparacion;
    private int intervaloDespacho;
    
    private ListaEnlazadaDoble<Libro> colaIngreso;
    private ListaEnlazadaDoble<Libro> colaTraspaso;
    private ListaEnlazadaDoble<Libro> colaSalida;

    public Biblioteca(String nombre, String ubicacion, int tiempoIngreso, int tiempoPreparacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoPreparacion = tiempoPreparacion;
    }
    
    public Biblioteca(String id, String nombre, String ubicacion, int tiempoIngreso, int tiempoTraspaso, int intervaloDespacho) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoTraspaso = tiempoTraspaso;
        this.intervaloDespacho = intervaloDespacho;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String toString(){ 
        return nombre; 
    }

    public double getTiempoIngreso() {
        return tiempoIngreso;
    }

    public void setTiempoIngreso(int tiempoIngreso) {
        this.tiempoIngreso = tiempoIngreso;
    }

    public double getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public double getIntervaloDespacho() {
        return intervaloDespacho;
    }

    public void setIntervaloDespacho(int intervaloDespacho) {
        this.intervaloDespacho = intervaloDespacho;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ListaEnlazadaDoble<Libro> getColaIngreso() {
        return colaIngreso;
    }

    public ListaEnlazadaDoble<Libro> getColaTraspaso() {
        return colaTraspaso;
    }

    public void setColaTraspaso(ListaEnlazadaDoble<Libro> colaTraspaso) {
        this.colaTraspaso = colaTraspaso;
    }

    public ListaEnlazadaDoble<Libro> getColaSalida() {
        return colaSalida;
    }

    public void setColaSalida(ListaEnlazadaDoble<Libro> colaSalida) {
        this.colaSalida = colaSalida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTiempoTraspaso() {
        return tiempoTraspaso;
    }

    public void setTiempoTraspaso(int tiempoTraspaso) {
        this.tiempoTraspaso = tiempoTraspaso;
    }
}