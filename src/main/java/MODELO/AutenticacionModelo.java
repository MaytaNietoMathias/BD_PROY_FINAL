/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;


import CONTROLADOR.Conexion;
import java.sql.*;

public class AutenticacionModelo {

    private Conexion conexion;

    public AutenticacionModelo() {
        conexion = new Conexion(); // Asumiendo que ya tienes esta clase con el método de conexión
    }

    // Método para autenticar al usuario con base de datos
    public boolean autenticarUsuario(String usuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            // Si existe el usuario, retorna true
            return rs.next();

        } catch (SQLException e) {
            System.err.println("Error al autenticar el usuario: " + e.getMessage());
            return false;
        }
    }
}


