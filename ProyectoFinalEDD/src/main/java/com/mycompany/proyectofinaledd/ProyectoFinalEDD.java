/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinaledd;

import com.mycompany.proyectofinaledd.backend.Controlador;
import com.mycompany.proyectofinaledd.frontend.inicio.Inicio;

/**
 *
 * @author brandon
 */
public class ProyectoFinalEDD {

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Inicio iniciar = new Inicio(controlador);
        controlador.setInicio(iniciar);
        iniciar.setLocationRelativeTo(null);
        iniciar.setVisible(true);
    }
}
