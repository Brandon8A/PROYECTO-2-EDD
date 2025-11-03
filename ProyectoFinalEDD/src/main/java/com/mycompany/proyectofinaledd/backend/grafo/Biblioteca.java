/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.arboles.arbolAVL.ArbolAVL;
import com.mycompany.proyectofinaledd.backend.arboles.arbolB.ArbolB;
import com.mycompany.proyectofinaledd.backend.arboles.arbolBMas.ArbolBMas;
import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble;
import com.mycompany.proyectofinaledd.backend.tablahash.TablaHashLibros;

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
    
    private ArbolAVL arbolAVL;
    private ArbolB arbolB;
    private ArbolBMas arbolBMas;
    private TablaHashLibros tablaHash;
    private ListaEnlazadaDoble<Libro> listaEnlazada;
    
    private ListaEnlazadaDoble<Libro> colaIngreso;
    private ListaEnlazadaDoble<Libro> colaTraspaso;
    private ListaEnlazadaDoble<Libro> colaSalida;

    public Biblioteca(String nombre, String ubicacion, int tiempoIngreso, int tiempoPreparacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoPreparacion = tiempoPreparacion;
        this.colaIngreso = new ListaEnlazadaDoble<>();
        this.colaSalida = new ListaEnlazadaDoble<>();
        this.colaTraspaso = new ListaEnlazadaDoble<>();
    }
    
    public Biblioteca(String id, String nombre, String ubicacion, int tiempoIngreso, int tiempoTraspaso, int intervaloDespacho) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoTraspaso = tiempoTraspaso;
        this.intervaloDespacho = intervaloDespacho;
        this.colaIngreso = new ListaEnlazadaDoble<>();
        this.colaSalida = new ListaEnlazadaDoble<>();
        this.colaTraspaso = new ListaEnlazadaDoble<>();
        this.arbolAVL = new ArbolAVL();
        this.arbolB = new ArbolB(3);
        this.arbolBMas = new ArbolBMas(3);
        this.tablaHash = new TablaHashLibros(127);
        this.listaEnlazada = new ListaEnlazadaDoble<>();
    }

    public void generarImagenesEstructuras(){
        this.arbolAVL.generarImagen("ArbolAVL", "imagenes/biblioteca_"+ this.id);
        this.arbolB.generarImagen("ArbolB", "imagenes/biblioteca_" + this.id);
        this.arbolBMas.generarImagen("Arbol_B_Mas", "imagenes/biblioteca_" + this.id);
        this.listaEnlazada.generarImagen("ListaEnlazada", "imagenes/biblioteca_"+ this.id);
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

    public ArbolAVL getArbolAVL() {
        return arbolAVL;
    }

    public void setArbolAVL(ArbolAVL arbolAVL) {
        this.arbolAVL = arbolAVL;
    }

    public ArbolB getArbolB() {
        return arbolB;
    }

    public void setArbolB(ArbolB arbolB) {
        this.arbolB = arbolB;
    }

    public ArbolBMas getArbolBMas() {
        return arbolBMas;
    }

    public void setArbolBMas(ArbolBMas arbolBMas) {
        this.arbolBMas = arbolBMas;
    }

    public TablaHashLibros getTablaHash() {
        return tablaHash;
    }

    public void setTablaHash(TablaHashLibros tablaHash) {
        this.tablaHash = tablaHash;
    }

    public ListaEnlazadaDoble<Libro> getListaEnlazada() {
        return listaEnlazada;
    }

    public void setListaEnlazada(ListaEnlazadaDoble<Libro> listaEnlazada) {
        this.listaEnlazada = listaEnlazada;
    }
    
    
}