/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.biblioteca;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble;

/**
 *
 * @author brandon
 */
public class Biblioteca {
    private String nombre;
    private String ubicacion;
    private int tiempoIngreso;
    private int tiempoTraspaso;
    private String prioridad;
    
    private ListaEnlazadaDoble<Libro> colaIngreso = new ListaEnlazadaDoble<>();
    private ListaEnlazadaDoble<Libro> colaTraspaso = new ListaEnlazadaDoble<>();
    private ListaEnlazadaDoble<Libro> colaSalida = new ListaEnlazadaDoble<>();

    public Biblioteca(String nombre, String ubicacion, int tiempoIngreso, int tiempoTraspaso) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoTraspaso = tiempoTraspaso;
    }

    /**
     * Metodo que llama al metodo dentro del objeto de lista enlazada que se 
     * encarga de agregar valores al final de la cola
     * @param libro valor que se ingresará a la cola de valores
     */
    public void agregarLibroAColaIngreso(Libro libro){
        this.colaIngreso.agregarValorAlFinal(libro);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getTiempoIngreso() {
        return tiempoIngreso;
    }

    public void setTiempoIngreso(int tiempoIngreso) {
        this.tiempoIngreso = tiempoIngreso;
    }

    public int getTiempoTraspaso() {
        return tiempoTraspaso;
    }

    public void setTiempoTraspaso(int tiempoTraspaso) {
        this.tiempoTraspaso = tiempoTraspaso;
    }

    public ListaEnlazadaDoble<Libro> getColaIngreso() {
        return colaIngreso;
    }

    public ListaEnlazadaDoble<Libro> getColaTraspaso() {
        return colaTraspaso;
    }

    public ListaEnlazadaDoble<Libro> getColaSalida() {
        return colaSalida;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    
    
}
