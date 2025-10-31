/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend;

import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.exception.ExceptionBibliotecaMagica;
import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;
import com.mycompany.proyectofinaledd.backend.grafo.GrafoBiblioteca;
import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.libro.TypeEstado;
import com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazada;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazada;
import com.mycompany.proyectofinaledd.frontend.filechooser.FileChooser;
import com.mycompany.proyectofinaledd.frontend.inicio.Inicio;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

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
    private ListaEnlazada<String> erroresCargaArchivos;
    private ListaEnlazada<Libro> libros;
    private ListaEnlazada<Biblioteca> bibliotecas;
    private ListaEnlazada<Conexion> conexiones;

    public Controlador() {
        this.bibliotecas = new ListaEnlazada<>();
        this.libros = new ListaEnlazada<>();
        this.conexiones = new ListaEnlazada<>();
        this.erroresCargaArchivos = new ListaEnlazada<>();
    }

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
                        System.out.println("⚠ Línea ignorada (formato incorrecto): " + linea);
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
                        Biblioteca bibliotecaOrigen = grafoBibliotecas.getBiblioteca(idOrigen);
                        Biblioteca bibliotecaDestino = grafoBibliotecas.getBiblioteca(idDestino);

                        if (bibliotecaOrigen == null) {
                            System.out.println("⚠No se encontró biblioteca de origen: " + idOrigen);
                            continue;
                        }

                        if (bibliotecaDestino == null) {
                            System.out.println("⚠No se encontró biblioteca de destino: " + idDestino);
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

                        // Agregar a la lista de libros global
                        this.libros.agregarValorAlFinal(libro);

                    } catch (Exception e) {
                        System.out.println("⚠ Error procesando línea " + numeroLinea + ": " + e.getMessage());
                    }
                }

                System.out.println("✅ Carga completada: " + libros.getTamanio() + " libros cargados.");
                JOptionPane.showMessageDialog(null,
                        "Se cargaron " + libros.getTamanio() + " libros con éxito.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                throw new ExceptionBibliotecaMagica("Error al leer el archivo de libros: " + e.getMessage());
            }

        } catch (ExceptionBibliotecaMagica e) {
            JOptionPane.showMessageDialog(this.inicio, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Funcion que indica si el isbn es valido: si existe pero con el mismo titulo
     * es valido, si existe pero con otro titulo se rechaza
     * @param isbn isbn a comparar con los ya registrados
     * @param titulo titulo del libro a comparar
     * @return retorna verdadero si el isbn es valido o falso si es invalido
     */
    private boolean validarISBN(String isbn, String titulo) {
        NodoListaEnlazada<Libro> actual = this.libros.getInicio();
        while (actual != null) {
            Libro existente = actual.getDato();
            if (existente.getISBN().equalsIgnoreCase(isbn)) {
                // ISBN ya existe, verificar título
                if (!existente.getTitulo().equalsIgnoreCase(titulo)) {
                    // ISBN duplicado con otro título → error
                    /*
                    JOptionPane.showMessageDialog(null,
                            "Error: El ISBN " + isbn + " ya está registrado para otro libro: "
                            + existente.getTitulo(),
                            "ISBN duplicado", JOptionPane.ERROR_MESSAGE);
                    */
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
                    System.out.println("⚠ Línea ignorada (formato incorrecto): " + linea);
                    this.erroresCargaArchivos.agregarValorAlFinal("Error de formato incorrecto en linea: " + numeroLinea + ": " + linea);
                    continue;
                }
                
                //Verifica que el id de la nueva biblioteca no se repita con otra ya ingresada
                if (validoIdBiblioteca(datos[0])) {
                    this.erroresCargaArchivos.agregarValorAlFinal("Error de ID en linea: " + numeroLinea + ": " + linea);
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

                } catch (Exception e) {
                    System.out.println("⚠ Error procesando línea " + numeroLinea + ": " + e.getMessage());
                }
            }

            System.out.println("✅ Carga completada: " + bibliotecas.getTamanio() + " bibliotecas cargadas.");
            JOptionPane.showMessageDialog(null,
                    "Se cargaron un total de: " + bibliotecas.getTamanio() + " bibliotecas con éxito!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            throw new ExceptionBibliotecaMagica("Error al leer el archivo de bibliotecas: " + e.getMessage());
        }
    }
    
    /**
     * Funcion que valida si un id esta o no repetido dentro de la lista enlazada
     * de bibliotecas
     * @param idBiblioteca el id de la biblioteca que se va a ingresar
     * @return retorna verdadero o falso si el id existe o no
     */
    private boolean validoIdBiblioteca(String idBiblioteca){
        NodoListaEnlazada<Biblioteca> actual = this.bibliotecas.getInicio();
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
                    continue;
                }

                try {
                    String nombreOrigen = datos[0].trim();
                    String nombreDestino = datos[1].trim();
                    double tiempo = Double.parseDouble(datos[2].trim());
                    double costo = Double.parseDouble(datos[3].trim());

                    // Buscar las bibliotecas registradas en el grafo
                    Biblioteca origen = grafoBibliotecas.getBiblioteca(nombreOrigen);
                    Biblioteca destino = grafoBibliotecas.getBiblioteca(nombreDestino);

                    if (origen == null || destino == null) {
                        System.out.println("⚠ No se encontró una o ambas bibliotecas: " + nombreOrigen + " -> " + nombreDestino);
                        continue;
                    }

                    // Crear conexión y agregar al grafo
                    Conexion conexion = new Conexion(origen, destino, tiempo, costo);
                    conexiones.agregarValorAlFinal(conexion);
                    //conexiones.add(conexion);

                    grafoBibliotecas.agregarConexion(origen, destino, tiempo, costo);

                } catch (NumberFormatException e) {
                    System.out.println("⚠ Error de formato numérico en línea " + numeroLinea + ": " + e.getMessage());
                }
            }

            System.out.println("✅ Carga completada: " + conexiones.getTamanio() + " conexiones agregadas al grafo.");

        } catch (IOException e) {
            throw new ExceptionBibliotecaMagica("Error al leer el archivo de conexiones: " + e.getMessage());
        }
    }

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

    public ListaEnlazada<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ListaEnlazada<Libro> libros) {
        this.libros = libros;
    }

    public ListaEnlazada<Biblioteca> getBibliotecas() {
        return bibliotecas;
    }

    public void setBibliotecas(ListaEnlazada<Biblioteca> bibliotecas) {
        this.bibliotecas = bibliotecas;
    }

    public ListaEnlazada<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ListaEnlazada<Conexion> conexiones) {
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
    
    
}
