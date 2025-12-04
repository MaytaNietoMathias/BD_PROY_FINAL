/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.ProveedorModelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Statement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompraControlador {

    private Conexion conexion = new Conexion();

    // Obtener lista de proveedores
    public Map<Integer, String> obtenerProveedores() {
        Map<Integer, String> proveedores = new HashMap<>();
        String query = "SELECT id_proveedor, nombre_proveedor FROM proveedores ORDER BY nombre_proveedor";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                proveedores.put(rs.getInt("id_proveedor"), rs.getString("nombre_proveedor"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener proveedores: " + e.getMessage());
        }

        return proveedores;
    }

    // Obtener lista de productos
    public Map<Integer, String> obtenerProductos() {
        Map<Integer, String> productos = new HashMap<>();
        String query = "SELECT ID_Producto, Nombre_Producto FROM Productos ORDER BY Nombre_Producto";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.put(rs.getInt("ID_Producto"), rs.getString("Nombre_Producto"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener productos: " + e.getMessage());
        }

        return productos;
    }

    // Registrar compra y actualizar inventario
    public boolean registrarCompra(int idProveedor, int idProducto, int cantidad, 
                                   BigDecimal monto, java.time.LocalDate fecha) {
        
        try (Connection con = conexion.establecerConexion()) {
            
            // 1. Insertar en tabla Compras
            String queryInsertCompra = "INSERT INTO Compras (id_proveedor, id_producto, cantidad, monto, fecha) " +
                                      "VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement psCompra = con.prepareStatement(queryInsertCompra)) {
                psCompra.setInt(1, idProveedor);
                psCompra.setInt(2, idProducto);
                psCompra.setInt(3, cantidad);
                psCompra.setBigDecimal(4, monto);
                psCompra.setDate(5, Date.valueOf(fecha));
                psCompra.executeUpdate();
            }

            // 2. Actualizar inventario
            String queryObtenerInventario = "SELECT cantidad FROM Inventario WHERE id_producto = ?";
            int cantidadActual = 0;
            
            try (PreparedStatement psObtener = con.prepareStatement(queryObtenerInventario)) {
                psObtener.setInt(1, idProducto);
                try (ResultSet rs = psObtener.executeQuery()) {
                    if (rs.next()) {
                        cantidadActual = rs.getInt("cantidad");
                    }
                }
            }

            // 3. Actualizar o insertar en Inventario
            int nuevaCantidad = cantidadActual + cantidad;
            
            if (cantidadActual > 0) {
                // Actualizar inventario existente
                String queryUpdate = "UPDATE Inventario SET cantidad = ? WHERE id_producto = ?";
                try (PreparedStatement psUpdate = con.prepareStatement(queryUpdate)) {
                    psUpdate.setInt(1, nuevaCantidad);
                    psUpdate.setInt(2, idProducto);
                    psUpdate.executeUpdate();
                }
            } else {
                // Insertar nuevo inventario
                String queryInsertInventario = "INSERT INTO Inventario (id_producto, cantidad) VALUES (?, ?)";
                try (PreparedStatement psInsert = con.prepareStatement(queryInsertInventario)) {
                    psInsert.setInt(1, idProducto);
                    psInsert.setInt(2, cantidad);
                    psInsert.executeUpdate();
                }
            }

            System.out.println("✅ Compra registrada. Inventario actualizado: " + cantidadActual + " → " + nuevaCantidad);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error al registrar compra: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las compras
    public List<Map<String, Object>> obtenerTodasLasCompras() {
        List<Map<String, Object>> compras = new ArrayList<>();
        String query = "SELECT id_compra, id_proveedor, id_producto, cantidad, monto, fecha FROM Compras ORDER BY fecha DESC";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> compra = new HashMap<>();
                compra.put("id_compra", rs.getInt("id_compra"));
                compra.put("id_proveedor", rs.getInt("id_proveedor"));
                compra.put("id_producto", rs.getInt("id_producto"));
                compra.put("cantidad", rs.getInt("cantidad"));
                compra.put("monto", rs.getBigDecimal("monto"));
                compra.put("fecha", rs.getDate("fecha"));
                compras.add(compra);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener compras: " + e.getMessage());
        }

        return compras;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // MÉTODOS PARA CREAR NUEVOS PROVEEDORES Y PRODUCTOS
    // ═══════════════════════════════════════════════════════════════════════════

    // Registrar nuevo proveedor con datos formales
    public boolean registrarNuevoProveedor(String nombre, String ruc, String direccion, 
                                          String telefono, String email, String contacto) {
        // Validaciones
        if (nombre == null || nombre.trim().isEmpty() || ruc == null || ruc.trim().isEmpty()) {
            System.out.println("❌ Nombre y RUC son requeridos");
            return false;
        }

        // Validar formato de RUC (11 dígitos)
        if (!ruc.matches("^[0-9]{11}$")) {
            System.out.println("❌ RUC debe ser 11 dígitos");
            return false;
        }

        // Verificar que no exista proveedor con el mismo RUC
        if (proveedorExisteConRuc(ruc)) {
            System.out.println("❌ Ya existe un proveedor con este RUC");
            return false;
        }

        String query = "INSERT INTO Proveedores (nombre_proveedor, ruc, direccion, telefono, email, contacto, estado) " +
                      "VALUES (?, ?, ?, ?, ?, ?, 'Activo')";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, nombre.trim());
            ps.setString(2, ruc.trim());
            ps.setString(3, direccion != null ? direccion.trim() : "");
            ps.setString(4, telefono != null ? telefono.trim() : "");
            ps.setString(5, email != null ? email.trim() : "");
            ps.setString(6, contacto != null ? contacto.trim() : "");
            
            ps.executeUpdate();
            System.out.println("✅ Proveedor registrado correctamente: " + nombre);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }

    // Verificar si existe proveedor con el RUC
    public boolean proveedorExisteConRuc(String ruc) {
        String query = "SELECT id_proveedor FROM Proveedores WHERE ruc = ?";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, ruc);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("❌ Error al verificar RUC: " + e.getMessage());
            return false;
        }
    }

    // Obtener datos completos de un proveedor por RUC
    public ProveedorModelo obtenerProveedorPorRuc(String ruc) {
        String query = "SELECT * FROM Proveedores WHERE ruc = ?";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, ruc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ProveedorModelo(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre_proveedor"),
                        rs.getString("ruc"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("contacto"),
                        rs.getString("estado")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener proveedor: " + e.getMessage());
        }
        return null;
    }

    // Registrar nuevo producto
    public boolean registrarNuevoProducto(String nombre, String tipo, BigDecimal precio, int idProveedor) {
        // Validaciones
        if (nombre == null || nombre.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()) {
            System.out.println("❌ Nombre y tipo del producto son requeridos");
            return false;
        }

        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("❌ Precio debe ser mayor a 0");
            return false;
        }

        String query = "INSERT INTO Productos (Nombre_Producto, Tipo_Producto, Precio, ID_Proveedor, Fecha_Ingreso) " +
                      "VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, nombre.trim());
            ps.setString(2, tipo.trim());
            ps.setBigDecimal(3, precio);
            ps.setInt(4, idProveedor);
            
            ps.executeUpdate();
            System.out.println("✅ Producto registrado correctamente: " + nombre);
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    // Verificar si existe un producto con el mismo nombre
    public boolean productoExiste(String nombre) {
        String query = "SELECT ID_Producto FROM Productos WHERE LOWER(Nombre_Producto) = LOWER(?)";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("❌ Error al verificar producto: " + e.getMessage());
            return false;
        }
    }

    // Obtener último ID de proveedor insertado
    public int obtenerUltimoIdProveedor() {
        String query = "SELECT MAX(id_proveedor) as id FROM Proveedores";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener último ID: " + e.getMessage());
        }
        return -1;
    }

    // Obtener último ID de producto insertado
    public int obtenerUltimoIdProducto() {
        String query = "SELECT MAX(ID_Producto) as id FROM Productos";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener último ID: " + e.getMessage());
        }
        return -1;
    }
}
