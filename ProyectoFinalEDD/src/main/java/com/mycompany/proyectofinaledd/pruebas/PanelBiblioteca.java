/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.pruebas;

import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;
import com.mycompany.proyectofinaledd.backend.libro.Libro;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazadaDoble;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author brandon
 */
public class PanelBiblioteca extends JPanel{
     private Biblioteca biblioteca;
    private JPanel panelIngreso, panelTraspaso, panelSalida;

    public PanelBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(biblioteca.getNombre()));
        setBackground(new Color(230, 240, 255));

        // Panel contenedor de las 3 colas
        JPanel contenedorColas = new JPanel(new GridLayout(3, 1, 5, 5));
        contenedorColas.add(crearScrollCola("Ingreso", Color.GREEN, biblioteca.getColaIngreso()));
        contenedorColas.add(crearScrollCola("Traspaso", Color.YELLOW, biblioteca.getColaTraspaso()));
        contenedorColas.add(crearScrollCola("Salida", Color.CYAN, biblioteca.getColaSalida()));

        add(contenedorColas, BorderLayout.CENTER);
    }

    private JScrollPane crearScrollCola(String nombre, Color colorFondo, com.mycompany.proyectofinaledd.backend.listaenlazada.ListaEnlazadaDoble<Libro> cola) {
        JPanel panelLibros = new JPanel();
        panelLibros.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelLibros.setBackground(colorFondo);

        NodoListaEnlazadaDoble<Libro> actual = cola.getInicio();
        while (actual != null) {
            Libro libro = actual.getDato();
            JButton botonLibro = new JButton("<html><b>" + libro.getTitulo() + "</b><br>ISBN: " + libro.getISBN() + "</html>");
            botonLibro.setPreferredSize(new Dimension(120, 50));
            botonLibro.setBackground(Color.WHITE);
            botonLibro.addActionListener(e -> mostrarInfoLibro(libro));
            panelLibros.add(botonLibro);
            actual = actual.getSiguiente();
        }

        JScrollPane scroll = new JScrollPane(panelLibros);
        scroll.setBorder(BorderFactory.createTitledBorder(nombre));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private void mostrarInfoLibro(Libro libro) {
        JOptionPane.showMessageDialog(this,
                "📚 Título: " + libro.getTitulo() + "\n" +
                        "Autor: " + libro.getAutor() + "\n" +
                        "Género: " + libro.getGenero() + "\n" +
                        "Año: " + libro.getAnio() + "\n" +
                        "Estado: " + libro.getEstado(),
                "Información del Libro", JOptionPane.INFORMATION_MESSAGE);
    }

    public void actualizarVista() {
        removeAll();
        setBorder(BorderFactory.createTitledBorder(biblioteca.getNombre()));
        JPanel contenedorColas = new JPanel(new GridLayout(3, 1, 5, 5));
        contenedorColas.add(crearScrollCola("Ingreso", Color.GREEN, biblioteca.getColaIngreso()));
        contenedorColas.add(crearScrollCola("Traspaso", Color.YELLOW, biblioteca.getColaTraspaso()));
        contenedorColas.add(crearScrollCola("Salida", Color.CYAN, biblioteca.getColaSalida()));
        add(contenedorColas, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
