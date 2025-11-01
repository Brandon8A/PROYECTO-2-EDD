/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.grafo;

import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazadaDoble;

/**
 *
 * @author brandon
 */
public class GrafoBiblioteca {
    private ListaEnlazadaDoble<NodoGrafo> nodosGrafo = new ListaEnlazadaDoble<>();
    
    private void agregarBiblioteca(Biblioteca bibliotecaNueva) {
        // Verificar si ya existe
        if (buscarNodo(bibliotecaNueva.getNombre()) == null) {
            nodosGrafo.agregarValorAlFinal(new NodoGrafo(bibliotecaNueva));
        }
    }
    
    /**
     * Metodo que se encarga de agregar la conexion entre dos bibliotecas
     */
    public void agregarConexion(ListaEnlazadaDoble<Conexion> conexiones) {
        
        NodoListaEnlazadaDoble<Conexion> actual = conexiones.getInicio();
        
        //Establecer conexion estre nodos
        while (actual != null) {
            establecerConexionesGrafo(actual.getDato());
            Conexion inversaConexion = new Conexion(actual.getDato().getDestino(), actual.getDato().getOrigen(), 
                    actual.getDato().getPesoTiempo(), actual.getDato().getPesoCosto());
            establecerConexionesGrafo(inversaConexion);
            actual = actual.getSiguiente();
        }
        
        this.imprimirConexiones();
        /*
        //Condicional para verificar si las bibliotecas existe ya en el grafo
        if (bibliotecaOrigen != null && bibliotecaDestino != null) {//Si no existen ambas bibliotecas(nodos) 
            Conexion conexion = new Conexion(origen, destino, tiempo, costo);//Crea una nueva conexion entre bibliotecas
            bibliotecaOrigen.getConexiones().agregarValorAlFinal(conexion);//Se agrega a su lista de conexiones con otras bibliotecas

            // Bidireccional
            Conexion conexionInversa = new Conexion(destino, origen, tiempo, costo);
            bibliotecaDestino.getConexiones().agregarValorAlFinal(conexionInversa);
        }
        */
    }
    
    private void establecerConexionesGrafo(Conexion conexion){
        NodoListaEnlazadaDoble<NodoGrafo> actual = this.nodosGrafo.getInicio();
        while (actual != null) {            
            if (actual.getDato().getBiblioteca().getNombre().equals(conexion.getOrigen().getNombre())) {
                 actual.getDato().getConexiones().agregarValorAlFinal(conexion);
                 
                return;
            }
            actual = actual.getSiguiente();
        }
    }  

    /**
     * Funcion que se encarga de buscar una biblioteca y devolver dicha biblioteca
     * @param nombre nombre de la biblioteca a buscar
     * @return retorna un NodoGrafo(biblioteca)
     */
    private NodoGrafo buscarNodo(String nombre) {
        NodoListaEnlazadaDoble<NodoGrafo> actual = nodosGrafo.getInicio();//Obtener el inicio de los nodos
        while (actual != null) {//Bucle para recorrer la conexion entre los nodos del grafo
            if (actual.getDato().getBiblioteca().getNombre().equalsIgnoreCase(nombre)) {//Si el @param nombre coincide con el nodo actual, entonces se encontro el nodo
                return actual.getDato();//Retorna el nodoGrafo(biblioteca)
            }
            actual = actual.getSiguiente();//Obtiene la conexion con el siguiente nodo
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

    public Biblioteca getBiblioteca(String nombre) {
        NodoGrafo nodo = buscarNodo(nombre);
        return (nodo != null) ? nodo.getBiblioteca() : null;
    }

    public Biblioteca getBibliotecaPorId(String id){
        NodoGrafo nodo = buscarNodoPorId(id);
        return (nodo != null) ? nodo.getBiblioteca() : null;
    }
    
    private void imprimirConexiones(){
        System.out.println("Conexiones: ");
        NodoListaEnlazadaDoble<NodoGrafo> actual = this.nodosGrafo.getInicio();
        while (actual != null) {            
            System.out.println(actual.getDato().getBiblioteca().getId() +":" );
            NodoListaEnlazadaDoble<Conexion> conexionActual = actual.getDato().getConexiones().getInicio();
            while (conexionActual != null) {                
                System.out.println("\t"+conexionActual.getDato().getDestino().getId());
                conexionActual = conexionActual.getSiguiente();
            }
            actual = actual.getSiguiente();
        }
    }
    
    public ListaEnlazadaDoble<Biblioteca> calcularRutaDijkstra(String nombreOrigen, String nombreDestino, boolean prioridadTiempo) {
        NodoGrafo origen = buscarNodo(nombreOrigen);
        NodoGrafo destino = buscarNodo(nombreDestino);

        if (origen == null || destino == null) {
            System.out.println("❌ No se puede calcular ruta: una o ambas bibliotecas no existen.");
            return new ListaEnlazadaDoble<>();
        }

        // Estructuras auxiliares 
        ListaEnlazadaDoble<NodoGrafo> visitados = new ListaEnlazadaDoble<>();
        ListaEnlazadaDoble<NodoGrafo> pendientes = new ListaEnlazadaDoble<>();
        ListaEnlazadaDoble<Double> distancias = new ListaEnlazadaDoble<>();
        ListaEnlazadaDoble<NodoGrafo> anteriores = new ListaEnlazadaDoble<>();

        // Inicialización
        NodoListaEnlazadaDoble<NodoGrafo> cursor = nodosGrafo.getInicio();
        while (cursor != null) {
            pendientes.agregarValorAlFinal(cursor.getDato());
            distancias.agregarValorAlFinal(cursor.getDato() == origen ? 0.0 : Double.MAX_VALUE);
            anteriores.agregarValorAlFinal(null);
            cursor = cursor.getSiguiente();
        }

        while (!pendientes.estaVacia()) {
            // Buscar nodo con menor distancia
            NodoGrafo actual = obtenerMinimo(pendientes, distancias);

            if (actual == null) break;
            if (actual == destino) break;

            visitados.agregarValorAlFinal(actual);

            NodoListaEnlazadaDoble<Conexion> conexionActual = actual.getConexiones().getInicio();
            while (conexionActual != null) {
                Conexion conexion = conexionActual.getDato();
                NodoGrafo vecino = buscarNodo(conexion.getDestino().getNombre());
                if (vecino != null && !contiene(visitados, vecino)) {
                    double peso = prioridadTiempo ? conexion.getPesoTiempo(): conexion.getPesoCosto();
                    double nuevaDist = obtenerDistancia(actual, distancias) + peso;

                    double distVecino = obtenerDistancia(vecino, distancias);
                    if (nuevaDist < distVecino) {
                        actualizarDistancia(vecino, nuevaDist, distancias);
                        actualizarAnterior(vecino, actual, anteriores);
                    }
                }
                conexionActual = conexionActual.getSiguiente();
            }

            eliminar(pendientes, actual);
        }

        // Reconstruir ruta
        ListaEnlazadaDoble<Biblioteca> ruta = new ListaEnlazadaDoble<>();
        NodoGrafo actual = destino;
        while (actual != null) {
            ruta.agregarValorAlFinal(actual.getBiblioteca());
            actual = obtenerAnterior(actual, anteriores);
        }

        // Invertir para mostrar origen → destino
        ListaEnlazadaDoble<Biblioteca> rutaFinal = new ListaEnlazadaDoble<>();
        NodoListaEnlazadaDoble<Biblioteca> r = ruta.getInicio();
        while (r != null) {
            rutaFinal.agregarValorAlFinal(r.getDato());
            r = r.getSiguiente();
        }

        return rutaFinal;
    }
    
    private NodoGrafo obtenerMinimo(ListaEnlazadaDoble<NodoGrafo> pendientes, ListaEnlazadaDoble<Double> distancias) {
        NodoListaEnlazadaDoble<NodoGrafo> nNodo = pendientes.getInicio();
        NodoListaEnlazadaDoble<Double> nDist = distancias.getInicio();
        NodoGrafo minimo = null;
        double minValor = Double.MAX_VALUE;

        while (nNodo != null && nDist != null) {
            if (nDist.getDato() < minValor) {
                minValor = nDist.getDato();
                minimo = nNodo.getDato();
            }
            nNodo = nNodo.getSiguiente();
            nDist = nDist.getSiguiente();
        }
        return minimo;
    }
    
    private boolean contiene(ListaEnlazadaDoble<NodoGrafo> lista, NodoGrafo nodo) {
        NodoListaEnlazadaDoble<NodoGrafo> actual = lista.getInicio();
        while (actual != null) {
            if (actual.getDato() == nodo) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    private double obtenerDistancia(NodoGrafo nodo, ListaEnlazadaDoble<Double> distancias) {
        NodoListaEnlazadaDoble<NodoGrafo> nNodo = nodosGrafo.getInicio();
        NodoListaEnlazadaDoble<Double> nDist = distancias.getInicio();
        while (nNodo != null && nDist != null) {
            if (nNodo.getDato() == nodo) return nDist.getDato();
            nNodo = nNodo.getSiguiente();
            nDist = nDist.getSiguiente();
        }
        return Double.MAX_VALUE;
    }
    
    private void actualizarDistancia(NodoGrafo nodo, double nueva, ListaEnlazadaDoble<Double> distancias) {
        NodoListaEnlazadaDoble<NodoGrafo> nNodo = nodosGrafo.getInicio();
        NodoListaEnlazadaDoble<Double> nDist = distancias.getInicio();
        while (nNodo != null && nDist != null) {
            if (nNodo.getDato() == nodo) {
                nDist.setDato(nueva);
                return;
            }
            nNodo = nNodo.getSiguiente();
            nDist = nDist.getSiguiente();
        }
    }
    
    private void actualizarAnterior(NodoGrafo nodo, NodoGrafo anterior, ListaEnlazadaDoble<NodoGrafo> anteriores) {
        NodoListaEnlazadaDoble<NodoGrafo> nNodo = nodosGrafo.getInicio();
        NodoListaEnlazadaDoble<NodoGrafo> nAnt = anteriores.getInicio();
        while (nNodo != null && nAnt != null) {
            if (nNodo.getDato() == nodo) {
                nAnt.setDato(anterior);
                return;
            }
            nNodo = nNodo.getSiguiente();
            nAnt = nAnt.getSiguiente();
        }
    }

    private NodoGrafo obtenerAnterior(NodoGrafo nodo, ListaEnlazadaDoble<NodoGrafo> anteriores) {
        NodoListaEnlazadaDoble<NodoGrafo> nNodo = nodosGrafo.getInicio();
        NodoListaEnlazadaDoble<NodoGrafo> nAnt = anteriores.getInicio();
        while (nNodo != null && nAnt != null) {
            if (nNodo.getDato() == nodo) {
                return nAnt.getDato();
            }
            nNodo = nNodo.getSiguiente();
            nAnt = nAnt.getSiguiente();
        }
        return null;
    }

    private void eliminar(ListaEnlazadaDoble<NodoGrafo> lista, NodoGrafo nodo) {
        lista.eliminarValor(nodo);
    }
    
    
    
    
    
    
    
    
    
    
    /**
 * Muestra en consola los caminos más óptimos desde una biblioteca origen hacia
 * todas las demás bibliotecas del grafo, según el algoritmo de Dijkstra.
 */
public void mostrarCaminosOptimos(String nombreOrigen, boolean prioridadTiempo) {
    NodoGrafo origen = buscarNodo(nombreOrigen);
    if (origen == null) {
        System.out.println("❌ Biblioteca origen no encontrada.");
        return;
    }

    System.out.println("📍 Caminos más óptimos desde: " + nombreOrigen);
    System.out.println("------------------------------------------------------");

    // Recorremos todos los nodos destino del grafo
    NodoListaEnlazadaDoble<NodoGrafo> cursor = nodosGrafo.getInicio();
    while (cursor != null) {
        NodoGrafo destino = cursor.getDato();

        // Evitamos calcular hacia el mismo origen
        if (!destino.getBiblioteca().getNombre().equals(nombreOrigen)) {
            ListaEnlazadaDoble<Biblioteca> ruta = calcularRutaDijkstra(nombreOrigen, destino.getBiblioteca().getNombre(), prioridadTiempo);

            if (ruta != null && !ruta.estaVacia()) {
                System.out.print("🛣 Ruta hacia " + destino.getBiblioteca().getNombre()+ ": ");

                // Mostrar la ruta en orden (origen → destino)
                NodoListaEnlazadaDoble<Biblioteca> n = ruta.getInicio();
                while (n != null) {
                    System.out.print(n.getDato().getNombre());
                    if (n.getSiguiente() != null) System.out.print(" → ");
                    n = n.getSiguiente();
                }

                // Calcular la distancia total
                double distanciaTotal = calcularDistanciaTotal(ruta, prioridadTiempo);
                String tipo = prioridadTiempo ? "minutos" : "costo";
                System.out.println(" | Distancia total: " + distanciaTotal + " " + tipo);
            } else {
                System.out.println("⚠ No existe ruta hacia " + destino.getBiblioteca().getId());
            }
        }
        cursor = cursor.getSiguiente();
    }
}
    
    /**
 * Calcula la distancia o tiempo total de una ruta dada según el tipo de prioridad.
 */
private double calcularDistanciaTotal(ListaEnlazadaDoble<Biblioteca> ruta, boolean prioridadTiempo) {
    double total = 0;
    NodoListaEnlazadaDoble<Biblioteca> actual = ruta.getInicio();
    while (actual != null && actual.getSiguiente() != null) {
        NodoGrafo origen = buscarNodo(actual.getDato().getNombre());
        NodoGrafo destino = buscarNodo(actual.getSiguiente().getDato().getNombre());

        if (origen != null && destino != null) {
            NodoListaEnlazadaDoble<Conexion> c = origen.getConexiones().getInicio();
            while (c != null) {
                Conexion con = c.getDato();
                if (con.getDestino().getNombre().equals(destino.getBiblioteca().getNombre())) {
                    total += prioridadTiempo ? con.getPesoTiempo() : con.getPesoCosto();
                    break;
                }
                c = c.getSiguiente();
            }
        }
        actual = actual.getSiguiente();
    }
    return total;
}
    
    
    
    
    public ListaEnlazadaDoble<NodoGrafo> getNodosGrafo() {
        return nodosGrafo;
    }

    public void setNodosGrafo(ListaEnlazadaDoble<NodoGrafo> nodosGrafo) {
        this.nodosGrafo = nodosGrafo;
    }
}