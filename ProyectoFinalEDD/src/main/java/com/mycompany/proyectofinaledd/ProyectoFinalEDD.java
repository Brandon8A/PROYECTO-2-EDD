/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinaledd;

import com.mycompany.proyectofinaledd.backend.Controlador;
import com.mycompany.proyectofinaledd.frontend.inicio.Inicio;

/**
 *
 * @author brandon
 */
public class ProyectoFinalEDD {

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Inicio iniciar = new Inicio(controlador);
        controlador.setInicio(iniciar);
        iniciar.setLocationRelativeTo(null);
        iniciar.setVisible(true);
        
        /*
        MatrizAdyacenciaCostos grafo = new MatrizAdyacenciaCostos(4);
        
        grafo.agregarArista(0, 1, 5);
        grafo.agregarArista(0, 2, 9);
        grafo.agregarArista(1, 3, 3);
        grafo.agregarArista(2, 3, 1);
        grafo.agregarArista(3, 0, 8);
        
        grafo.mostrarMatriz();

        Grafo g = new Grafo(6);
        
        int a = g.agregarBiblioteca(new Biblioteca("Central", 5, 3, 2));
        int b = g.agregarBiblioteca(new Biblioteca("Norte", 4, 2, 1.5));
        int c = g.agregarBiblioteca(new Biblioteca("Sur", 6, 2.5, 2.5));
        int d = g.agregarBiblioteca(new Biblioteca("Este", 3.5, 2, 1));
        int e = g.agregarBiblioteca(new Biblioteca("Oeste", 4.5, 2.5, 2));
        
        g.agregarConexion(a, b, 10, 5, true);
        g.agregarConexion(a, c, 20, 8, true);
        g.agregarConexion(b, d, 8, 3, true);
        g.agregarConexion(d, c, 6, 2.5, true);
        g.agregarConexion(c, e, 12, 4, true);
        g.agregarConexion(e, a, 15, 5.5, true);
        
        g.mostrarMatrizAdyacencia();
*/
    }
}
