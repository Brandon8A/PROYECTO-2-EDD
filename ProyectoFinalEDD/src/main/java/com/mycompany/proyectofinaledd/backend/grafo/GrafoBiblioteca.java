/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazada;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazada;

/**
 *
 * @author brandon
 */
public class GrafoBiblioteca {
    private ListaEnlazada<NodoGrafo> nodosGrafo = new ListaEnlazada<>();
    
    public void agregarBiblioteca(Biblioteca bibliotecaNueva) {
        
        // Verificar si ya existe
        if (buscarNodo(bibliotecaNueva.getNombre()) == null) {
            nodosGrafo.agregarValorAlFinal(new NodoGrafo(bibliotecaNueva));
        }
    }
    
    /**
     * Metodo que se encarga de agregar la conexion entre dos bibliotecas
     * @param origen biblioteca donde "empieza la conexion"
     * @param destino biblioteca donde "llega la conexion"
     * @param tiempo el tiempo que hay para llegar, desde origen hasta destino
     * @param costo el costo que hay para llegar, desde origen hasta destino
     */
    public void agregarConexion(Biblioteca origen, Biblioteca destino, double tiempo, double costo) {
        //Obteniendo bibliotecas del grafo
        NodoGrafo bibliotecaOrigen = buscarNodo(origen.getNombre());
        NodoGrafo bibliotecaDestino = buscarNodo(destino.getNombre());

        //Condicional para verificar si las bibliotecas existe ya en el grafo
        if (bibliotecaOrigen != null && bibliotecaDestino != null) {//Si no existen ambas bibliotecas(nodos) 
            Conexion conexion = new Conexion(origen, destino, tiempo, costo);//Crea una nueva conexion entre bibliotecas
            bibliotecaOrigen.getConexiones().agregarValorAlFinal(conexion);//Se agrega a su lista de conexiones con otras bibliotecas

            // Bidireccional
            Conexion conexionInversa = new Conexion(destino, origen, tiempo, costo);
            bibliotecaDestino.getConexiones().agregarValorAlFinal(conexionInversa);
        }
    }

    /**
     * Funcion que se encarga de buscar una biblioteca y devolver dicha biblioteca
     * @param nombre nombre de la biblioteca a buscar
     * @return retorna un NodoGrafo(biblioteca)
     */
    private NodoGrafo buscarNodo(String nombre) {
        NodoListaEnlazada<NodoGrafo> actual = nodosGrafo.getInicio();//Obtener el inicio de los nodos
        while (actual != null) {//Bucle para recorrer la conexion entre los nodos del grafo
            if (actual.getDato().getBiblioteca().getNombre().equalsIgnoreCase(nombre)) {//Si el @param nombre coincide con el nodo actual, entonces se encontro el nodo
                return actual.getDato();//Retorna el nodoGrafo(biblioteca)
            }
            actual = actual.getSiguiente();//Obtiene la conexion con el siguiente nodo
        }
        return null;
    }

    public Biblioteca getBiblioteca(String nombre) {
        NodoGrafo nodo = buscarNodo(nombre);
        return (nodo != null) ? nodo.getBiblioteca() : null;
    }
}