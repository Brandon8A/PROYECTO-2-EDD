/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.pruebas;

import com.mycompany.proyectofinaledd.backend.Controlador;
import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazadaDoble;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author brandon
 */
public class VentanaSimulacion extends JFrame{
    private Controlador controlador;
    private PanelGrafo panelGrafo;
    private Map<String, PanelBiblioteca> panelesBibliotecas = new HashMap<>();

    public VentanaSimulacion(Controlador controlador) {
        this.controlador = controlador;
        setTitle("Simulación de Red de Bibliotecas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel que dibuja el grafo visualmente
        this.panelGrafo = new PanelGrafo(controlador);
        add(panelGrafo, BorderLayout.CENTER);

        // Panel lateral con las bibliotecas (colas)
        JPanel panelBibliotecas = new JPanel();
        panelBibliotecas.setLayout(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollBibliotecas = new JScrollPane(panelBibliotecas);
        scrollBibliotecas.setPreferredSize(new Dimension(500, 800));
        add(scrollBibliotecas, BorderLayout.EAST);

        // Crear los paneles por biblioteca
        NodoListaEnlazadaDoble<Biblioteca> actual = controlador.getBibliotecas().getInicio();
        while (actual != null) {
            Biblioteca b = actual.getDato();
            PanelBiblioteca panel = new PanelBiblioteca(b);
            panelBibliotecas.add(panel);
            panelesBibliotecas.put(b.getId(), panel);
            // Iniciar el hilo de simulación
            new HiloBiblioteca(b, controlador, panel, this).start();
            actual = actual.getSiguiente();
        }

        JButton btnActualizar = new JButton("🔄 Actualizar Grafo");
        btnActualizar.addActionListener(e -> {
            panelGrafo.repaint();
            actualizarBibliotecas();
        });
        add(btnActualizar, BorderLayout.SOUTH);
    }

    public void actualizarBibliotecas() {
        for (PanelBiblioteca p : panelesBibliotecas.values()) {
            p.actualizarVista();
        }
    }
}
