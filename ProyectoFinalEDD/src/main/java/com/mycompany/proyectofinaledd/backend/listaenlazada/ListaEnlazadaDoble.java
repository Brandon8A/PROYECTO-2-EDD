/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.listaenlazada;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

/**
 *
 * @author brandon
 */
public class ListaEnlazadaDoble<T> {

    private NodoListaEnlazadaDoble<T> inicio;
    private NodoListaEnlazadaDoble<T> fin;
    private int tamanio = 0;

    /**
     * Metodo que almacena valores al final de la lista
     *
     * @param nuevoValor valor a ingresar a la lista
     */
    public void agregarValorAlFinal(T nuevoValor) {
        NodoListaEnlazadaDoble<T> nuevo = new NodoListaEnlazadaDoble<>(nuevoValor, tamanio);
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setAnterior(fin);
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    /**
     * Funcion que se encarga de quitar elementos de enfrente FIFO
     *
     * @return returna el valor de enfrente de la lista
     */
    public T quitarElementoEnfrente() {
        if (estaVacia()) {
            return null;
        }
        T dato = inicio.getDato();
        inicio = inicio.getSiguiente();
        if (inicio != null) {
            inicio.setAnterior(null);
        } else {
            fin = null;
        }
        this.tamanio--;
        return dato;
    }

    public void eliminarLista() {
        this.inicio = null;
        this.fin = null;
        this.tamanio = 0;
    }

    // Elimina un elemento específico
    public boolean eliminarValor(T dato) {
        NodoListaEnlazadaDoble<T> actual = inicio;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (actual.getAnterior() != null) {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                } else {
                    inicio = actual.getSiguiente();
                }

                if (actual.getSiguiente() != null) {
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                } else {
                    fin = actual.getAnterior();
                }
                tamanio--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public T eliminarInicio() {
        if (inicio == null) {
            return null; // o lanzar excepción si prefieres
        }
        T datoEliminado = inicio.getDato();
        inicio = inicio.getSiguiente();
        if (inicio != null) {
            inicio.setAnterior(null);
        } else {
            fin = null; // si la lista queda vacía
        }
        tamanio--;
        return datoEliminado;
    }

    public void generarImagen(String nombreImagen, String carpetaDestino) {
        if (inicio == null) {
            System.out.println("⚠ No se puede generar imagen: la lista está vacía.");
            return;
        }

        try {
            // Crear carpeta si no existe
            File carpeta = new File(carpetaDestino);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Rutas de archivo DOT y PNG
            String rutaDot = carpetaDestino + "/" + nombreImagen + ".dot";
            String rutaPng = carpetaDestino + "/" + nombreImagen + ".png";

            // Crear archivo DOT
            try (PrintWriter out = new PrintWriter(new FileWriter(rutaDot))) {
                out.println("digraph ListaDoble {");
                out.println("  rankdir=LR;"); // Dirección izquierda a derecha
                out.println("  node [shape=record, style=filled, fillcolor=lightblue, fontcolor=black];");

                // Generar nodos y enlaces
                NodoListaEnlazadaDoble<T> actual = inicio;
                int index = 0;

                while (actual != null) {
                    String nodoNombre = "nodo" + index;
                    // Convertir dato a texto (usando toString)
                    String etiqueta = actual.getDato() != null ? actual.getDato().toString().replace("\"", "") : "null";

                    // Crear nodo con etiqueta
                    out.println("  " + nodoNombre + " [label=\"" + etiqueta + "\"];");

                    // Conexiones entre nodos
                    if (actual.getSiguiente() != null) {
                        out.println("  " + nodoNombre + " -> nodo" + (index + 1) + ";");
                        out.println("  nodo" + (index + 1) + " -> " + nodoNombre + ";");
                    }

                    actual = actual.getSiguiente();
                    index++;
                }

                out.println("}");
            }

            // Ejecutar Graphviz (generar PNG)
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", rutaDot, "-o", rutaPng);
            pb.start();

            System.out.println("✅ Imagen de la lista doble generada en: " + rutaPng);

        } catch (IOException e) {
            System.out.println("⚠ Error generando imagen de la lista: " + e.getMessage());
        }
    }
    
    //***************************************************************************************************

    /**
     * Metodo ordenacion Intercambio
     * @param comp se va a compara con datos del objeto tipo libro
     */
    public void ordenarPorIntercambio(Comparator<Libro> comp) {
        if (inicio == null) {
            return;
        }

        boolean huboCambio;
        do {
            huboCambio = false;
            NodoListaEnlazadaDoble<Libro> actual = (NodoListaEnlazadaDoble<Libro>) this.inicio;
            while (actual != null && actual.getSiguiente() != null) {
                if (comp.compare(actual.getDato(), actual.getSiguiente().getDato()) > 0) {
                    Libro temp = actual.getDato();
                    actual.setDato(actual.getSiguiente().getDato());
                    actual.getSiguiente().setDato(temp);
                    huboCambio = true;
                }
                actual = actual.getSiguiente();
            }
        } while (huboCambio);
    }

    /**
     * Seleccion directa
     * @param comp 
     */
    public void ordenarPorSeleccion(Comparator<Libro> comp) {
        NodoListaEnlazadaDoble<Libro> actual = (NodoListaEnlazadaDoble<Libro>) inicio;
        while (actual != null) {
            NodoListaEnlazadaDoble<Libro> min = actual;
            NodoListaEnlazadaDoble<Libro> siguiente = actual.getSiguiente();
            while (siguiente != null) {
                if (comp.compare(siguiente.getDato(), min.getDato()) < 0) {
                    min = siguiente;
                }
                siguiente = siguiente.getSiguiente();
            }
            if (min != actual) {
                Libro temp = actual.getDato();
                actual.setDato(min.getDato());
                min.setDato(temp);
            }
            actual = actual.getSiguiente();
        }
    }

    /**
     * Por insercion
     * @param comp 
     */
    public void ordenarPorInsercion(Comparator<Libro> comp) {
        if (inicio == null || inicio.getSiguiente() == null) {
            return;
        }

        NodoListaEnlazadaDoble<Libro> actual = (NodoListaEnlazadaDoble<Libro>) inicio.getSiguiente();
        while (actual != null) {
            Libro valor = actual.getDato();
            NodoListaEnlazadaDoble<Libro> anterior = actual.getAnterior();

            while (anterior != null && comp.compare(anterior.getDato(), valor) > 0) {
                anterior.getSiguiente().setDato(anterior.getDato());
                anterior = anterior.getAnterior();
            }
            if (anterior == null) {
                inicio.setDato((T) valor);
            } else {
                anterior.getSiguiente().setDato(valor);
            }
            actual = actual.getSiguiente();
        }
    }

    /**
     * Shell sort
     * @param comp 
     */
    public void ordenarPorShell(Comparator<Libro> comp) {
        if (tamanio <= 1) {
            return;
        }

        int gap = tamanio / 2;
        while (gap > 0) {
            for (int i = gap; i < tamanio; i++) {
                Libro temp = obtenerValorPorIndice(i);
                int j = i;
                while (j >= gap && comp.compare(obtenerValorPorIndice(j - gap), temp) > 0) {
                    asignarValorEnIndice(j, obtenerValorPorIndice(j - gap));
                    j -= gap;
                }
                asignarValorEnIndice(j, temp);
            }
            gap /= 2;
        }
    }

    /**
     * Quick sort
     * @param comp 
     */
    public void ordenarPorQuickSort(Comparator<Libro> comp) {
        quickSortRec(0, tamanio - 1, comp);
    }

    /**
     * Parte del Quick sort
     * @param izquierda
     * @param derecha
     * @param comp 
     */
    private void quickSortRec(int izquierda, int derecha, Comparator<Libro> comp) {
        if (izquierda >= derecha) {
            return;
        }

        Libro pivote = obtenerValorPorIndice((izquierda + derecha) / 2);
        int i = izquierda, j = derecha;

        while (i <= j) {
            while (comp.compare(obtenerValorPorIndice(i), pivote) < 0) {
                i++;
            }
            while (comp.compare(obtenerValorPorIndice(j), pivote) > 0) {
                j--;
            }
            if (i <= j) {
                Libro temp = obtenerValorPorIndice(i);
                asignarValorEnIndice(i, obtenerValorPorIndice(j));
                asignarValorEnIndice(j, temp);
                i++;
                j--;
            }
        }

        if (izquierda < j) {
            quickSortRec(izquierda, j, comp);
        }
        if (i < derecha) {
            quickSortRec(i, derecha, comp);
        }
    }

// Métodos auxiliares
    private Libro obtenerValorPorIndice(int indice) {
        NodoListaEnlazadaDoble<Libro> actual = (NodoListaEnlazadaDoble<Libro>) inicio;
        for (int i = 0; i < indice && actual != null; i++) {
            actual = actual.getSiguiente();
        }
        return actual != null ? actual.getDato() : null;
    }

    private void asignarValorEnIndice(int indice, Libro valor) {
        NodoListaEnlazadaDoble<Libro> actual = (NodoListaEnlazadaDoble<Libro>) inicio;
        for (int i = 0; i < indice && actual != null; i++) {
            actual = actual.getSiguiente();
        }
        if (actual != null) {
            actual.setDato(valor);
        }
    }

    //*******************************************************
    public boolean estaVacia() {
        return inicio == null;
    }

    public NodoListaEnlazadaDoble<T> getInicio() {
        return inicio;
    }

    public void setInicio(NodoListaEnlazadaDoble<T> inicio) {
        this.inicio = inicio;
    }

    public NodoListaEnlazadaDoble<T> getFin() {
        return fin;
    }

    public void setFin(NodoListaEnlazadaDoble<T> fin) {
        this.fin = fin;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
}
