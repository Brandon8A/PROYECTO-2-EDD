/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.grafo.arista.Arista;
import com.mycompany.proyectofinaledd.backend.grafo.arista.ListaArista;

/**
 *
 * @author brandon
 */
public class Grafo {
    private Biblioteca[] libs;
    private ListaArista[] adj;
    private double[][] matrizAdyacenciaTiempos;
    private double [][] matrizAdyacenciaCostos;
    private int tamanio, n = 0;

    public Grafo(int tamanio) {
        this.tamanio = tamanio;
        this.libs = new Biblioteca[tamanio];
        this.adj = new ListaArista[tamanio];
        this.matrizAdyacenciaCostos = new double[tamanio][tamanio];
        this.matrizAdyacenciaTiempos = new double[tamanio][tamanio];
        for (int i = 0; i < tamanio; i++) {
            adj[i] = new ListaArista();
            for (int j = 0; j < tamanio; j++) {
                matrizAdyacenciaTiempos[i][j] = Double.POSITIVE_INFINITY;
                matrizAdyacenciaCostos[i][j] = Double.POSITIVE_INFINITY;
            }
            matrizAdyacenciaTiempos[i][i] = 0;
            matrizAdyacenciaCostos[i][i] = 0;
        }
    }
    
    /* Mostrar matriz de adyacencia */
    public void mostrarMatrizAdyacencia(){
        System.out.printf("%10s","");
        for(int i=0;i<n;i++) System.out.printf("%10s",libs[i].getNombre());
        System.out.println();
        for(int i=0;i<n;i++){
            System.out.printf("%10s",libs[i].getNombre());
            for(int j=0;j<n;j++){
                if(Double.isInfinite(matrizAdyacenciaTiempos[i][j])) System.out.printf("%10s","∞");
                else System.out.printf("%10.1f",matrizAdyacenciaTiempos[i][j]);
            }
            System.out.println();
        }
    }
    
    public int addLibrary(Biblioteca l){
        libs[n]=l;
        return n++;
    }
    
    public void agregarConexion(int origen,int destino,double tiempo,double costo,boolean bidir){
        adj[origen].agregarArista(new Arista(destino,tiempo,costo,bidir));
        matrizAdyacenciaTiempos[origen][destino]=tiempo;
        if(bidir){
            adj[destino].agregarArista(new Arista(origen,tiempo,costo,bidir));
            matrizAdyacenciaTiempos[destino][origen]=tiempo;
        }
    }
}
