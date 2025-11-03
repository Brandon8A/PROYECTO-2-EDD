/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.tablahash;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author brandon
 */
public class TablaHashLibros {

    private NodoHash[] tabla;
    private int tamaño;

    public TablaHashLibros(int tamaño) {
        this.tamaño = tamaño;
        this.tabla = new NodoHash[tamaño];
    }

    // -------------------------------------------------
    // Función de dispersión: PLEGAMIENTO
    // -------------------------------------------------
    private int funcionHash(String isbn) {
        // Elimina guiones por si vienen en formato "978-0-306-40615-7"
        isbn = isbn.replaceAll("-", "");
        int suma = 0;

        // Agrupamos en grupos de 2 o 3 dígitos
        for (int i = 0; i < isbn.length(); i += 2) {
            int fin = Math.min(i + 2, isbn.length());
            String parte = isbn.substring(i, fin);
            suma += Integer.parseInt(parte);
        }

        return suma % tamaño;
    }

    // -------------------------------------------------
    // Insertar libro (clave única ISBN)
    // -------------------------------------------------
    public void insertar(Libro libro) {
        int indice = funcionHash(libro.getISBN());

        NodoHash nuevo = new NodoHash(libro);
        if (tabla[indice] == null) {
            tabla[indice] = nuevo;
        } else {
            // Encadenamiento (colisión)
            NodoHash actual = tabla[indice];
            while (actual.getSiguiente() != null) {
                if (actual.getLibro().getISBN().equals(libro.getISBN())) {
                    System.out.println("⚠️ El ISBN ya existe, no se puede insertar duplicado: " + libro.getISBN());
                    return;
                }
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }

    // -------------------------------------------------
    // Buscar libro por ISBN
    // -------------------------------------------------
    public Libro buscar(String isbn) {
        int indice = funcionHash(isbn);
        NodoHash actual = tabla[indice];

        while (actual != null) {
            if (actual.getLibro().getISBN().equals(isbn)) {
                return actual.getLibro();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    // -------------------------------------------------
    // Mostrar toda la tabla
    // -------------------------------------------------
    public void mostrarTabla() {
        for (int i = 0; i < tamaño; i++) {
            System.out.print("[" + i + "] → ");
            NodoHash actual = tabla[i];
            if (actual == null) {
                System.out.println("∅");
            } else {
                while (actual != null) {
                    Libro libro = actual.getLibro();
                    System.out.print(libro.getISBN() + " (" + libro.getTitulo() + ")");
                    if (actual.getSiguiente() != null) {
                        System.out.print(" → ");
                    }
                    actual = actual.getSiguiente();
                }
                System.out.println();
            }
        }
    }

    // -------------------------------------------------
    // Generar imagen con Graphviz
    // -------------------------------------------------
    public void generarImagen(String nombreImagen, String nombreCarpeta) {
        try {
            File carpeta = new File(nombreCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String rutaDot = nombreCarpeta + "/" + nombreImagen + ".dot";
            String rutaPng = nombreCarpeta + "/" + nombreImagen + ".png";

            try (PrintWriter out = new PrintWriter(rutaDot)) {
                out.println("digraph TablaHash {");
                out.println("  rankdir=LR;");
                out.println("  node [shape=record, style=filled, fillcolor=lightcyan];");
                out.println("  edge [color=gray50, arrowsize=0.8];");

                for (int i = 0; i < tamaño; i++) {
                    String nodoIndice = "indice" + i;
                    out.println("  " + nodoIndice + " [label=\"[" + i + "]\", fillcolor=lightblue];");

                    NodoHash actual = tabla[i];
                    NodoHash anterior = null;
                    int contador = 0;

                    while (actual != null) {
                        String nodoLibro = "n" + i + "_" + contador;
                        Libro libro = actual.getLibro();
                        out.println("  " + nodoLibro + " [label=\"ISBN: " + libro.getISBN() + "\\n"
                                + libro.getTitulo().replace("\"", "") + "\", fillcolor=lightgoldenrodyellow];");

                        if (contador == 0) {
                            out.println("  " + nodoIndice + " -> " + nodoLibro + ";");
                        } else {
                            out.println("  n" + i + "_" + (contador - 1) + " -> " + nodoLibro + ";");
                        }

                        anterior = actual;
                        actual = actual.getSiguiente();
                        contador++;
                    }
                }

                out.println("}");
            }

            // Ejecutar Graphviz
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", rutaDot, "-o", rutaPng);
            pb.start();

            System.out.println("✅ Imagen de la Tabla Hash generada: " + rutaPng);

        } catch (IOException e) {
            System.err.println("❌ Error al generar la imagen de la tabla hash: " + e.getMessage());
        }
    }

    // -------------------------------------------------
// Eliminar libro por ISBN
// -------------------------------------------------
    public boolean eliminarPorISBN(String isbn) {
        int indice = funcionHash(isbn);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {
            Libro libro = actual.getLibro();
            if (libro.getISBN().equalsIgnoreCase(isbn)) {
                // Caso 1: el nodo a eliminar es la cabeza de la lista
                if (anterior == null) {
                    tabla[indice] = actual.getSiguiente();
                } else {
                    // Caso 2: el nodo está en medio o final
                    anterior.setSiguiente(actual.getSiguiente());
                }
                System.out.println("🗑️Libro eliminado (ISBN: " + isbn + ") del índice [" + indice + "]");
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        System.out.println("⚠️ No se encontró ningún libro con ISBN: " + isbn);
        return false;
    }
}
