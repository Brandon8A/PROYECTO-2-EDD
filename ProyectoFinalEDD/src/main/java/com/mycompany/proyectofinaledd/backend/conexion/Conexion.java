/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.conexion;

import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;

/*
 * @author brandon
 */
public class Conexion {
    private Biblioteca origen;
    private Biblioteca destino;
    private int pesoTiempo;
    private double pesoCosto;

    public Conexion(Biblioteca origen, Biblioteca destino, int pesoTiempo, double pesoCosto) {
        this.origen = origen;
        this.destino = destino;
        this.pesoTiempo = pesoTiempo;
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
    
    public int getPesoTiempo() {
        return pesoTiempo;
    }

    public void setPesoTiempo(int peso) {
        this.pesoTiempo = peso;
    }
    
}
