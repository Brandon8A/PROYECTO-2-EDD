/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinaledd.frontend.filechooser;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author brandon
 */
public class FileChooser {
    private JFileChooser selector = new JFileChooser();
    
    //Funcion que obtiene el path de la carpeta seleccionada
    public String selectPath() {
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setDialogTitle("Selecciona la carpeta que se usara como persistencia");
        final int result = selector.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            final File folder = selector.getSelectedFile();
            final String path = folder.getAbsolutePath();
            System.out.println("Carpeta seleccionada: " + path);
            return path;
        } else {
            System.out.println("Accion cancelada");
            return null;
        }
    }

    /**
     * Funcion que obtiene el path absoluto del archivo csv que se desea cargar
     * @return 
     */
    public String seleccionarArchivoCSV() {
        // Filtro para solo mostrar archivos .csv
        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv"));
        selector.setDialogTitle("Seleccione el archivo con extension CSV");
        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            if (archivo != null && archivo.exists() && archivo.isFile()) {

                //final File folder = selector.getSelectedFile();
                final String path = archivo.getAbsolutePath();
                System.out.println("Archivo seleccionado: " + path);
                return path;
            } else {
                System.out.println("Error, No se selecciono ningun archivo.");
                return null;
            }
        } else {
            System.out.println("Accion cancelada");
            return null;
        }
    }
}
