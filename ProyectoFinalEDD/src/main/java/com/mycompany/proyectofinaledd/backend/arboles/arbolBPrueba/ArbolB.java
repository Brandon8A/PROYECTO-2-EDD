/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolBPrueba;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.util.ArrayList;
import java.util.List;

public class ArbolB {

    private NodoB raiz;
    private final int gradoMinimo;

    public ArbolB(int gradoMinimo) {
        this.gradoMinimo = gradoMinimo;
        this.raiz = new NodoB(true);
    }

    // INSERTAR
    public void insertar(int anio, Libro libro) {
        NodoB r = raiz;
        if (r.getClaves().size() == 2 * gradoMinimo - 1) {
            NodoB nuevo = new NodoB(false);
            nuevo.getHijos().add(r);
            dividir(nuevo, 0, r);
            raiz = nuevo;
            insertarNoLleno(nuevo, anio, libro);
        } else {
            insertarNoLleno(r, anio, libro);
        }
    }

    private void insertarNoLleno(NodoB nodo, int anio, Libro libro) {
        int i = nodo.getClaves().size() - 1;

        if (nodo.esHoja()) {
            while (i >= 0 && anio < nodo.getClaves().get(i)) {
                i--;
            }

            i++;

            if (i < nodo.getClaves().size() && anio == nodo.getClaves().get(i)) {
                nodo.getValores().get(i).add(libro);
                return;
            }

            nodo.getClaves().add(i, anio);
            List<Libro> lista = new ArrayList<>();
            lista.add(libro);
            nodo.getValores().add(i, lista);
        } else {
            while (i >= 0 && anio < nodo.getClaves().get(i)) {
                i--;
            }
            i++;

            if (nodo.getHijos().get(i).getClaves().size() == 2 * gradoMinimo - 1) {
                dividir(nodo, i, nodo.getHijos().get(i));
                if (anio > nodo.getClaves().get(i)) {
                    i++;
                }
            }

            insertarNoLleno(nodo.getHijos().get(i), anio, libro);
        }
    }

    private void dividir(NodoB padre, int i, NodoB lleno) {
        NodoB nuevo = new NodoB(lleno.hoja);

        for (int j = 0; j < gradoMinimo - 1; j++) {
            nuevo.getClaves().add(lleno.getClaves().remove(gradoMinimo));
            nuevo.getValores().add(lleno.getValores().remove(gradoMinimo));
        }

        if (!lleno.esHoja()) {
            for (int j = 0; j < gradoMinimo; j++) {
                nuevo.getHijos().add(lleno.getHijos().remove(gradoMinimo));
            }
        }

        padre.getHijos().add(i + 1, nuevo);
        padre.getClaves().add(i, lleno.getClaves().remove(gradoMinimo - 1));
        padre.getValores().add(i, lleno.getValores().remove(gradoMinimo - 1));
    }

    public List<Libro> buscar(int anio) {
        return buscar(raiz, anio);
    }

    private List<Libro> buscar(NodoB nodo, int anio) {
        int i = 0;
        while (i < nodo.getClaves().size() && anio > nodo.getClaves().get(i)) {
            i++;
        }

        if (i < nodo.getClaves().size() && anio == nodo.getClaves().get(i)) {
            return nodo.getValores().get(i);
        }

        if (nodo.esHoja()) {
            return null;
        }

        return buscar(nodo.getHijos().get(i), anio);
    }

    public List<Libro> buscarPorRango(int inicio, int fin) {
        List<Libro> resultado = new ArrayList<>();
        buscarPorRango(raiz, inicio, fin, resultado);
        return resultado;
    }

    private void buscarPorRango(NodoB nodo, int inicio, int fin, List<Libro> resultado) {
        int i;

        for (i = 0; i < nodo.getClaves().size(); i++) {

            if (!nodo.esHoja()) {
                buscarPorRango(nodo.getHijos().get(i), inicio, fin, resultado);
            }

            int clave = nodo.getClaves().get(i);

            if (clave >= inicio && clave <= fin) {
                resultado.addAll(nodo.getValores().get(i));
            }

            if (clave > fin) {
                return;
            }
        }

        if (!nodo.esHoja()) {
            buscarPorRango(nodo.getHijos().get(i), inicio, fin, resultado);
        }
    }

    public void generarImagen(String nombreArchivo) {
        try {
            //Generar archivo DOT temporal
            String dot = generarDot();
            String rutaDot = "imagenes/" + nombreArchivo + ".dot";
            String rutaPng = "imagenes/" + nombreArchivo + ".png";

            java.io.File carpeta = new java.io.File("imagenes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            java.nio.file.Files.write(java.nio.file.Paths.get(rutaDot), dot.getBytes());

            //Ejecutar Graphviz para convertir DOT → PNG
            String comando = "dot -Tpng " + rutaDot + " -o " + rutaPng;
            Process proceso = Runtime.getRuntime().exec(comando);
            proceso.waitFor();

            System.out.println("✅ Imagen generada en: " + rutaPng);

        } catch (Exception e) {
            System.err.println("❌ Error al generar imagen: " + e.getMessage());
        }
    }

    private String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ArbolB {\n");
        sb.append("rankdir=TB;\n");
        sb.append("node [shape=record, style=filled, fillcolor=lightgray];\n");

        generarDotNodo(raiz, sb);

        sb.append("}\n");
        return sb.toString();
    }

    private void generarDotNodo(NodoB nodo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }

        String nodoId = "Nodo" + nodo.hashCode();
        sb.append(nodoId + " [label=\"");

        for (int i = 0; i < nodo.getClaves().size(); i++) {
            sb.append("<f" + i + "> " + nodo.getClaves().get(i));
            if (i < nodo.getClaves().size() - 1) {
                sb.append("|");
            }
        }

        sb.append("\"];\n");

        for (int i = 0; i <= nodo.getClaves().size(); i++) {
            if (!nodo.esHoja() && i < nodo.getHijos().size()) {
                NodoB hijo = nodo.getHijos().get(i);
                if (hijo != null) {
                    String hijoId = "Nodo" + hijo.hashCode();
                    sb.append(hijoId + " -> " + nodoId + ":f" + i + ";\n");
                    generarDotNodo(hijo, sb);
                }
            }
        }
    }

}
