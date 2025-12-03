/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import VISTA.VistaEmpleados;
import MODELO.Empleado;
import MODELO.EmpleadoDAO;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.List;

public class ControladorEmpleado {
    private VistaEmpleados vista;
    private EmpleadoDAO empleadoDAO;

    public ControladorEmpleado(VistaEmpleados vista) {
        this.vista = vista;
        this.empleadoDAO = new EmpleadoDAO();
    }

    public void setVista(VistaEmpleados vista) {
        this.vista = vista;
    }

    public void cargarEmpleados() {
        List<Empleado> empleados = obtenerEmpleados();
        vista.cargarEmpleados(empleados);
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoDAO.obtenerEmpleados();
    }

    public void agregarEmpleado(Empleado empleado) {
        empleadoDAO.agregarEmpleado(empleado);
        JOptionPane.showMessageDialog(vista, "Empleado agregado correctamente.");
        cargarEmpleados();
    }

    public void editarEmpleado(Empleado empleado) {
        try {
            empleadoDAO.editarEmpleado(empleado);
            JOptionPane.showMessageDialog(vista, "Empleado editado correctamente.");
            cargarEmpleados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al editar: " + e.getMessage());
        }
    }

    public void eliminarEmpleado(int idEmpleado) {
        try {
            empleadoDAO.eliminarEmpleado(idEmpleado);
            JOptionPane.showMessageDialog(vista, "Empleado eliminado correctamente.");
            cargarEmpleados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + e.getMessage());
        }
    }
}



