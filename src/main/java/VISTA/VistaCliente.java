/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.ClienteControlador;
import MODELO.ModeloCliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VistaCliente extends JFrame {
    private JTable tablaClientes;
    private DefaultTableModel modelo;
    private ClienteControlador controlador = new ClienteControlador();
    private JFrame menuPrincipal;

    public VistaCliente(JFrame menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        setTitle("Gestión de Clientes");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Clientes registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        modelo = new DefaultTableModel(new Object[]{"ID Cliente", "Nombre", "Edad", "Compras Realizadas"}, 0);
        tablaClientes = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);

        JButton btnEditar = new JButton("Editar");
        JButton btnBorrar = new JButton("Borrar");
        JButton btnCerrar = new JButton("Cerrar");

        btnEditar.addActionListener(e -> editarCliente());
        btnBorrar.addActionListener(e -> borrarCliente());
        btnCerrar.addActionListener(e -> {
            this.dispose();
            if (menuPrincipal != null) menuPrincipal.setVisible(true);
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnEditar);
        panelInferior.add(btnBorrar);
        panelInferior.add(btnCerrar);

        setLayout(new BorderLayout(10, 10));
        add(lblTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cargarClientes();
    }

    private void cargarClientes() {
        modelo.setRowCount(0);
        List<ModeloCliente> clientes = controlador.obtenerClientes();
        for (ModeloCliente c : clientes) {
            modelo.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getEdad(), c.getComprasRealizadas()});
        }
    }

    private void editarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para editar.");
            return;
        }
        int idCliente = (int) modelo.getValueAt(fila, 0);
        String nombreActual = modelo.getValueAt(fila, 1).toString();
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", nombreActual);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            if (controlador.actualizarCliente(idCliente, nuevoNombre.trim())) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado.");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar cliente.");
            }
        }
    }

    private void borrarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para borrar.");
            return;
        }
        int idCliente = (int) modelo.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controlador.borrarCliente(idCliente)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al borrar cliente.");
            }
        }
    }
}

