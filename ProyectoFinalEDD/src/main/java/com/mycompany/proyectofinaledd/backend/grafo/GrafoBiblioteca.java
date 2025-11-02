/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.Controlador;
import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazadaDoble;
import java.util.Arrays;

/**
 *
 * @author brandon
 */
public class GrafoBiblioteca {

    private ListaEnlazadaDoble<NodoGrafo> nodosGrafo = new ListaEnlazadaDoble<>();
    private Controlador controlador;
    private int matrizTiempos[][];
    private double matrizCostos[][];
    private int tamanio;
    // Constante para representar INFINITO
    private static final int INF_INT = Integer.MAX_VALUE / 4;
    private static final double INF_DOUBLE = Double.MAX_VALUE / 4.0;

    public GrafoBiblioteca(Controlador controlador) {
        this.controlador = controlador;
    }

    /**
     * Buscar una biblioteca por su id recorriendo la lista de bibliotecas del
     * controlador.
     */
    public Biblioteca getBibliotecaPorId(String id) {
        NodoListaEnlazadaDoble<Biblioteca> actual = controlador.getBibliotecas().getInicio();
        while (actual != null) {
            Biblioteca b = actual.getDato();
            if (b.getId().equalsIgnoreCase(id)) {
                return b;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    private NodoGrafo buscarNodoPorId(String id) {
        NodoListaEnlazadaDoble<NodoGrafo> actual = nodosGrafo.getInicio();//Obtener el inicio de los nodos
        while (actual != null) {//Bucle para recorrer la conexion entre los nodos del grafo
            if (actual.getDato().getBiblioteca().getId().equalsIgnoreCase(id)) {//Si el @param nombre coincide con el nodo actual, entonces se encontro el nodo
                return actual.getDato();//Retorna el nodoGrafo(biblioteca)
            }
            actual = actual.getSiguiente();//Obtiene la conexion con el siguiente nodo
        }
        return null;
    }

    /**
     * Método que recibe la lista de conexiones (cargada por el Controlador) y
     * construye las matrices de adyacencia (tiempos y costos). También
     * actualiza los arreglos en el controlador.
     */
    public void agregarConexion(ListaEnlazadaDoble<Conexion> listaConexiones) {
        // 1) Crear mapeo de índices para bibliotecas (basado en la lista de bibliotecas del controlador)
        this.tamanio = controlador.getBibliotecas().getTamanio();
        if (tamanio <= 0) {
            return;
        }

        // Inicializar matrices
        matrizTiempos = new int[tamanio][tamanio];
        matrizCostos = new double[tamanio][tamanio];

        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                if (i == j) {
                    matrizTiempos[i][j] = 0;
                    matrizCostos[i][j] = 0.0;
                } else {
                    matrizTiempos[i][j] = INF_INT;
                    matrizCostos[i][j] = INF_DOUBLE;
                }
            }
        }

        // 2) Recorrer la lista de conexiones y poblar matrices
        NodoListaEnlazadaDoble<Conexion> nodoCon = listaConexiones.getInicio();
        while (nodoCon != null) {
            Conexion c = nodoCon.getDato();
            Biblioteca origen = c.getOrigen();
            Biblioteca destino = c.getDestino();

            int idxOrigen = getIndicePorId(origen.getId());
            int idxDestino = getIndicePorId(destino.getId());

            if (idxOrigen == -1 || idxDestino == -1) {
                // Podría registrarse en errores del controlador, pero por ahora se ignora la conexión inválida
                nodoCon = nodoCon.getSiguiente();
                continue;
            }

            // Llenar matrices (se asume bidireccional; si es dirigido, quita la segunda asignación)
            matrizTiempos[idxOrigen][idxDestino] = c.getPesoTiempo();
            matrizCostos[idxOrigen][idxDestino] = c.getPesoCosto();

            matrizTiempos[idxDestino][idxOrigen] = c.getPesoTiempo();
            matrizCostos[idxDestino][idxOrigen] = c.getPesoCosto();

            nodoCon = nodoCon.getSiguiente();
        }
        /*
        // 3) Guardar matrices también en el controlador 
        controlador.matrizAdyacenciaTiempos = this.matrizTiempos;
        controlador.matrizAdyacenciaCostos = this.matrizCostos;
        controlador.tamanioMatrizAdyacencia = this.tamanio;
         */
    }

    private void imprimirConexiones() {
        System.out.println("Conexiones: ");
        NodoListaEnlazadaDoble<NodoGrafo> actual = this.nodosGrafo.getInicio();
        while (actual != null) {
            System.out.println(actual.getDato().getBiblioteca().getId() + ":");
            NodoListaEnlazadaDoble<Conexion> conexionActual = actual.getDato().getConexiones().getInicio();
            while (conexionActual != null) {
                System.out.println("\t" + conexionActual.getDato().getDestino().getId());
                conexionActual = conexionActual.getSiguiente();
            }
            actual = actual.getSiguiente();
        }
    }

    private void establecerConexionesGrafo(Conexion conexion) {
        NodoListaEnlazadaDoble<NodoGrafo> actual = this.nodosGrafo.getInicio();
        while (actual != null) {
            if (actual.getDato().getBiblioteca().getNombre().equals(conexion.getOrigen().getNombre())) {
                actual.getDato().getConexiones().agregarValorAlFinal(conexion);

                return;
            }
            actual = actual.getSiguiente();
        }
    }

    //**************************************************************************
    /**
     * Devuelve el índice (0..n-1) correspondiente al id de la biblioteca. El
     * orden de índices corresponde al orden en la lista enlazada de bibliotecas
     * del controlador.
     */
    private int getIndicePorId(String id) {
        NodoListaEnlazadaDoble<Biblioteca> actual = controlador.getBibliotecas().getInicio();
        int idx = 0;
        while (actual != null) {
            Biblioteca b = actual.getDato();
            if (b.getId().equalsIgnoreCase(id)) {
                return idx;
            }
            actual = actual.getSiguiente();
            idx++;
        }
        return -1;
    }

    /**
     * Calcula la ruta mínima entre dos bibliotecas usando Dijkstra sobre la
     * matriz de adyacencia.
     *
     * @param idOrigen id de la biblioteca origen
     * @param idDestino id de la biblioteca destino
     * @param prioridadTiempo true -> minimizar tiempo; false -> minimizar costo
     * @return ListaDoble<Biblioteca> con la ruta desde origen a destino (vacía
     * si no hay ruta)
     */
    public ListaEnlazadaDoble<Biblioteca> dijkstra(String idOrigen, String idDestino, boolean prioridadTiempo) {
        ListaEnlazadaDoble<Biblioteca> ruta = new ListaEnlazadaDoble<>();

        if (tamanio == 0) {
            return ruta;
        }

        int src = getIndicePorId(idOrigen);
        int dest = getIndicePorId(idDestino);
        if (src == -1 || dest == -1) {
            return ruta;
        }

        // Distancias y predecesores
        double[] dist = new double[tamanio];
        boolean[] visited = new boolean[tamanio];
        int[] prev = new int[tamanio];
        Arrays.fill(prev, -1);

        for (int i = 0; i < tamanio; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
            visited[i] = false;
        }
        dist[src] = 0.0;

        for (int count = 0; count < tamanio - 1; count++) {
            // Encontrar el nodo no visitado con distancia mínima
            int u = minDistance(dist, visited);
            if (u == -1) {
                break;
            }
            visited[u] = true;

            // Relajación de aristas desde u
            for (int v = 0; v < tamanio; v++) {
                if (visited[v]) {
                    continue;
                }
                double peso;
                if (prioridadTiempo) {
                    int t = matrizTiempos[u][v];
                    if (t == INF_INT) {
                        continue;
                    }
                    peso = t;
                } else {
                    double c = matrizCostos[u][v];
                    if (c == INF_DOUBLE) {
                        continue;
                    }
                    peso = c;
                }

                if (dist[u] + peso < dist[v]) {
                    dist[v] = dist[u] + peso;
                    prev[v] = u;
                }
            }
        }

        // Si no hay camino (dist[dest] == INF)
        if (Double.isInfinite(dist[dest]) || dist[dest] == Double.POSITIVE_INFINITY) {
            return ruta; // vacío
        }

        // Reconstruir ruta (del destino al origen)
        int crawl = dest;
        java.util.LinkedList<Biblioteca> inversa = new java.util.LinkedList<>();
        while (crawl != -1) {
            // obtener biblioteca por índice
            Biblioteca b = getBibliotecaPorIndice(crawl);
            if (b == null) {
                break;
            }
            inversa.addFirst(b);
            crawl = prev[crawl];
        }

        // Convertir a ListaEnlazadaDoble
        for (Biblioteca b : inversa) {
            ruta.agregarValorAlFinal(b);
        }

        return ruta;
    }

    /**
     * Calcula el total acumulado (tiempo o costo) de una ruta ya encontrada por
     * Dijkstra.
     *
     * @param ruta lista de bibliotecas en orden (de origen a destino)
     * @param prioridadTiempo true → devuelve total de tiempo, false → total de
     * costo
     * @return el total acumulado de la ruta, o -1 si la ruta está vacía o
     * inválida
     */
    public double calcularTotalRuta(ListaEnlazadaDoble<Biblioteca> ruta, boolean prioridadTiempo)   {
        if (ruta == null || ruta.getTamanio() < 2) {
            return -1; // ruta vacía o solo un nodo
        }

        double total = 0.0;

        NodoListaEnlazadaDoble<Biblioteca> actual = ruta.getInicio();
        while (actual != null && actual.getSiguiente() != null) {
            Biblioteca origen = actual.getDato();
            Biblioteca destino = actual.getSiguiente().getDato();

            int idxOrigen = getIndicePorId(origen.getId());
            int idxDestino = getIndicePorId(destino.getId());

            if (idxOrigen == -1 || idxDestino == -1) {
                return -1; // ruta inválida
            }

            if (prioridadTiempo) {
                int tiempo = matrizTiempos[idxOrigen][idxDestino];
                if (tiempo == INF_INT) {
                    return -1;
                }
                total += tiempo;
            } else {
                double costo = matrizCostos[idxOrigen][idxDestino];
                if (costo == INF_DOUBLE) {
                    return -1;
                }
                total += costo;
            }

            actual = actual.getSiguiente();
        }

        return total;
    }

    /**
     * Obtiene el nodo con menor distancia no visitado.
     */
    private int minDistance(double[] dist, boolean[] visited) {
        double min = Double.POSITIVE_INFINITY;
        int min_index = -1;
        for (int v = 0; v < tamanio; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    /**
     * Devuelve la biblioteca correspondiente al índice (según el orden en
     * controlador.getBibliotecas()).
     */
    private Biblioteca getBibliotecaPorIndice(int indice) {
        NodoListaEnlazadaDoble<Biblioteca> actual = controlador.getBibliotecas().getInicio();
        int idx = 0;
        while (actual != null) {
            if (idx == indice) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
            idx++;
        }
        return null;
    }

    // Métodos getters para matrices (por si quieres exponerlas)
    public int[][] getMatrizTiempos() {
        return matrizTiempos;
    }

    public double[][] getMatrizCostos() {
        return matrizCostos;
    }
    //**************************************************************************

    public ListaEnlazadaDoble<NodoGrafo> getNodosGrafo() {
        return nodosGrafo;
    }

    public void setNodosGrafo(ListaEnlazadaDoble<NodoGrafo> nodosGrafo) {
        this.nodosGrafo = nodosGrafo;
    }
}
