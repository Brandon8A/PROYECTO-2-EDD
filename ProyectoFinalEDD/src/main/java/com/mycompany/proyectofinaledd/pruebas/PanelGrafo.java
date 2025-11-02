/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.pruebas;

import com.mycompany.proyectofinaledd.backend.Controlador;
import com.mycompany.proyectofinaledd.backend.conexion.Conexion;
import com.mycompany.proyectofinaledd.backend.grafo.Biblioteca;
import com.mycompany.proyectofinaledd.backend.listaenlazada.NodoListaEnlazadaDoble;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author brandon
 */
public class PanelGrafo extends JPanel{
    private Controlador controlador;
    private Map<String, Point> posicionesNodos;

    public PanelGrafo(Controlador controlador) {
        this.controlador = controlador;
        this.posicionesNodos = new HashMap<>();
        generarPosiciones();
        setBackground(Color.WHITE);
    }

    private void generarPosiciones() {
        Random rand = new Random();
        NodoListaEnlazadaDoble<Biblioteca> actual = controlador.getBibliotecas().getInicio();
        while (actual != null) {
            Biblioteca b = actual.getDato();
            posicionesNodos.put(b.getId(), new Point(rand.nextInt(600) + 50, rand.nextInt(600) + 50));
            actual = actual.getSiguiente();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        // Dibujar conexiones
        NodoListaEnlazadaDoble<Conexion> con = controlador.getConexiones().getInicio();
        while (con != null) {
            Conexion c = con.getDato();
            Point p1 = posicionesNodos.get(c.getOrigen().getId());
            Point p2 = posicionesNodos.get(c.getDestino().getId());
            if (p1 != null && p2 != null) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(p1.x + 25, p1.y + 25, p2.x + 25, p2.y + 25);
                // Etiqueta con el costo o tiempo
                String texto = "T:" + c.getPesoTiempo()+ " | $" + c.getPesoCosto();
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(texto, (p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
            }
            con = con.getSiguiente();
        }

        // Dibujar nodos (bibliotecas)
        NodoListaEnlazadaDoble<Biblioteca> actual = controlador.getBibliotecas().getInicio();
        while (actual != null) {
            Biblioteca b = actual.getDato();
            Point p = posicionesNodos.get(b.getId());
            if (p != null) {
                g2.setColor(new Color(135, 206, 250));
                g2.fillOval(p.x, p.y, 60, 60);
                g2.setColor(Color.BLACK);
                g2.drawOval(p.x, p.y, 60, 60);
                g2.drawString(b.getNombre(), p.x - 5, p.y - 5);
            }
            actual = actual.getSiguiente();
        }
    }
}
