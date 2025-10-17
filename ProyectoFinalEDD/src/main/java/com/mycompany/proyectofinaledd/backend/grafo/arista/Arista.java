/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo.arista;

/**
 *
 * @author brandon
 */
public class Arista {
    private int origen;
    private double tiempo;
    private double costo;
    private boolean bidireccional;

    public Arista(int origen, double tiempo, double costo, boolean bidireccional) {
        this.origen = origen;
        this.tiempo = tiempo;
        this.costo = costo;
        this.bidireccional = bidireccional;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isBidireccional() {
        return bidireccional;
    }

    public void setBidireccional(boolean bidireccional) {
        this.bidireccional = bidireccional;
    }
    
    
}
