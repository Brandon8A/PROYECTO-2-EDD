/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.arboles.arbolAVL;

import com.mycompany.proyectofinaledd.backend.libro.Libro;

/**
 *
 * @author brandon
 */
public class ArbolAVL {

    private NodoAVL raiz = null;

    public NodoAVL insertarNodo(NodoAVL nodo, Libro libro) {
        if (nodo == null) {
            return new NodoAVL(libro);
        }

        //Realizar comparacion de titulos
        if (libro.getTitulo().compareTo(nodo.getLibro().getTitulo()) < 0) {
            nodo.setNodoIzquierdo(insertarNodo(nodo.getNodoIzquierdo(), libro));
        } else if (libro.getTitulo().compareTo(nodo.getLibro().getTitulo()) > 0) {
            nodo.setNodoDerecho(insertarNodo(nodo.getNodoDerecho(), libro));
        } else {
            return nodo;
        }

        //Actualizar factor equilibrio
        int alturaIzq = altura(nodo.getNodoIzquierdo());//Obteniendo el factor equilibiro del hijo izquierdo
        int alturaDer = altura(nodo.getNodoDerecho());//Obteniendo el factor equilibiro del hijo derecho
        nodo.setFactorEquilibrio(alturaDer - alturaIzq);//Estableciendo el factor de equilibro realizando la restas de los fe de sus hijos

        int fe = nodo.getFactorEquilibrio();//Obteniendo el factor equilibrio del nodo

        // Rotacion Izquierda-Izquierda
        if (fe == -2 && libro.getTitulo().compareTo(nodo.getLibro().getTitulo()) < 0) {
            return rotarIzquierdaIzquierda(nodo);
        }

        // Rotacion Derecha-Derecha
        if (fe == 2 && libro.getTitulo().compareTo(nodo.getLibro().getTitulo()) > 0) {
            return rotarDerechaDerecha(nodo);
        }

        // Rotacion Izquierda-Derecha
        if (fe == -2 && libro.getTitulo().compareTo(nodo.getLibro().getTitulo()) > 0) {
            return rotarIzquierdaDerecha(nodo);
        }

        //Rotacion Derecha-Izquierda
        if (fe == +2 && libro.getTitulo().compareTo(nodo.getLibro().getTitulo()) < 0) {
            return rotarDerechaIzquierda(nodo);
        }

        // Retornar el nodo
        return nodo;
    }

    /*Funcion para obtener la altura de un nodo*/
    private int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        //Comparando altura de los hijos para obtener la altura mayor
        int izq = altura(nodo.getNodoIzquierdo());
        int der = altura(nodo.getNodoDerecho());
        return 1 + obtenerMayor(der, izq);//retorna la altura mayor del hijo + 1 que indica la altura del nodo.
    }

    public static int obtenerMayor(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    /*
 * Funcion que retorna la raiz de un arbol el cual tiene un factor de equilibrio 2 y donde su hijo derecho tiene
 * factor de equilibrio 1 que seria una rotacion derecha derecha
 *     a
 *      \             b
 *       b   --->    / \
 *        \         a   c
 *         c
     */
    private NodoAVL rotarDerechaDerecha(NodoAVL a) {
        NodoAVL b = a.getNodoDerecho();//Referencia del nodo derecho de a (raiz).

        a.setNodoDerecho(b.getNodoIzquierdo());//haciendo nullptr el hijo derecho de a (raiz) para que pueda convertirse en hijo
        b.setNodoIzquierdo(a);//Estableciendo hijo izquierdo a b (nueva raiz).

        // Actualización de FE
        if (b.getFactorEquilibrio() == +1) {
            a.setFactorEquilibrio(0);
            b.setFactorEquilibrio(0);
        } else if (b.getFactorEquilibrio() == 0) {
            a.setFactorEquilibrio(+1);
            b.setFactorEquilibrio(-1);
        }

        return b;
    }

    /*
 * Funcion que retorna la raiza de un arbol el cual tiene un factor de equilibrio -2 y donde su hijo izquierdo tiene
 * factor de equilibrio -1 que seria una rotacion izquierda izquierda
 *       c
 *      /                b
 *     b     --->       / \
 *    /                a   c
 *   a
     */
    private NodoAVL rotarIzquierdaIzquierda(NodoAVL c) {
        NodoAVL b = c.getNodoIzquierdo();

        c.setNodoIzquierdo(b.getNodoDerecho());
        b.setNodoDerecho(c);

        // Actualización de FE
        if (b.getFactorEquilibrio() == -1) {
            c.setFactorEquilibrio(0);
            b.setFactorEquilibrio(0);
        } else if (b.getFactorEquilibrio() == 0) {
            c.setFactorEquilibrio(-1);
            b.setFactorEquilibrio(+1);
        }
        return b;
    }

    /*
 * Funcion que retorna un NodoAVL
 *     a
 *      \             b
 *       c   --->    / \
 *      /           a   c
 *     b
     */
// Rotación Doble Derecha-Izquierda (RL)
    private NodoAVL rotarDerechaIzquierda(NodoAVL c) {
        NodoAVL a = c.getNodoDerecho();
        NodoAVL b = a.getNodoIzquierdo();

        c.setNodoDerecho(b.getNodoIzquierdo());
        a.setNodoIzquierdo(b.getNodoDerecho());

        b.setNodoIzquierdo(c);
        b.setNodoDerecho(a);

        // Actualización de FE según b
        if (b.getFactorEquilibrio() == 0) {
            c.setFactorEquilibrio(0);
            b.setFactorEquilibrio(0);
            a.setFactorEquilibrio(0);
        } else if (b.getFactorEquilibrio() == -1) {
            c.setFactorEquilibrio(0);
            b.setFactorEquilibrio(0);
            a.setFactorEquilibrio(+1);
        } else if (b.getFactorEquilibrio() == +1) {
            c.setFactorEquilibrio(-1);
            b.setFactorEquilibrio(0);
            a.setFactorEquilibrio(0);
        }

        return b;
    }

    /*
 * Funcion que retorna un NodoAVL
 *      c
 *     /          b
 *    a   --->   / \
 *     \        a   c
 *      b
     */
// Rotación Doble Izquierda-Derecha (LR)
    private NodoAVL rotarIzquierdaDerecha(NodoAVL c) {
        NodoAVL a = c.getNodoIzquierdo();
        NodoAVL b = a.getNodoDerecho();

        a.setNodoDerecho(b.getNodoIzquierdo());
        c.setNodoIzquierdo(b.getNodoDerecho());

        b.setNodoIzquierdo(a);
        b.setNodoDerecho(c);

        // Actualización de FE según b
        if (b.getFactorEquilibrio() == 0) {
            a.setFactorEquilibrio(0);
            b.setFactorEquilibrio(0);
            c.setFactorEquilibrio(0);
        } else if (b.getFactorEquilibrio() == -1) {
            a.setFactorEquilibrio(0);
            b.setFactorEquilibrio(0);
            c.setFactorEquilibrio(+1);
        } else if (b.getFactorEquilibrio() == +1) {
            a.setFactorEquilibrio(-1);
            b.setFactorEquilibrio(0);
            c.setFactorEquilibrio(0);
        }

        return b;
    }
}
