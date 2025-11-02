/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolBMas;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author brandon
 */
public class ArbolBMas {

    private NodoArbolBMas raiz;
    private int gradoMinimo;

    public ArbolBMas(int gradoMinimo) {
        this.gradoMinimo = gradoMinimo;
        this.raiz = null;
    }

    // Insertar libro (ordenado por género)
    public void insertar(Libro libro) {
        if (raiz == null) {
            raiz = new NodoArbolBMas(gradoMinimo, true);
            raiz.getClaves()[0] = libro;
            raiz.setSiguienteHoja(null);
            raiz.insertarNoLleno(libro);
        } else {
            if (raiz.getNumClaves() == 2 * gradoMinimo) {
                NodoArbolBMas nuevaRaiz = new NodoArbolBMas(gradoMinimo, false);
                nuevaRaiz.getHijos()[0] = raiz;
                nuevaRaiz.dividirHijo(0, raiz);
                int i = 0;
                if (nuevaRaiz.getClaves()[0].getGenero().compareToIgnoreCase(libro.getGenero()) < 0) i++;
                nuevaRaiz.getHijos()[i].insertarNoLleno(libro);
                raiz = nuevaRaiz;
            } else {
                raiz.insertarNoLleno(libro);
            }
        }
    }

    // Recorrer por género (secuencial por hojas)
    public void recorrerPorGenero() {
        if (raiz == null) {
            System.out.println("Árbol B+ vacío");
            return;
        }

        NodoArbolBMas nodo = raiz;
        while (!nodo.isHoja()) {
            nodo = nodo.getHijos()[0];
        }

        while (nodo != null) {
            for (int i = 0; i < nodo.getNumClaves(); i++) {
                Libro libro = nodo.getClaves()[i];
                System.out.println(libro.getGenero() + " → " + libro.getTitulo());
            }
            nodo = nodo.getSiguienteHoja();
        }
    }

    // Generar imagen con Graphviz
    public void generarImagen(String nombreImagen, String nombreCarpeta) {
        try {
            File carpeta = new File(nombreCarpeta);
            if (!carpeta.exists()) carpeta.mkdirs();

            String rutaDot = nombreCarpeta + "/" + nombreImagen + ".dot";
            String rutaPng = nombreCarpeta + "/" + nombreImagen + ".png";

            try (PrintWriter out = new PrintWriter(rutaDot)) {
                out.println("digraph BPlusTree {");
                out.println("  node [shape=record, style=filled, fillcolor=lightgoldenrodyellow];");
                out.println("  rankdir=TB;");
                if (raiz != null) raiz.generarDotRec(out);
                out.println("}");
            }

            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", rutaDot, "-o", rutaPng);
            pb.start();

            System.out.println("✅ Imagen del Árbol B+ (por género) generada: " + rutaPng);

        } catch (IOException e) {
            System.err.println("❌ Error al generar la imagen del árbol B+: " + e.getMessage());
        }
    }
}
