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

    // Se mantienen como JTextField
    private JTextField campoNombre, campoEdad, campoCorreo;

    // RESTAURACIÓN: Los campos Rol y Horario ahora son JComboBox
    private JComboBox<String> comboRol;
    private JComboBox<String> comboHorario;

    private static final String DOMINIO_CORREO = "@mercatop.com";

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

        // RESTAURACIÓN: Listener para cargar datos en los campos/combos
        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEmpleados.getSelectedRow() != -1) {
                int filaSeleccionada = tablaEmpleados.getSelectedRow();
                try {
                    campoNombre.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
                    campoEdad.setText(modelo.getValueAt(filaSeleccionada, 2).toString());

                    // Carga el valor en el JComboBox
                    comboRol.setSelectedItem(modelo.getValueAt(filaSeleccionada, 4).toString());
                    comboHorario.setSelectedItem(modelo.getValueAt(filaSeleccionada, 5).toString());

                    String correoCompleto = modelo.getValueAt(filaSeleccionada, 3).toString();
                    if (correoCompleto.endsWith(DOMINIO_CORREO)) {
                        String usuario = correoCompleto.substring(0, correoCompleto.length() - DOMINIO_CORREO.length());
                        campoCorreo.setText(usuario);
                    } else {
                        campoCorreo.setText(correoCompleto);
                    }

                } catch (Exception ex) {
                }
            }
        });

        // RESTAURACIÓN: Panel Formulario con 5 filas (se eliminó campo ID)
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 80, 10, 80));

        // Se ELIMINÓ la sección para campoId
        // panelForm.add(new JLabel("ID:"));
        // campoId = new JTextField();
        // panelForm.add(campoId);
        panelForm.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        panelForm.add(campoNombre);

        panelForm.add(new JLabel("Edad:"));
        campoEdad = new JTextField();
        panelForm.add(campoEdad);

        // RESTAURACIÓN: Automatización del Correo
        JPanel panelCorreo = new JPanel(new BorderLayout(5, 0));
        campoCorreo = new JTextField();
        panelCorreo.add(campoCorreo, BorderLayout.CENTER);
        panelCorreo.add(new JLabel(DOMINIO_CORREO), BorderLayout.EAST);

        panelForm.add(new JLabel("Correo:"));
        panelForm.add(panelCorreo);

        // RESTAURACIÓN: JComboBox para Rol
        panelForm.add(new JLabel("Rol:"));
        String[] roles = {"Ventas", "Compras"}; // Opciones de Rol
        comboRol = new JComboBox<>(roles);
        panelForm.add(comboRol);

        // RESTAURACIÓN: JComboBox para Horario
        panelForm.add(new JLabel("Horario:"));
        String[] horarios = {"08:00-16:00", "16:00-22:00"}; // Opciones de Horario
        comboHorario = new JComboBox<>(horarios);
        panelForm.add(comboHorario);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        // RESTAURACIÓN: Se cambió btnLimpiar a btnNuevo, y se ELIMINÓ btnEliminar
        JButton btnNuevo = new JButton("Nuevo");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnNuevo);
        // Se ELIMINÓ el botón btnEliminar

        btnAgregar.addActionListener(e -> {
            try {
                String correoFinal = campoCorreo.getText().trim() + DOMINIO_CORREO;

                Empleado emp = new Empleado(
                        0, // ID 0 porque es automático
                        campoNombre.getText(),
                        Integer.parseInt(campoEdad.getText()),
                        correoFinal,
                        // Obtener valor de JComboBox
                        comboRol.getSelectedItem().toString(),
                        comboHorario.getSelectedItem().toString()
                );
                controlador.agregarEmpleado(emp);
                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: La Edad debe ser un número entero.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar empleado.");
            }
        });

        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaEmpleados.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un empleado de la tabla para editar.");
                return;
            }
            try {
                // RESTAURACIÓN: Obtiene el ID directamente de la tabla (Columna 0)
                int id = (int) modelo.getValueAt(filaSeleccionada, 0);

                String correoFinal = campoCorreo.getText().trim() + DOMINIO_CORREO;

                Empleado emp = new Empleado(
                        id,
                        campoNombre.getText(),
                        Integer.parseInt(campoEdad.getText()),
                        correoFinal,
                        // Obtener valor de JComboBox
                        comboRol.getSelectedItem().toString(),
                        comboHorario.getSelectedItem().toString()
                );
                controlador.editarEmpleado(emp);
                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: La Edad debe ser un número entero.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al editar empleado.");
            }
        });

        // Se ELIMINÓ el ActionListener para btnEliminar
        // RESTAURACIÓN: Botón Nuevo (antes Limpiar)
        btnNuevo.addActionListener(e -> limpiarCampos());

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

    // RESTAURACIÓN: Limpiar campos
    public void limpiarCampos() {
        // campoId.setText(""); // Ya no existe
        campoNombre.setText("");
        campoEdad.setText("");
        campoCorreo.setText("");

        // Limpiar campos de JComboBox, seleccionando el primer elemento (índice 0)
        comboRol.setSelectedIndex(0);
        comboHorario.setSelectedIndex(0);

        tablaEmpleados.clearSelection();
    }
}
