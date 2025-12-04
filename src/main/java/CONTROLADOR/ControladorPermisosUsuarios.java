/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

public class ControladorPermisosUsuarios {

    public static boolean puedeRegistrarVenta(String rol) {
        return rol != null && (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Ventas"));
    }

    public static boolean puedeRegistrarCompra(String rol) {
        return rol != null && (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Compras"));
    }

    public static boolean puedeAccederClientes(String rol) {
        return rol != null && (rol.equalsIgnoreCase("Administrador") || rol.equalsIgnoreCase("Ventas") || rol.equalsIgnoreCase("Compras"));
    }

    public static boolean puedeAccederInventario(String rol) {
        return rol != null && rol.equalsIgnoreCase("Administrador");
    }
    
    public static boolean puedeAccederLogueoUsuarios(String rol) {
        return rol != null && rol.equalsIgnoreCase("Administrador");
    }
}

