/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import CONTROLADOR.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public List<Empleado> obtenerEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT ID_Trabajador, Nombre_Trabajador, Edad, Horario, Correo, Rol FROM Trabajadores";

        try {
            Connection con = new Conexion().establecerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("ID_Trabajador"),
                        rs.getString("Nombre_Trabajador"),
                        rs.getInt("Edad"),
                        rs.getString("Correo"),
                        rs.getString("Rol"),
                        rs.getString("Horario")
                );
                lista.add(emp);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("❌ Error en obtenerEmpleados: " + e.getMessage());
        }
        return lista;
    }

    public void agregarEmpleado(Empleado e) {
        String sql = "INSERT INTO Trabajadores (Nombre_Trabajador, Edad, Horario, Correo, Rol) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = new Conexion().establecerConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, e.getNombre());
            ps.setInt(2, e.getEdad());
            ps.setString(3, e.getHorario());
            ps.setString(4, e.getCorreo());
            ps.setString(5, e.getRol());

            ps.executeUpdate();
            ps.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("❌ Error en agregarEmpleado: " + ex.getMessage());
        }
    }

    public void editarEmpleado(Empleado e) throws SQLException {
        String sql = "UPDATE Trabajadores SET Nombre_Trabajador=?, Edad=?, Horario=?, Correo=?, Rol=? WHERE ID_Trabajador=?";

        Connection con = new Conexion().establecerConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, e.getNombre());
        ps.setInt(2, e.getEdad());
        ps.setString(3, e.getHorario());
        ps.setString(4, e.getCorreo());
        ps.setString(5, e.getRol());
        ps.setInt(6, e.getId());

        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public void eliminarEmpleado(int id) throws SQLException {
        String sql = "DELETE FROM Trabajadores WHERE ID_Trabajador=?";

        Connection con = new Conexion().establecerConexion();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();

        ps.close();
        con.close();
    }
}



