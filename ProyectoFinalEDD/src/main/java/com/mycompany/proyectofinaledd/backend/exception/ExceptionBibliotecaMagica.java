/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.backend.exception;

/**
 *
 * @author brandon
 */
public class ExceptionBibliotecaMagica extends Exception {

    /**
     * Metodo constructor para la creacion de una excepcion, recibiendo por para-
     * metro el motivo de la excepcion.
     * @param message mensaje que indica el motivo por el cual se esta lanzando
     * dicha excepcion.
     */
    public ExceptionBibliotecaMagica(String message) {
        super(message);
    }
    
}
