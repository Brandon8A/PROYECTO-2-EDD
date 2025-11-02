/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolBMas;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.PrintWriter;

/**
 *
 * @author brandon
 */
public class NodoArbolBMas {

    private Libro[] claves;
    private NodoArbolBMas[] hijos;
    private int numClaves;
    private boolean hoja;
    private int gradoMinimo;
    private NodoArbolBMas siguienteHoja; // Enlace entre hojas (recorrido secuencial)

    public NodoArbolBMas(int gradoMinimo, boolean hoja) {
        this.gradoMinimo = gradoMinimo;
        this.hoja = hoja;
        this.claves = new Libro[2 * gradoMinimo];
        this.hijos = new NodoArbolBMas[2 * gradoMinimo + 1];
        this.numClaves = 0;
        this.siguienteHoja = null;
    }

    // Inserta un libro en el nodo (no lleno)
    public void insertarNoLleno(Libro libro) {
        int i = numClaves - 1;

        if (hoja) {
            // Desplazar claves para mantener el orden por género
            while (i >= 0 && libro.getGenero().compareToIgnoreCase(claves[i].getGenero()) < 0) {
                claves[i + 1] = claves[i];
                i--;
            }
            claves[i + 1] = libro;
            numClaves++;
        } else {
            while (i >= 0 && libro.getGenero().compareToIgnoreCase(claves[i].getGenero()) < 0) {
                i--;
            }
            i++;
            if (hijos[i].numClaves == 2 * gradoMinimo) {
                dividirHijo(i, hijos[i]);
                if (libro.getGenero().compareToIgnoreCase(claves[i].getGenero()) > 0) {
                    i++;
                }
            }
            hijos[i].insertarNoLleno(libro);
        }
    }

    // Divide un nodo hijo lleno
    public void dividirHijo(int i, NodoArbolBMas y) {
        NodoArbolBMas z = new NodoArbolBMas(y.gradoMinimo, y.hoja);
        z.numClaves = gradoMinimo;

        // Copiar claves a z
        for (int j = 0; j < gradoMinimo; j++) {
            z.claves[j] = y.claves[j + gradoMinimo];
        }

        if (!y.hoja) {
            for (int j = 0; j <= gradoMinimo; j++) {
                z.hijos[j] = y.hijos[j + gradoMinimo];
            }
        } else {
            // Enlazar hojas (importante en B+)
            z.siguienteHoja = y.siguienteHoja;
            y.siguienteHoja = z;
        }

        y.numClaves = gradoMinimo;

        // Mover hijos del nodo actual
        for (int j = numClaves; j >= i + 1; j--) {
            hijos[j + 1] = hijos[j];
        }
        hijos[i + 1] = z;

        // Mover claves guía
        for (int j = numClaves - 1; j >= i; j--) {
            claves[j + 1] = claves[j];
        }
        claves[i] = y.claves[gradoMinimo - 1];
        numClaves++;
    }

    // Generar contenido DOT para Graphviz
    public void generarDotRec(PrintWriter out) {
        out.print("  node" + this.hashCode() + " [label=\"");

        for (int i = 0; i < numClaves; i++) {
            if (claves[i] != null) {
                out.print(claves[i].getGenero() + "\\n" + claves[i].getTitulo());
                if (i < numClaves - 1) out.print(" | ");
            }
        }

        out.println("\"];");

        if (!hoja) {
            for (int i = 0; i <= numClaves; i++) {
                if (hijos[i] != null) {
                    out.println("  node" + this.hashCode() + " -> node" + hijos[i].hashCode() + ";");
                    hijos[i].generarDotRec(out);
                }
            }
        }
    }

    // Getters y Setters
    public Libro[] getClaves() { 
        return claves; 
    }
    
    public NodoArbolBMas[] getHijos() { 
        return hijos; 
    }
    
    public int getNumClaves() { 
        return numClaves; 
    }
    
    public boolean isHoja() { 
        return hoja; 
    }
    
    public NodoArbolBMas getSiguienteHoja() { 
        return siguienteHoja; 
    }
    
    public void setSiguienteHoja(NodoArbolBMas siguienteHoja) { 
        this.siguienteHoja = siguienteHoja; 
    }
}
