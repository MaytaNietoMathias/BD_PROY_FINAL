/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONTROLADOR.Conexion;
import MODELO.ProductoModelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoControlador {

    // NO hay getInstance(), así que instanciamos normal
    private Conexion conexion = new Conexion();

    public List<ProductoModelo> obtenerTodosLosProductos() {
        List<ProductoModelo> lista = new ArrayList<>();

        String query = "SELECT ID_Producto, Nombre_Producto, Precio, Tipo_Producto FROM Productos";

        try (Connection con = conexion.establecerConexion();  // ✔ ESTE SÍ EXISTE
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoModelo p = new ProductoModelo();
                p.setId(rs.getInt("ID_Producto"));
                p.setNombre(rs.getString("Nombre_Producto"));
                p.setPrecio(rs.getBigDecimal("Precio"));
                p.setTipo(rs.getString("Tipo_Producto"));
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("❌ Error al obtener productos: " + e.getMessage());
        }

        return lista;
    }
}





