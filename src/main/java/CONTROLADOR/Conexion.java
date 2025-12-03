/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Conexion {

    // Instancia única de la clase Conexion (Singleton)
    private static Conexion instance;
    private Connection conexion;

    // Configuración de la base de datos
    private final String usuario = "postgres";
    private final String contraseña = "admin"; // Ajusta según tu configuración real
    private final String bd = "MercaTop";
    private final String ip = "localhost";
    private final String puerto = "5432";

    // Cadena de conexión JDBC
    private final String cadena = "jdbc:postgresql://" + ip + ":" + puerto + "/" + bd;

    // Constructor privado para evitar instanciación externa
    public Conexion() {}

    // Método para obtener la instancia única de Conexion (Singleton)
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    // Método para establecer la conexión
    public Connection establecerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Establecer una nueva conexión si no hay una activa
                Class.forName("org.postgresql.Driver");
                conexion = DriverManager.getConnection(cadena, usuario, contraseña);
                System.out.println("✅ Conexión exitosa a PostgreSQL.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error al conectar con PostgreSQL:\n" + e.getMessage());
        }
        return conexion;
    }

    // Método para autenticar usuario en la tabla UsuarioBD
    public boolean autenticarUsuario(String nombreUsuario, String contrasena) {
        String query = "SELECT 1 FROM UsuarioBD WHERE Nombre_Usuario = ? AND Contraseña = ?";
        try (Connection con = establecerConexion();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si existe un registro, autenticación exitosa
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error al verificar usuario:\n" + e.getMessage());
        }
        return false;
    }

    // Método para probar conexión sin lógica adicional
    public boolean probarConexion() {
        try (Connection con = establecerConexion()) {
            return con != null && !con.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    // Método para cerrar la conexión (si es necesario)
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✅ Conexión cerrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Getters (opcionales si necesitas acceder a las credenciales)
    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}

