/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolB;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brandon-ochoa
 */
public class ArbolB {

    private NodoArbolB raiz;
    private int t;

    public ArbolB(int t) {
        this.t = t;

    }

    // ---- Recorre el árbol ----
    public void traverse() {
        if (raiz != null) {
            raiz.traverse();
        }
    }

    // ---- Búsqueda ----
    public NodoArbolB search(Libro k) {
        return (raiz == null) ? null : raiz.search(k);
    }

    // ---- Inserción ----
    public void insert(Libro k) {
        if (raiz == null) {
            raiz = new NodoArbolB(t, true);
            raiz.getClaves()[0] = k;
            raiz.setNumeroActualClaves(1);
        } else {
            if (raiz.getNumeroActualClaves() == 2 * t - 1) {
                NodoArbolB s = new NodoArbolB(t, false);
                s.getNodosHijos()[0] = raiz;
                s.dividirNodoHijoLlenoEnDos(0, raiz);

                int i = 0;
                if (s.getClaves()[0].getAnio() < k.getAnio()) {
                    i++;
                }
                s.getNodosHijos()[i].insertarEnNodoNoLleno(k);
                raiz = s;
            } else {
                raiz.insertarEnNodoNoLleno(k);
            }
        }
    }

    public void generarImagen(String nombreImagen, String carpetaDestino) {
        try {
            // Crear carpeta si no existe
            File carpeta = new File(carpetaDestino);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Rutas completas
            String rutaDot = carpetaDestino + "/" + nombreImagen + ".dot";
            String rutaPng = carpetaDestino + "/" + nombreImagen + ".png";

            // Crear archivo DOT
            try (PrintWriter out = new PrintWriter(rutaDot)) {
                out.println("digraph BTree {");
                out.println("  node [shape=record, style=filled, fillcolor=lightgrey];");
                if (this.raiz != null) {
                    this.raiz.generateDotRec(out);
                }
                out.println("}");
            }

            // Ejecutar Graphviz
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", rutaDot, "-o", rutaPng);
            pb.start();

            System.out.println("✅ Imagen del árbol B generada en: " + rutaPng);

        } catch (IOException e) {
            System.out.println("⚠ Error generando imagen del árbol B: " + e.getMessage());
        }
    }

    public List<Libro> buscarPorRango(int anioInicio, int anioFin) {
        List<Libro> resultados = new ArrayList<>();

        if (raiz == null) {
            System.out.println("⚠ Árbol vacío");
            return resultados;
        }

        raiz.buscarPorRango(anioInicio, anioFin, resultados);
        System.out.println("✅ Se encontraron " + resultados.size() + " libros entre " + anioInicio + " y " + anioFin);
        return resultados;
    }

    public void eliminar(Libro k) {
        if (raiz == null) {
            System.out.println("⚠ Árbol vacío, no hay nada que eliminar");
            return;
        }

        raiz.eliminar(k);

        // Si la raíz se quedó sin claves y no es hoja, reducimos la altura del árbol
        if (raiz.getNumeroActualClaves() == 0) {
            if (raiz.isHoja()) {
                raiz = null;
            } else {
                raiz = raiz.getNodosHijos()[0];
            }
        }
    }
}
