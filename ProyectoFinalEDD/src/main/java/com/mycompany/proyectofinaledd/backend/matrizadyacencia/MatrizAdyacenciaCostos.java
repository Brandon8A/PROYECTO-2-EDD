/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.matrizadyacencia;

/**
 *
 * @author brandon
 */
public class MatrizAdyacenciaCostos {

    private int matriz[][];
    private int cantidadVertices;

    public MatrizAdyacenciaCostos(int cantidadVertices) {
        this.cantidadVertices = cantidadVertices;
        this.matriz = new int[cantidadVertices][cantidadVertices];
    }

    /**
     * Metodo que agrega (crea) una arista a la matriz con su peso(costo)
     *
     * @param origen punto de partida del grafo
     * @param destino punto hacia donde llega el arco del grafo
     * @param costo valor asignado entre los dos nodos del grafo (origen,
     * destino)
     */
    public void agregarArista(int origen, int destino, int costo) {
        this.matriz[origen][destino] = costo;
    }

    public void mostrarMatriz() {
        System.out.println("Matriz de adyacencia: ");
        for (int i = 0; i < cantidadVertices; i++) {
            if (i == 0) {
                System.out.println("  a b c d");
                System.out.print("a ");
            } else if (i == 1) {
                System.out.print("b ");
            } else if (i == 2){
                System.out.print("c ");
            } else if (i == 3) {
                System.out.print("d ");
            }
            for (int j = 0; j < cantidadVertices; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

}
