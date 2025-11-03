/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolB;

import com.mycompany.proyectofinaledd.backend.libro.Libro;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author brandon-ochoa
 */
public class NodoArbolB {

    private Libro[] claves;
    private int gradoMinimo;
    private NodoArbolB[] nodosHijos;
    private int numeroActualClaves;
    boolean hoja;

    public NodoArbolB(int gradoMinimo, boolean hoja) {
        this.gradoMinimo = gradoMinimo;
        this.hoja = hoja;
        this.claves = new Libro[2 * gradoMinimo - 1];//Asignando la cantidad de claves maxima que cada nodo puede tener
        this.nodosHijos = new NodoArbolB[2 * gradoMinimo];//Asignando la cantidad maxima de hijos que cada nodo puede tener
        this.numeroActualClaves = 0;
    }

    public void traverse() {
        int i;
        for (i = 0; i < this.numeroActualClaves; i++) {
            if (!hoja) {
                this.nodosHijos[i].traverse();
                System.out.print(" " + this.claves[i].getTitulo() + ": " + this.claves[i].getAnio());
            }
        }
        if (!hoja) {
            nodosHijos[i].traverse();
        }
    }

    // ---- Búsqueda ----
    public NodoArbolB search(Libro k) {
        int i = 0;
        while (i < numeroActualClaves && k.getAnio() > this.claves[i].getAnio()) {
            i++;
        }

        if (i < numeroActualClaves && this.claves[i].getAnio() == k.getAnio()) {
            return this;
        }

        if (hoja) {
            return null;
        }

        return this.nodosHijos[i].search(k);
    }

    /*
    public NodoArbolB buscar(String k) {
        
    }
     */
    public void insertarEnNodoNoLleno(Libro k) {
        int i = this.numeroActualClaves - 1;

        // Si el nodo es hoja
        if (this.hoja) {
            // Mover las claves mayores una posición hacia adelante
            while (i >= 0 && k.getAnio() < this.claves[i].getAnio()) {
                this.claves[i + 1] = this.claves[i];
                i--;
            }

            // Insertar la nueva clave
            this.claves[i + 1] = k;
            this.numeroActualClaves++;
        } else {
            // Buscar el hijo adecuado para descender
            while (i >= 0 && this.claves[i].getAnio() > k.getAnio()) {
                i--;
            }

            // Si el hijo está lleno, dividirlo
            if (this.nodosHijos[i + 1].numeroActualClaves == 2 * gradoMinimo - 1) {
                dividirNodoHijoLlenoEnDos(i + 1, nodosHijos[i + 1]);

                // Después de dividir, decidir a cuál hijo descender
                if (this.claves[i + 1].getAnio() < k.getAnio()) {
                    i++;
                }
            }

            // Llamada recursiva al hijo correspondiente
            nodosHijos[i + 1].insertarEnNodoNoLleno(k);
        }
    }

    public void dividirNodoHijoLlenoEnDos(int i, NodoArbolB y) {
        // Crear un nuevo nodo que contendrá t-1 claves de y
        NodoArbolB z = new NodoArbolB(y.gradoMinimo, y.hoja);
        z.numeroActualClaves = gradoMinimo - 1;

        // Copiar las últimas t-1 claves de y a z
        for (int j = 0; j < gradoMinimo - 1; j++) {
            z.claves[j] = y.claves[j + gradoMinimo];
        }

        // Copiar los últimos t hijos de y a z (si no es hoja)
        if (!y.hoja) {
            for (int j = 0; j < gradoMinimo; j++) {
                z.nodosHijos[j] = y.nodosHijos[j + gradoMinimo];
            }
        }

        // Reducir el número de claves en y
        y.numeroActualClaves = gradoMinimo - 1;

        // Mover los hijos del nodo actual una posición a la derecha
        for (int j = numeroActualClaves; j >= i + 1; j--) {
            this.nodosHijos[j + 1] = this.nodosHijos[j];
        }
        this.nodosHijos[i + 1] = z;

        // Mover las claves del nodo actual una posición a la derecha
        for (int j = numeroActualClaves - 1; j >= i; j--) {
            this.claves[j + 1] = this.claves[j];
        }

        // Subir la clave del medio de y al nodo actual
        claves[i] = y.claves[gradoMinimo - 1];
        numeroActualClaves++;
    }

    // ---- Genera recursivamente el contenido del archivo DOT ----
    public void generateDotRec(PrintWriter out) {
        out.print("  node" + this.hashCode() + " [label=\"");

        for (int i = 0; i < this.numeroActualClaves; i++) {
            Libro libro = this.claves[i];
            if (libro != null) {
                out.print(libro.getTitulo() + "\\n" + libro.getAutor() + "\\n" + libro.getAnio());
            }

            if (i < this.numeroActualClaves - 1) {
                out.print(" | ");
            }
        }

        out.println("\"];");

        if (!hoja) {
            for (int i = 0; i <= this.numeroActualClaves; i++) {
                out.println("  node" + this.hashCode() + " -> node" + this.nodosHijos[i].hashCode() + ";");
                if (this.nodosHijos[i] != null) {
                    this.nodosHijos[i].generateDotRec(out);
                }
            }
        }
    }

    public void buscarPorRango(int anioInicio, int anioFin, List<Libro> resultados) {
    int i;
    for (i = 0; i < numeroActualClaves; i++) {
        // Si no es hoja, explorar subárbol izquierdo
        if (!hoja) {
            nodosHijos[i].buscarPorRango(anioInicio, anioFin, resultados);
        }

        int anio = claves[i].getAnio();
        if (anio >= anioInicio && anio <= anioFin) {
            resultados.add(claves[i]);
        }
    }

    // Explorar el último hijo
    if (!hoja) {
        nodosHijos[i].buscarPorRango(anioInicio, anioFin, resultados);
    }
}
    
    /*
    public void generateDotRec(PrintWriter out) {
        out.print("  node" + this.hashCode() + " [label=\"");
        for (int i = 0; i < this.numeroActualClaves; i++) {
            out.print(this.claves[i]);
            if (i < this.numeroActualClaves - 1) out.print(" | ");
        }
        out.println("\"];");

        if (!hoja) {
            for (int i = 0; i <= this.numeroActualClaves; i++) {
                out.println("  node" + this.hashCode() + " -> node" + this.nodosHijos[i].hashCode() + ";");
                this.nodosHijos[i].generateDotRec(out);
            }
        }
    }*/
    public Libro[] getClaves() {
        return claves;
    }

    public void setClaves(Libro[] claves) {
        this.claves = claves;
    }

    public int getGradoMinimo() {
        return gradoMinimo;
    }

    public void setGradoMinimo(int gradoMinimo) {
        this.gradoMinimo = gradoMinimo;
    }

    public NodoArbolB[] getNodosHijos() {
        return nodosHijos;
    }

    public void setNodosHijos(NodoArbolB[] nodosHijos) {
        this.nodosHijos = nodosHijos;
    }

    public int getNumeroActualClaves() {
        return numeroActualClaves;
    }

    public void setNumeroActualClaves(int numeroActualClaves) {
        this.numeroActualClaves = numeroActualClaves;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }

}
