/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import java.util.List;
import MODELO.LogueoUsuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author diego
 */
public class LogueoUsuariosControlador {
    private Conexion conexion = new Conexion();
    
    public List<LogueoUsuarios> listarLogueos(){
        List<LogueoUsuarios> lista = new ArrayList<>();

        String sql = "SELECT * FROM LogueosUsuarios ORDER BY Fecha DESC";

        try (Connection cn = conexion.establecerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LogueoUsuarios log = new LogueoUsuarios(
                        rs.getInt("ID_Log"),
                        rs.getInt("ID_Usuario"),
                        rs.getString("Nombre"),
                        rs.getString("Rol"),
                        rs.getDate("Fecha")
                );
                lista.add(log);
            }
        } catch (Exception e) {
            System.out.println("Error al listar logueos: " + e.getMessage());
        }
        return lista;
    }
}

