/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend;

import com.mycompany.proyectofinaledd.backend.arboles.arbolAVL.ArbolAVL;
import com.mycompany.proyectofinaledd.backend.arboles.arbolB.ArbolB;
import com.mycompany.proyectofinaledd.backend.arboles.arbolBMas.ArbolBMas;
import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.exception.ExceptionBibliotecaMagica;
import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;
import com.mycompany.proyectofinaledd.backend.grafo.GrafoBiblioteca;
import com.mycompany.proyectofinaledd.backend.grafo.NodoGrafo;
import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.libro.TypeEstado;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazadaDoble;
import com.mycompany.proyectofinaledd.backend.tablahash.TablaHashLibros;
import com.mycompany.proyectofinaledd.frontend.error.DialogErrorLineas;
import com.mycompany.proyectofinaledd.frontend.filechooser.FileChooser;
import com.mycompany.proyectofinaledd.frontend.inicio.Inicio;
import com.mycompany.proyectofinaledd.pruebas.VentanaSimulacion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author brandon
 */
public class Controlador {

    private FileChooser fileChooser;
    private Inicio inicio;
    private boolean hayBibliotecas = false;
    private boolean hayConexiones = false;
    private boolean hayLibros = false;
    private GrafoBiblioteca grafoBibliotecas;
    private ListaEnlazadaDoble<String> erroresCargaArchivos;
    private ListaEnlazadaDoble<Libro> libros;
    private ListaEnlazadaDoble<Biblioteca> bibliotecas;
    private ListaEnlazadaDoble<Conexion> conexiones;
    private int matrizAdyacenciaTiempos[][];
    private double matrizAdyacenciaCostos[][];
    private int tamanioMatrizAdyacencia;
    private ArbolAVL arbolAVL;
    private ArbolB arbolB;
    private ArbolBMas arbolBMas;
    private TablaHashLibros tablaHashLibros;

    public Controlador() {
        this.bibliotecas = new ListaEnlazadaDoble<>();
        this.libros = new ListaEnlazadaDoble<>();
        this.conexiones = new ListaEnlazadaDoble<>();
        this.erroresCargaArchivos = new ListaEnlazadaDoble<>();
        this.grafoBibliotecas = new GrafoBiblioteca(this);
        this.arbolAVL = new ArbolAVL();
        this.arbolB = new ArbolB(3);
        this.arbolBMas = new ArbolBMas(3);
        this.tablaHashLibros = new TablaHashLibros(10);
    }

    /**
     * Funcion encargada de poder recolectar un archivo con extension .csv
     *
     * @return retorna el archivo seleccionado por el usuario
     * @throws ExceptionBibliotecaMagica lanza excepcion por algun error de
     * archivo
     */
    private File archivoCSV() throws ExceptionBibliotecaMagica {
        this.fileChooser = new FileChooser();
        String pathSCV = this.fileChooser.seleccionarArchivoCSV();
        //Validar que se haya seleccionado un archivo
        if (pathSCV == null) {
            throw new ExceptionBibliotecaMagica("Ningun archivo seleccionado.");
        }
        File fileCSV = new File(pathSCV);

        // Validar extensión .csv
        if (!fileCSV.getName().toLowerCase().endsWith(".csv")) {
            throw new ExceptionBibliotecaMagica("El archivo seleccionado no tiene extensión .csv");
        }
        return fileCSV;
    }

    /**
     * Metodo que carga los libros desde un archivo con extension csv
     */
    public void cargarLibros() {
        this.erroresCargaArchivos.eliminarLista();

        try {
            File archivo = this.archivoCSV();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int numeroLinea = 0;

                while ((linea = br.readLine()) != null) {
                    numeroLinea++;

                    // Saltar encabezado o líneas vacías
                    if (numeroLinea == 1 || linea.trim().isEmpty()) {
                        continue;
                    }

                    String[] datos = linea.split(",");
                    if (datos.length < 9) {
                        System.out.println("⚠Línea ignorada (formato incorrecto): " + linea);
                        this.erroresCargaArchivos.agregarValorAlFinal("Línea [" + numeroLinea + "] ignorada (formato incorrecto): " + linea);
                        continue;
                    }

                    if (!this.ISBNValido(datos[1], datos[0])) {
                        this.erroresCargaArchivos.agregarValorAlFinal("Línea [" + numeroLinea + "] ignorada (ISBN incorrecto): " + linea);
                        continue;
                    }

                    try {
                        // Coincidir con el orden del encabezado del CSV
                        String titulo = datos[0].trim();
                        String isbn = datos[1].trim();
                        String genero = datos[2].trim();
                        int anio = Integer.parseInt(datos[3].trim());
                        String autor = datos[4].trim();
                        String estado = datos[5].trim();
                        String idOrigen = datos[6].trim();
                        String idDestino = datos[7].trim();
                        String prioridad = datos[8].trim();

                        // Convertir estado a enum
                        TypeEstado tipoEstado;
                        switch (estado.toLowerCase()) {
                            case "disponible" ->
                                tipoEstado = TypeEstado.DISPONIBLE;
                            case "en transito" ->
                                tipoEstado = TypeEstado.EN_TRANSITO;
                            case "agotado" ->
                                tipoEstado = TypeEstado.AGOTADO;
                            default ->
                                tipoEstado = TypeEstado.PRESTADO;
                        }

                        // Buscar bibliotecas de origen y destino en el grafo
                        Biblioteca bibliotecaOrigen = grafoBibliotecas.getBibliotecaPorId(idOrigen);
                        Biblioteca bibliotecaDestino = grafoBibliotecas.getBibliotecaPorId(idDestino);

                        if (bibliotecaOrigen == null) {
                            System.out.println("⚠No se encontró biblioteca de origen: " + idOrigen);
                            this.erroresCargaArchivos.agregarValorAlFinal("Error en linea [" + numeroLinea + "] no se encontró biblioteca de origen: " + idOrigen);
                            continue;
                        }

                        if (bibliotecaDestino == null) {
                            System.out.println("⚠No se encontró biblioteca de destino: " + idDestino);
                            this.erroresCargaArchivos.agregarValorAlFinal("Error en linea [" + numeroLinea + "] no se encontró biblioteca de destino: " + idDestino);
                            continue;
                        }

                        // Crear el libro
                        Libro libro = new Libro(titulo, isbn, genero, anio, autor, tipoEstado);

                        // Registrar origen y destino (si tu clase Libro tiene estos campos)
                        libro.setBibliotecaOrigen(bibliotecaOrigen);
                        libro.setBibliotecaDestino(bibliotecaDestino);
                        libro.setPrioridad(prioridad);

                        // Colocar libro en la cola de ingreso de la biblioteca de origen
                        bibliotecaOrigen.getColaIngreso().agregarValorAlFinal(libro);
                        this.libros.agregarValorAlFinal(libro);
                    } catch (Exception e) {
                        System.out.println("⚠ Error procesando línea " + numeroLinea + ": " + e.getMessage());
                    }
                }

                System.out.println("✅ Carga completada: " + libros.getTamanio() + " libros cargados.");
                JOptionPane.showMessageDialog(null,
                        "Se cargaron " + libros.getTamanio() + " libros con éxito.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                if (erroresCargaArchivos.getTamanio() != 0) {
                    DialogErrorLineas errorLineas = new DialogErrorLineas(null, true, erroresCargaArchivos);
                    errorLineas.setLocationRelativeTo(null);
                    errorLineas.setVisible(true);
                }
            } catch (IOException e) {
                throw new ExceptionBibliotecaMagica("Error al leer el archivo de libros: " + e.getMessage());
            }

        } catch (ExceptionBibliotecaMagica e) {
            JOptionPane.showMessageDialog(this.inicio, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        /*
        SwingUtilities.invokeLater(() -> new VentanaSimulacion(this).setVisible(true));
         */
        this.almacenarLibrosCargados();
    }

    private void almacenarLibrosCargados() {
        NodoListaEnlazadaDoble<Libro> actualLibro = this.libros.getInicio();
        while (actualLibro != null) {
            NodoListaEnlazadaDoble<NodoGrafo> actualNodoGrafo = this.grafoBibliotecas.getNodosGrafo().getInicio();
            while (actualNodoGrafo != null) {                
                //Condicional para verificar si la biblioteca origen del libro coincide con alguna de las del grafo
                if (actualLibro.getDato().getBibliotecaDestino().getId().equals(actualNodoGrafo.getDato().getBiblioteca().getId())) {
                    actualNodoGrafo.getDato().getBiblioteca().getArbolAVL().insertar(actualLibro.getDato());
                    actualNodoGrafo.getDato().getBiblioteca().getArbolB().insert(actualLibro.getDato());
                    actualNodoGrafo.getDato().getBiblioteca().getArbolBMas().insertar(actualLibro.getDato());
                    actualNodoGrafo.getDato().getBiblioteca().getTablaHash().insertar(actualLibro.getDato());
                    actualNodoGrafo.getDato().getBiblioteca().getListaEnlazada().agregarValorAlFinal(actualLibro.getDato());
                    break;
                }
                actualNodoGrafo = actualNodoGrafo.getSiguiente();
            }
            actualLibro = actualLibro.getSiguiente();
        }
        
    }

    /**
     * Funcion que indica si el isbn es valido: si existe pero con el mismo
     * titulo es valido, si existe pero con otro titulo se rechaza
     *
     * @param isbn isbn a comparar con los ya registrados
     * @param titulo titulo del libro a comparar
     * @return retorna verdadero si el isbn es valido o falso si es invalido
     */
    private boolean ISBNValido(String isbn, String titulo) {
        NodoListaEnlazadaDoble<Libro> actual = this.libros.getInicio();
        while (actual != null) {
            Libro existente = actual.getDato();
            if (existente.getISBN().equalsIgnoreCase(isbn)) {
                // ISBN ya existe, verificar título
                if (!existente.getTitulo().equalsIgnoreCase(titulo)) {
                    // ISBN duplicado con otro título → error
                    return false;
                }
            }
            actual = actual.getSiguiente();
        }
        return true; // No hay conflicto
    }

    public void cargarBibliotecas() throws ExceptionBibliotecaMagica {
        File archivo = archivoCSV();
        this.erroresCargaArchivos.eliminarLista();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;

                // Saltar encabezado o líneas vacías
                if (numeroLinea == 1 || linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");

                //Verica que la linea a analizar tenga la cantidad de campos correctos
                if (datos.length < 6) {
                    System.out.println("⚠Línea ignorada (formato incorrecto): " + linea);
                    this.erroresCargaArchivos.agregarValorAlFinal("Error de formato incorrecto en linea: [" + numeroLinea + "] " + linea);
                    continue;
                }

                //Verifica que el id de la nueva biblioteca no se repita con otra ya ingresada
                if (!validoIdBiblioteca(datos[0])) {
                    this.erroresCargaArchivos.agregarValorAlFinal("Error de ID Biblioteca en linea: [" + numeroLinea + "] " + linea);
                    continue;
                }

                //Almacena la nueva biblioteca en la lista enlazada
                try {
                    // Coincidir con el orden del CSV
                    String id = datos[0].trim();
                    String nombre = datos[1].trim();
                    String ubicacion = datos[2].trim();
                    int tiempoIngreso = Integer.parseInt(datos[3].trim());
                    int tiempoTraspaso = Integer.parseInt(datos[4].trim());
                    int dispatchInterval = Integer.parseInt(datos[5].trim());

                    // Crear biblioteca con los nuevos datos
                    Biblioteca biblioteca = new Biblioteca(id, nombre, ubicacion, tiempoIngreso, tiempoTraspaso, dispatchInterval);

                    // Agregar al final de la lista enlazada de bibliotecas
                    bibliotecas.agregarValorAlFinal(biblioteca);
                    //Agregando y creando nodos del grafo con su respectiva biblioteca
                    this.grafoBibliotecas.getNodosGrafo().agregarValorAlFinal(new NodoGrafo(biblioteca));

                } catch (Exception e) {
                    System.out.println("⚠ Error procesando línea " + numeroLinea + ": " + e.getMessage());
                }
            }

            System.out.println("✅ Carga completada: " + bibliotecas.getTamanio() + " bibliotecas cargadas.");
            JOptionPane.showMessageDialog(null,
                    "Se cargaron un total de: " + bibliotecas.getTamanio() + " bibliotecas con éxito!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.tamanioMatrizAdyacencia = this.bibliotecas.getTamanio();
            if (erroresCargaArchivos.getTamanio() != 0) {
                DialogErrorLineas errores = new DialogErrorLineas(null, true, erroresCargaArchivos);
                errores.setLocationRelativeTo(null);
                errores.setVisible(true);
            }
            this.hayBibliotecas = true;
        } catch (IOException e) {
            throw new ExceptionBibliotecaMagica("Error al leer el archivo de bibliotecas: " + e.getMessage());
        }
    }

    /**
     * Funcion que valida si un id esta o no repetido dentro de la lista
     * enlazada de bibliotecas
     *
     * @param idBiblioteca el id de la biblioteca que se va a ingresar
     * @return retorna verdadero o falso si el id existe o no
     */
    private boolean validoIdBiblioteca(String idBiblioteca) {
        NodoListaEnlazadaDoble<Biblioteca> actual = this.bibliotecas.getInicio();
        while (actual != null) {
            Biblioteca existente = actual.getDato();
            if (existente.getId().equalsIgnoreCase(idBiblioteca)) {
                return false;
            }
            actual = actual.getSiguiente();
        }
        return true;
    }

    /**
     * Funcion que se encarga de cargar las conexiones entre bibliotecas desde
     * un archivo csv
     *
     * @throws ExceptionBibliotecaMagica
     */
    public void cargarConexiones() throws ExceptionBibliotecaMagica {
        File archivo = archivoCSV();//Archivo csv seleccionado 
        this.erroresCargaArchivos.eliminarLista();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;

                // Saltar encabezado o líneas vacías
                if (numeroLinea == 1 || linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length < 4) {
                    System.out.println("⚠Línea ignorada (formato incorrecto): " + linea);
                    this.erroresCargaArchivos.agregarValorAlFinal("Línea [" + numeroLinea + "] ignorada (formato incorrecto): " + linea);
                    continue;
                }

                try {
                    String idOrigen = datos[0].trim();
                    String idDestino = datos[1].trim();
                    int tiempo = Integer.parseInt(datos[2].trim());
                    double costo = Double.parseDouble(datos[3].trim());

                    // Buscar las bibliotecas registradas en el grafo
                    Biblioteca origen = grafoBibliotecas.getBibliotecaPorId(idOrigen);
                    Biblioteca destino = grafoBibliotecas.getBibliotecaPorId(idDestino);

                    if (origen == null) {
                        this.erroresCargaArchivos.agregarValorAlFinal("Error de biblioteca origen en línea [" + numeroLinea + "]: " + linea);
                        continue;
                    } else if (destino == null) {
                        this.erroresCargaArchivos.agregarValorAlFinal("Error de biblioteca destino en línea [" + numeroLinea + "]: " + linea);
                        continue;
                    }

                    // Crear conexión y agregar al grafo
                    Conexion conexion = new Conexion(origen, destino, tiempo, costo);
                    conexiones.agregarValorAlFinal(conexion);
                    //conexiones.add(conexion);
                    //grafoBibliotecas.agregarConexion(origen, destino, tiempo, costo);

                } catch (NumberFormatException e) {
                    System.out.println("⚠Error de formato numérico en línea " + numeroLinea + ": " + e.getMessage());
                    this.erroresCargaArchivos.agregarValorAlFinal("Error de formato numérico en línea [" + numeroLinea + "]: " + linea);
                }
            }
            grafoBibliotecas.agregarConexion(this.conexiones);

            System.out.println("\n\nMatriz de tiempos: ");
            this.imprimirMatriz(this.grafoBibliotecas.getMatrizTiempos());
            System.out.println("\n\nMatriz de tiempos: ");
            this.imprimirMatrizDouble(this.grafoBibliotecas.getMatrizCostos());

            System.out.println("\n💰Ruta mas rapida de A a D");
            ListaEnlazadaDoble<Biblioteca> rutaTiempo = grafoBibliotecas.dijkstra("A-101", "D-412", true);
            mostrarRuta(rutaTiempo);
            System.out.println("Tiempo total: " + grafoBibliotecas.calcularTotalRuta(rutaTiempo, true));

            System.out.println("\n💰 Ruta más barata de B1 a B4 (prioridad costo):");
            ListaEnlazadaDoble<Biblioteca> rutaCosto = grafoBibliotecas.dijkstra("A-101", "D-412", false);
            mostrarRuta(rutaCosto);
            System.out.println("💵 Costo total: " + grafoBibliotecas.calcularTotalRuta(rutaCosto, false));

            System.out.println("✅ Carga completada: " + conexiones.getTamanio() + " conexiones agregadas al grafo.");
            JOptionPane.showMessageDialog(null,
                    "Se cargaron un total de: " + conexiones.getTamanio() + " conexiones con éxito!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            if (erroresCargaArchivos.getTamanio() != 0) {
                DialogErrorLineas errores = new DialogErrorLineas(null, true, erroresCargaArchivos);
                errores.setLocationRelativeTo(null);
                errores.setVisible(true);
            }
            this.hayConexiones = true;
        } catch (IOException e) {
            throw new ExceptionBibliotecaMagica("Error al leer el archivo de conexiones: " + e.getMessage());
        }
    }

    //**************************************************************************
    private static void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int val : fila) {
                System.out.print((val == Integer.MAX_VALUE / 4 ? "∞" : val) + "\t");
            }
            System.out.println();
        }
    }

    private static void imprimirMatrizDouble(double[][] matriz) {
        for (double[] fila : matriz) {
            for (double val : fila) {
                System.out.print((val == Double.MAX_VALUE / 4.0 ? "∞" : val) + "\t");
            }
            System.out.println();
        }
    }

    private static void mostrarRuta(ListaEnlazadaDoble<Biblioteca> ruta) {
        if (ruta.getTamanio() == 0) {
            System.out.println("⚠️ No hay ruta disponible.");
            return;
        }
        NodoListaEnlazadaDoble<Biblioteca> actual = ruta.getInicio();
        while (actual != null) {
            System.out.print(actual.getDato().getId());
            if (actual.getSiguiente() != null) {
                System.out.print(" ➡ ");
            }
            actual = actual.getSiguiente();
        }
        System.out.println();
    }
    //**************************************************************************

    public Inicio getInicio() {
        return inicio;
    }

    public void setInicio(Inicio inicio) {
        this.inicio = inicio;
    }

    public GrafoBiblioteca getGrafoBibliotecas() {
        return grafoBibliotecas;
    }

    public void setGrafoBibliotecas(GrafoBiblioteca grafoBibliotecas) {
        this.grafoBibliotecas = grafoBibliotecas;
    }

    public ListaEnlazadaDoble<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ListaEnlazadaDoble<Libro> libros) {
        this.libros = libros;
    }

    public ListaEnlazadaDoble<Biblioteca> getBibliotecas() {
        return bibliotecas;
    }

    public void setBibliotecas(ListaEnlazadaDoble<Biblioteca> bibliotecas) {
        this.bibliotecas = bibliotecas;
    }

    public ListaEnlazadaDoble<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ListaEnlazadaDoble<Conexion> conexiones) {
        this.conexiones = conexiones;
    }

    public boolean isHayBibliotecas() {
        return hayBibliotecas;
    }

    public boolean isHayConexiones() {
        return hayConexiones;
    }

    public boolean isHayLibros() {
        return hayLibros;
    }

    public ArbolAVL getArbolAVL() {
        return arbolAVL;
    }

    public void setArbolAVL(ArbolAVL arbolAVL) {
        this.arbolAVL = arbolAVL;
    }

    public ArbolB getArbolB() {
        return arbolB;
    }

    public void setArbolB(ArbolB arbolB) {
        this.arbolB = arbolB;
    }

    public ArbolBMas getArbolBMas() {
        return arbolBMas;
    }

    public void setArbolBMas(ArbolBMas arbolBMas) {
        this.arbolBMas = arbolBMas;
    }

    public TablaHashLibros getTablaHashLibros() {
        return tablaHashLibros;
    }

    public void setTablaHashLibros(TablaHashLibros tablaHashLibros) {
        this.tablaHashLibros = tablaHashLibros;
    }

    public int[][] getMatrizAdyacenciaTiempos() {
        return matrizAdyacenciaTiempos;
    }

    public void setMatrizAdyacenciaTiempos(int[][] matrizAdyacenciaTiempos) {
        this.matrizAdyacenciaTiempos = matrizAdyacenciaTiempos;
    }

    public double[][] getMatrizAdyacenciaCostos() {
        return matrizAdyacenciaCostos;
    }

    public void setMatrizAdyacenciaCostos(double[][] matrizAdyacenciaCostos) {
        this.matrizAdyacenciaCostos = matrizAdyacenciaCostos;
    }

}
