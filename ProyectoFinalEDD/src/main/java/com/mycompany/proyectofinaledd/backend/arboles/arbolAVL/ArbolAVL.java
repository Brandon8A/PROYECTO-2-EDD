/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolAVL;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brandon
 */
public class ArbolAVL {

    private NodoAVL raiz;

    public NodoAVL getRaiz() {
        return raiz;
    }

    public void insertar(Libro libro) {
        raiz = insertarNodo(raiz, libro);
    }

    private NodoAVL insertarNodo(NodoAVL nodo, Libro libro) {
        if (nodo == null) {
            return new NodoAVL(libro);
        }

        int cmp = libro.getTitulo().compareToIgnoreCase(nodo.getLibro().getTitulo());

        if (cmp < 0) {
            nodo.setIzquierda(insertarNodo(nodo.getIzquierda(), libro));
        } else if (cmp > 0) {
            nodo.setDerecha(insertarNodo(nodo.getDerecha(), libro));
        } else {
            return nodo; // No insertar duplicados
        }

        actualizarAlturaYFE(nodo);

        return balancear(nodo);
    }

    private void actualizarAlturaYFE(NodoAVL nodo) {
        int alturaIzq = (nodo.getIzquierda() == null) ? 0 : nodo.getIzquierda().getAltura();
        int alturaDer = (nodo.getDerecha() == null) ? 0 : nodo.getDerecha().getAltura();

        nodo.setAltura(1 + Math.max(alturaIzq, alturaDer));
        nodo.setFactorEquilibrio(alturaIzq - alturaDer);
    }

    private NodoAVL balancear(NodoAVL nodo) {
        int fe = nodo.getFactorEquilibrio();

        // LL
        if (fe > 1 && nodo.getIzquierda().getFactorEquilibrio() >= 0) {
            return rotacionDerecha(nodo);
        }

        // LR
        if (fe > 1 && nodo.getIzquierda().getFactorEquilibrio() < 0) {
            nodo.setIzquierda(rotacionIzquierda(nodo.getIzquierda()));
            return rotacionDerecha(nodo);
        }

        // RR
        if (fe < -1 && nodo.getDerecha().getFactorEquilibrio() <= 0) {
            return rotacionIzquierda(nodo);
        }

        // RL
        if (fe < -1 && nodo.getDerecha().getFactorEquilibrio() > 0) {
            nodo.setDerecha(rotacionDerecha(nodo.getDerecha()));
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.getIzquierda();
        NodoAVL T2 = x.getDerecha();

        x.setDerecha(y);
        y.setIzquierda(T2);

        actualizarAlturaYFE(y);
        actualizarAlturaYFE(x);

        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.getDerecha();
        NodoAVL T2 = y.getIzquierda();

        y.setIzquierda(x);
        x.setDerecha(T2);

        actualizarAlturaYFE(x);
        actualizarAlturaYFE(y);

        return y;
    }

    public void generarImagen(String nombreImagen, String carpetaDestino) {
        try {
            // Crear la carpeta si no existe
            File dir = new File(carpetaDestino);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Archivos DOT y PNG con ruta completa
            String nombreDot = carpetaDestino + "/" + nombreImagen + ".dot";
            String rutaImagen = carpetaDestino + "/" + nombreImagen + ".png";

            // Escribir el archivo DOT
            FileWriter writer = new FileWriter(nombreDot);
            writer.write("digraph AVL {\n");
            writer.write("node [shape=circle, style=filled, fillcolor=\"lightblue\", fontcolor=\"black\"];\n");
            generarDot(raiz, writer);
            writer.write("}\n");
            writer.close();

            // Generar imagen PNG usando Graphviz
            String comando = "dot -Tpng " + nombreDot + " -o " + rutaImagen;
            Runtime.getRuntime().exec(comando);

            System.out.println("✅ Imagen del árbol AVL generada en: " + rutaImagen);

        } catch (IOException e) {
            System.out.println("⚠ Error generando imagen del árbol AVL: " + e.getMessage());
        }
    }

// Recorre el árbol y genera los nodos y aristas
    private void generarDot(NodoAVL nodo, FileWriter writer) throws IOException {
        if (nodo == null) {
            return;
        }

        String titulo = nodo.getLibro().getTitulo().replace("\"", ""); // evitar errores por comillas

        writer.write("\"" + titulo + "\";\n");

        if (nodo.getIzquierda() != null) {
            String tituloIzq = nodo.getIzquierda().getLibro().getTitulo().replace("\"", "");
            writer.write("\"" + titulo + "\" -> \"" + tituloIzq + "\";\n");
        }
        if (nodo.getDerecha() != null) {
            String tituloDer = nodo.getDerecha().getLibro().getTitulo().replace("\"", "");
            writer.write("\"" + titulo + "\" -> \"" + tituloDer + "\";\n");
        }

        generarDot(nodo.getIzquierda(), writer);
        generarDot(nodo.getDerecha(), writer);
    }

    public Libro buscarPorTitulo(String titulo) {
        return buscarPorTituloRecursivo(raiz, titulo);
    }

    private Libro buscarPorTituloRecursivo(NodoAVL nodo, String titulo) {
        if (nodo == null) {
            return null;
        }

        int cmp = titulo.compareToIgnoreCase(nodo.getLibro().getTitulo());

        if (cmp == 0) {
            return nodo.getLibro(); // encontrado
        } else if (cmp < 0) {
            return buscarPorTituloRecursivo(nodo.getIzquierda(), titulo);
        } else {
            return buscarPorTituloRecursivo(nodo.getDerecha(), titulo);
        }
    }

    public List<Libro> sugerenciasPorTitulo(String texto) {
        List<Libro> sugerencias = new ArrayList<>();
        obtenerSugerencias(raiz, texto.toLowerCase(), sugerencias);
        return sugerencias;
    }

    private void obtenerSugerencias(NodoAVL nodo, String texto, List<Libro> lista) {
        if (nodo == null) {
            return;
        }

        obtenerSugerencias(nodo.getIzquierda(), texto, lista);

        String titulo = nodo.getLibro().getTitulo().toLowerCase();
        if (titulo.contains(texto)) {
            lista.add(nodo.getLibro());
        }

        obtenerSugerencias(nodo.getDerecha(), texto, lista);
    }
}