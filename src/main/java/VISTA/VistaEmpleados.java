/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.ControladorEmpleado;
import MODELO.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaEmpleados extends JFrame {

    private JTable tablaEmpleados;
    private DefaultTableModel modelo;
    private ControladorEmpleado controlador;

    private JTextField campoId, campoNombre, campoEdad, campoCorreo, campoRol, campoHorario;

    public VistaEmpleados(ControladorEmpleado controlador) {
        this.controlador = controlador;
        controlador.setVista(this);

        setTitle("Gestión de Empleados");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{
                "ID", "Nombre", "Edad", "Correo", "Rol", "Horario"
        }, 0);

        tablaEmpleados = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 80, 10, 80));

        panelForm.add(new JLabel("ID:"));
        campoId = new JTextField();
        panelForm.add(campoId);

        panelForm.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelForm.add(campoNombre);

        panelForm.add(new JLabel("Edad:"));
        campoEdad = new JTextField();
        panelForm.add(campoEdad);

        panelForm.add(new JLabel("Correo:"));
        campoCorreo = new JTextField();
        panelForm.add(campoCorreo);

        panelForm.add(new JLabel("Rol:"));
        campoRol = new JTextField();
        panelForm.add(campoRol);

        panelForm.add(new JLabel("Horario:"));
        campoHorario = new JTextField();
        panelForm.add(campoHorario);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        btnAgregar.addActionListener(e -> {
            try {
                Empleado emp = new Empleado(
                        0,
                        campoNombre.getText(),
                        Integer.parseInt(campoEdad.getText()),
                        campoCorreo.getText(),
                        campoRol.getText(),
                        campoHorario.getText()
                );
                controlador.agregarEmpleado(emp);
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en datos.");
            }
        });

        btnEditar.addActionListener(e -> {
            try {
                Empleado emp = new Empleado(
                        Integer.parseInt(campoId.getText()),
                        campoNombre.getText(),
                        Integer.parseInt(campoEdad.getText()),
                        campoCorreo.getText(),
                        campoRol.getText(),
                        campoHorario.getText()
                );
                controlador.editarEmpleado(emp);
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en datos.");
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText());
                controlador.eliminarEmpleado(id);
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        controlador.cargarEmpleados();
    }

    public void cargarEmpleados(List<Empleado> empleados) {
        modelo.setRowCount(0);
        for (Empleado e : empleados) {
            modelo.addRow(new Object[]{
                    e.getId(), e.getNombre(), e.getEdad(),
                    e.getCorreo(), e.getRol(), e.getHorario()
            });
        }
    }

    public void limpiarCampos() {
        campoId.setText("");
        campoNombre.setText("");
        campoEdad.setText("");
        campoCorreo.setText("");
        campoRol.setText("");
        campoHorario.setText("");
    }
}


     