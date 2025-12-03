/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Timestamp;

public class ModeloConexion {

    private Connection conn;

    public ModeloConexion() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/tuBD",
                    "postgres",
                    "123"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Autenticar usuario y registrar login
    public UsuarioBD autenticarUsuario(String usuario, String contrasena) {
        // Seleccionamos el usuario y su rol desde UsuarioBD y Trabajadores
        String sql = "SELECT u.ID_Usuario, u.Nombre_Usuario, u.Contraseña, t.Rol, t.Nombre_Trabajador " +
                     "FROM UsuarioBD u " +
                     "JOIN Trabajadores t ON u.Nombre_Usuario = t.Nombre_Trabajador " +
                     "WHERE u.Nombre_Usuario = ? AND u.Contraseña = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UsuarioBD u = new UsuarioBD();
                u.setIdUsuario(rs.getInt("ID_Usuario"));
                u.setNombreUsuario(rs.getString("Nombre_Usuario"));
                u.setContrasena(rs.getString("Contraseña"));
                u.setRol(rs.getString("Rol"));

                // Registrar inicio de sesión
                registrarLogin(u.getIdUsuario(), rs.getString("Nombre_Trabajador"), rs.getString("Rol"));

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Guardar login en LogueosUsuarios
    private void registrarLogin(int idUsuario, String nombre, String rol) {
        String sql = "INSERT INTO LogueosUsuarios (ID_Usuario, Nombre, Rol, Fecha) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, nombre);
            ps.setString(3, rol);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
