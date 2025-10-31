/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.conexion;

import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;

/**
 *
 * @author brandon
 */
public class Conexion {
    private Biblioteca origen;
    private Biblioteca destino;
    private double peso;
    private double pesoCosto;

    public Conexion(Biblioteca origen, Biblioteca destino, double pesoTiempo, double pesoCosto) {
        this.origen = origen;
        this.destino = destino;
        this.peso = pesoTiempo;
        this.pesoCosto = pesoCosto;
    }

    public Biblioteca getOrigen() {
        return origen;
    }

    public void setOrigen(Biblioteca origen) {
        this.origen = origen;
    }

    public Biblioteca getDestino() {
        return destino;
    }

    public void setDestino(Biblioteca destino) {
        this.destino = destino;
    }

    public double getPesoCosto() {
        return pesoCosto;
    }

    public void setPesoCosto(double pesoCosto) {
        this.pesoCosto = pesoCosto;
    }
    
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
}
