/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.ModeloCliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteControlador {
    private Conexion conexion = new Conexion();

    // Obtener todos los clientes
    public List<ModeloCliente> obtenerClientes() {
        List<ModeloCliente> lista = new ArrayList<>();
        String sql = "SELECT ID_Cliente, Nombre, Edad, Compras_Realizadas FROM Clientes ORDER BY ID_Cliente";

        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ModeloCliente c = new ModeloCliente(
                    rs.getInt("ID_Cliente"),
                    rs.getString("Nombre"),
                    rs.getInt("Edad"),
                    rs.getInt("Compras_Realizadas")
                );
                lista.add(c);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener clientes: " + e.getMessage());
        }
        return lista;
    }

    // Insertar un nuevo cliente
    public boolean insertarCliente(ModeloCliente cliente) {
        String sql = "INSERT INTO Clientes (Nombre, Edad, Compras_Realizadas) VALUES (?, ?, ?)";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setInt(2, cliente.getEdad());
            ps.setInt(3, cliente.getComprasRealizadas());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // Actualizar nombre del cliente
    public boolean actualizarCliente(int idCliente, String nuevoNombre) {
        String sql = "UPDATE Clientes SET Nombre = ? WHERE ID_Cliente = ?";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, idCliente);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Borrar cliente
    public boolean borrarCliente(int idCliente) {
        String sql = "DELETE FROM Clientes WHERE ID_Cliente = ?";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("❌ Error al borrar cliente: " + e.getMessage());
            return false;
        }
    }
}

