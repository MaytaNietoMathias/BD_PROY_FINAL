package MODELO;

import CONTROLADOR.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // 🔹 Obtener categorías únicas desde la tabla Productos
    public List<String> obtenerCategoriasUnicas() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT DISTINCT Tipo_Producto FROM Productos";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                categorias.add(rs.getString("Tipo_Producto"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorias;
    }

    // 🔹 Obtener productos por categoría
    public List<ProductoModelo> obtenerProductosPorCategoria(String categoria) {
        List<ProductoModelo> productos = new ArrayList<>();
        String sql = "SELECT ID_Producto, Nombre_Producto, Precio, Tipo_Producto " +
                     "FROM Productos WHERE Tipo_Producto = '" + categoria + "'";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ProductoModelo p = new ProductoModelo();
                p.setId(rs.getInt("ID_Producto"));
                p.setNombre(rs.getString("Nombre_Producto"));
                p.setPrecio(rs.getBigDecimal("Precio"));
                p.setTipo(rs.getString("Tipo_Producto"));
                productos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    // 🔹 Obtener precio de un producto por ID
    public java.math.BigDecimal obtenerPrecioPorId(int idProducto) {
        java.math.BigDecimal precio = null;
        String sql = "SELECT Precio FROM Productos WHERE ID_Producto = " + idProducto;

        Conexion conexion = new Conexion();
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                precio = rs.getBigDecimal("Precio");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return precio;
    }
}