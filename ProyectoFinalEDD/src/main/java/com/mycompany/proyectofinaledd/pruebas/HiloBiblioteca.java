/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.pruebas;

import com.mycompany.proyectofinaledd.backend.Controlador;
import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;
import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.libro.TypeEstado;
import javax.swing.SwingUtilities;

/**
 *
 * @author brandon
 */
public class HiloBiblioteca extends Thread{
    private final Biblioteca biblioteca;
    private final Controlador controlador;
    private final PanelBiblioteca panel;
    private final VentanaSimulacion ventana;

    public HiloBiblioteca(Biblioteca biblioteca, Controlador controlador, PanelBiblioteca panel, VentanaSimulacion ventana) {
        this.biblioteca = biblioteca;
        this.controlador = controlador;
        this.panel = panel;
        this.ventana = ventana;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Simular ingreso
                if (!biblioteca.getColaIngreso().estaVacia()) {
                    Libro libro = biblioteca.getColaIngreso().eliminarInicio();
                    libro.setEstado(TypeEstado.DISPONIBLE);
                    biblioteca.getColaTraspaso().agregarValorAlFinal(libro);
                    SwingUtilities.invokeLater(panel::actualizarVista);
                    Thread.sleep((long) (biblioteca.getTiempoIngreso() * 1000L));
                }

                // Simular traspaso
                if (!biblioteca.getColaTraspaso().estaVacia()) {
                    Libro libro = biblioteca.getColaTraspaso().eliminarInicio();
                    libro.setEstado(TypeEstado.EN_TRANSITO);
                    biblioteca.getColaSalida().agregarValorAlFinal(libro);
                    SwingUtilities.invokeLater(panel::actualizarVista);
                    Thread.sleep((long) (biblioteca.getTiempoTraspaso() * 1000L));
                }

                // Simular envío a destino
                if (!biblioteca.getColaSalida().estaVacia()) {
                    Libro libro = biblioteca.getColaSalida().eliminarInicio();
                    if (libro.getBibliotecaDestino() != null) {
                        libro.setEstado(TypeEstado.EN_TRANSITO);
                        libro.getBibliotecaDestino().getColaIngreso().agregarValorAlFinal(libro);
                    }
                    SwingUtilities.invokeLater(() -> {
                        panel.actualizarVista();
                        ventana.actualizarBibliotecas();
                    });
                    Thread.sleep((long) (biblioteca.getIntervaloDespacho()* 1000L));
                }

                Thread.sleep(1000); // Evita CPU alta
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo de biblioteca interrumpido: " + biblioteca.getNombre());
        }
    }
}
