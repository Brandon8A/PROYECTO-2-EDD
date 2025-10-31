/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazada;

/**
 *Representa un biblioteca
 * @author brandon
 */
public class NodoGrafo {
    private Biblioteca biblioteca;
    private ListaEnlazada<Conexion> conexiones;

    public NodoGrafo(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.conexiones = new ListaEnlazada<>();//Lista de conexiones salientes
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public ListaEnlazada<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ListaEnlazada<Conexion> conexiones) {
        this.conexiones = conexiones;
    }
    
    
}
