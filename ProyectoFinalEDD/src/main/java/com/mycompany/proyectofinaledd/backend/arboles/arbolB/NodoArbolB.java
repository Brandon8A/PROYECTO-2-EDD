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

    // ---- Eliminación ----
    public void eliminar(Libro k) {
        int i = 0;
        while (i < numeroActualClaves && k.getAnio() > claves[i].getAnio()) {
            i++;
        }

        // Caso 1: El libro está en este nodo
        if (i < numeroActualClaves && claves[i].getAnio() == k.getAnio()) {
            if (hoja) {
                eliminarDeHoja(i);
            } else {
                eliminarDeNoHoja(i);
            }
        } else {
            // Caso 2: El libro está en un subárbol
            if (hoja) {
                System.out.println("⚠ No se encontró el libro con año: " + k.getAnio());
                return;
            }

            // Bandera para saber si está al final
            boolean ultimaPos = (i == numeroActualClaves);

            // Si el hijo tiene menos de t claves, se corrige antes de descender
            if (nodosHijos[i].getNumeroActualClaves() < gradoMinimo) {
                llenar(i);
            }

            // Si se fusionó el hijo, puede haber cambiado el índice
            if (ultimaPos && i > numeroActualClaves) {
                nodosHijos[i - 1].eliminar(k);
            } else {
                nodosHijos[i].eliminar(k);
            }
        }
    }

// ---- Eliminar clave en hoja ----
    private void eliminarDeHoja(int i) {
        for (int j = i + 1; j < numeroActualClaves; j++) {
            claves[j - 1] = claves[j];
        }
        numeroActualClaves--;
    }

// ---- Eliminar clave en nodo no hoja ----
    private void eliminarDeNoHoja(int i) {
        Libro k = claves[i];

        // Caso A: El hijo izquierdo tiene >= t claves
        if (nodosHijos[i].getNumeroActualClaves() >= gradoMinimo) {
            Libro pred = obtenerPredecesor(i);
            claves[i] = pred;
            nodosHijos[i].eliminar(pred);
        } // Caso B: El hijo derecho tiene >= t claves
        else if (nodosHijos[i + 1].getNumeroActualClaves() >= gradoMinimo) {
            Libro succ = obtenerSucesor(i);
            claves[i] = succ;
            nodosHijos[i + 1].eliminar(succ);
        } // Caso C: Ambos hijos tienen t-1 claves → Fusionar
        else {
            fusionar(i);
            nodosHijos[i].eliminar(k);
        }
    }

// ---- Obtener predecesor ----
    private Libro obtenerPredecesor(int i) {
        NodoArbolB actual = nodosHijos[i];
        while (!actual.hoja) {
            actual = actual.nodosHijos[actual.numeroActualClaves];
        }
        return actual.claves[actual.numeroActualClaves - 1];
    }

// ---- Obtener sucesor ----
    private Libro obtenerSucesor(int i) {
        NodoArbolB actual = nodosHijos[i + 1];
        while (!actual.hoja) {
            actual = actual.nodosHijos[0];
        }
        return actual.claves[0];
    }

// ---- Asegurar que el hijo tenga al menos t claves ----
    private void llenar(int i) {
        if (i != 0 && nodosHijos[i - 1].getNumeroActualClaves() >= gradoMinimo) {
            pedirPrestadoDeAnterior(i);
        } else if (i != numeroActualClaves && nodosHijos[i + 1].getNumeroActualClaves() >= gradoMinimo) {
            pedirPrestadoDeSiguiente(i);
        } else {
            if (i != numeroActualClaves) {
                fusionar(i);
            } else {
                fusionar(i - 1);
            }
        }
    }

// ---- Pedir una clave al hijo izquierdo ----
    private void pedirPrestadoDeAnterior(int i) {
        NodoArbolB hijo = nodosHijos[i];
        NodoArbolB hermano = nodosHijos[i - 1];

        for (int j = hijo.numeroActualClaves - 1; j >= 0; j--) {
            hijo.claves[j + 1] = hijo.claves[j];
        }

        if (!hijo.hoja) {
            for (int j = hijo.numeroActualClaves; j >= 0; j--) {
                hijo.nodosHijos[j + 1] = hijo.nodosHijos[j];
            }
        }

        hijo.claves[0] = claves[i - 1];

        if (!hermano.hoja) {
            hijo.nodosHijos[0] = hermano.nodosHijos[hermano.numeroActualClaves];
        }

        claves[i - 1] = hermano.claves[hermano.numeroActualClaves - 1];

        hijo.numeroActualClaves++;
        hermano.numeroActualClaves--;
    }

// ---- Pedir una clave al hijo derecho ----
    private void pedirPrestadoDeSiguiente(int i) {
        NodoArbolB hijo = nodosHijos[i];
        NodoArbolB hermano = nodosHijos[i + 1];

        hijo.claves[hijo.numeroActualClaves] = claves[i];

        if (!hijo.hoja) {
            hijo.nodosHijos[hijo.numeroActualClaves + 1] = hermano.nodosHijos[0];
        }

        claves[i] = hermano.claves[0];

        for (int j = 1; j < hermano.numeroActualClaves; j++) {
            hermano.claves[j - 1] = hermano.claves[j];
        }

        if (!hermano.hoja) {
            for (int j = 1; j <= hermano.numeroActualClaves; j++) {
                hermano.nodosHijos[j - 1] = hermano.nodosHijos[j];
            }
        }

        hijo.numeroActualClaves++;
        hermano.numeroActualClaves--;
    }

// ---- Fusionar hijo i con su hermano derecho (i+1) ----
    private void fusionar(int i) {
        NodoArbolB hijo = nodosHijos[i];
        NodoArbolB hermano = nodosHijos[i + 1];

        hijo.claves[gradoMinimo - 1] = claves[i];

        for (int j = 0; j < hermano.numeroActualClaves; j++) {
            hijo.claves[j + gradoMinimo] = hermano.claves[j];
        }

        if (!hijo.hoja) {
            for (int j = 0; j <= hermano.numeroActualClaves; j++) {
                hijo.nodosHijos[j + gradoMinimo] = hermano.nodosHijos[j];
            }
        }

        for (int j = i + 1; j < numeroActualClaves; j++) {
            claves[j - 1] = claves[j];
        }

        for (int j = i + 2; j <= numeroActualClaves; j++) {
            nodosHijos[j - 1] = nodosHijos[j];
        }

        hijo.numeroActualClaves += hermano.numeroActualClaves + 1;
        numeroActualClaves--;
    }

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
