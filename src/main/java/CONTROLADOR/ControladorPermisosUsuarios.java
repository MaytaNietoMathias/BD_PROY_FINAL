/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

public class ControladorPermisosUsuarios {

    public static boolean puedeRegistrarVenta(String rol) {
        return rol.equals("ADMIN") || rol.equals("VENTAS");
    }

    public static boolean puedeRegistrarCompra(String rol) {
        return rol.equals("ADMIN") || rol.equals("COMPRAS");
    }

    public static boolean puedeAccederClientes(String rol) {
        return rol.equals("ADMIN") || rol.equals("VENTAS") || rol.equals("COMPRAS");
    }

    public static boolean puedeAccederInventario(String rol) {
        return rol.equals("ADMIN");
    }
    
    public static boolean puedeAccederLogueoUsuarios(String rol) {
        return rol.equals("Administrador");
    }
}

