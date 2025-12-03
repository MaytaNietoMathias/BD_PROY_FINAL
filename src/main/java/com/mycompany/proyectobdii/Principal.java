/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectobdii;

import CONTROLADOR.Conexion;
import VISTA.VistaLogin;


public class Principal {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        
        // Establecer la conexión
        if (conexion.establecerConexion() != null) {
            System.out.println("✅ La conexión se realizó correctamente.");
            
            // Si la conexión es exitosa, mostrar la ventana de Login
            VistaLogin loginVentana = new VistaLogin();
            loginVentana.setVisible(true);  // Mostrar la ventana de Login
        } else {
            System.out.println("❌ La conexión falló."); 
        }
    }
}
