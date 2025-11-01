/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolB;

/**
 *
 * @author brandon
 */

/**
 * 
 * @author brandon
 * @param <K> Tipo de clave (por ejemplo, Integer para año)
 * @param <V> Tipo de valor (por ejemplo, Libro)
 */
public class ArbolB<K extends Comparable<K>, V> {
    private NodoB raiz;
    private final int gradoMinimo; // T: número mínimo de claves por nodo

    public ArbolB(int gradoMinimo) {
        this.gradoMinimo = gradoMinimo;
        this.raiz = new NodoB(true);
    }

    // -------------------------------------------------------
    // INSERCIÓN
    // -------------------------------------------------------
    public void insertar(K clave, V valor) {
        NodoB r = raiz;
        if (r.getClaves().size() == 2 * gradoMinimo - 1) {
            // Nodo raíz lleno → dividir
            NodoB nuevo = new NodoB(false);
            nuevo.getHijos().add(r);
            dividirNodo(nuevo, 0, r);
            raiz = nuevo;
            insertarNoLleno(nuevo, clave, valor);
        } else {
            insertarNoLleno(r, clave, valor);
        }
    }

    private void insertarNoLleno(NodoB nodo, K clave, V valor) {
        int i = nodo.getClaves().size() - 1;

        if (nodo.esHoja()) {
            // Insertar clave ordenada
            while (i >= 0 && clave.compareTo(nodo.getClaves().get(i)) < 0) {
    i--;
}
            i++;

            // Si la clave ya existe, solo agregar el valor
            if (i < nodo.claves.size() && clave.compareTo(nodo.claves.get(i)) == 0) {
                nodo.valores.get(i).add(valor);
                return;
            }

            nodo.claves.add(i, clave);
            List<V> lista = new ArrayList<>();
            lista.add(valor);
            nodo.valores.add(i, lista);
        } else {
            while (i >= 0 && clave.compareTo(nodo.claves.get(i)) < 0) i--;
            i++;
            NodoB hijo = nodo.hijos.get(i);
            if (hijo.claves.size() == 2 * gradoMinimo - 1) {
                dividirNodo(nodo, i, hijo);
                if (clave.compareTo(nodo.claves.get(i)) > 0)
                    i++;
            }
            insertarNoLleno(nodo.hijos.get(i), clave, valor);
        }
    }

    private void dividirNodo(NodoB padre, int indice, NodoB lleno) {
        NodoB nuevo = new NodoB(lleno.hoja);
        for (int j = 0; j < gradoMinimo - 1; j++) {
            nuevo.claves.add(lleno.claves.remove(gradoMinimo));
            nuevo.valores.add(lleno.valores.remove(gradoMinimo));
        }

        if (!lleno.hoja) {
            for (int j = 0; j < gradoMinimo; j++) {
                nuevo.hijos.add(lleno.hijos.remove(gradoMinimo));
            }
        }

        padre.hijos.add(indice + 1, nuevo);
        padre.claves.add(indice, lleno.claves.remove(gradoMinimo - 1));
        padre.valores.add(indice, lleno.valores.remove(gradoMinimo - 1));
    }

    // -------------------------------------------------------
    // BÚSQUEDA SIMPLE
    // -------------------------------------------------------
    public List<V> buscar(K clave) {
        return buscar(raiz, clave);
    }

    private List<V> buscar(NodoB nodo, K clave) {
        int i = 0;
        while (i < nodo.claves.size() && clave.compareTo(nodo.claves.get(i)) > 0) {
            i++;
        }

        if (i < nodo.claves.size() && clave.compareTo(nodo.claves.get(i)) == 0) {
            return nodo.valores.get(i);
        }

        if (nodo.hoja) {
            return null;
        } else {
            return buscar(nodo.hijos.get(i), clave);
        }
    }

    // -------------------------------------------------------
    // BÚSQUEDA POR RANGO DE AÑOS
    // -------------------------------------------------------
    public List<V> buscarPorRango(K inicio, K fin) {
        List<V> resultado = new ArrayList<>();
        buscarPorRango(raiz, inicio, fin, resultado);
        return resultado;
    }

    private void buscarPorRango(NodoB nodo, K inicio, K fin, List<V> resultado) {
        int i;
        for (i = 0; i < nodo.claves.size(); i++) {
            K clave = nodo.claves.get(i);

            // Si no es hoja, explorar hijos menores a la clave actual
            if (!nodo.hoja) {
                buscarPorRango(nodo.hijos.get(i), inicio, fin, resultado);
            }

            // Si está dentro del rango, agregar los valores
            if (clave.compareTo(inicio) >= 0 && clave.compareTo(fin) <= 0) {
                resultado.addAll(nodo.valores.get(i));
            }

            // Si ya pasamos el rango, detener
            if (clave.compareTo(fin) > 0) {
                return;
            }
        }

        // Explorar último hijo
        if (!nodo.hoja) {
            buscarPorRango(nodo.hijos.get(i), inicio, fin, resultado);
        }
    }

    // -------------------------------------------------------
    // RECORRIDO COMPLETO (ordenado)
    // -------------------------------------------------------
    public void recorrer() {
        recorrer(raiz);
    }

    private void recorrer(NodoB nodo) {
        int i;
        for (i = 0; i < nodo.claves.size(); i++) {
            if (!nodo.hoja) recorrer(nodo.hijos.get(i));
            System.out.println(nodo.claves.get(i) + " -> " + nodo.valores.get(i));
        }
        if (!nodo.hoja) recorrer(nodo.hijos.get(i));
    }
}
