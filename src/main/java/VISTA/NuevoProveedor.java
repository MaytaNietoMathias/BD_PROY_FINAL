/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.CompraControlador;
import javax.swing.*;
import java.awt.*;

public class NuevoProveedor extends JDialog {
    private JTextField campoNombre;
    private JTextField campoRuc;
    private JTextField campoDireccion;
    private JTextField campoTelefono;
    private JTextField campoEmail;
    private JTextField campoContacto;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private CompraControlador compraControlador;
    private boolean guardado = false;

    public NuevoProveedor(JFrame parent, CompraControlador compraControlador) {
        super(parent, "Registrar Nuevo Proveedor", true);
        this.compraControlador = compraControlador;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("REGISTRO DE PROVEEDOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // Nombre Proveedor
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Nombre Proveedor:*"), gbc);
        gbc.gridx = 1;
        campoNombre = new JTextField(20);
        panel.add(campoNombre, gbc);

        // RUC
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("RUC (11 dígitos):*"), gbc);
        gbc.gridx = 1;
        campoRuc = new JTextField(20);
        panel.add(campoRuc, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        campoDireccion = new JTextField(20);
        panel.add(campoDireccion, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        campoTelefono = new JTextField(20);
        panel.add(campoTelefono, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        campoEmail = new JTextField(20);
        panel.add(campoEmail, gbc);

        // Contacto
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Persona de Contacto:"), gbc);
        gbc.gridx = 1;
        campoContacto = new JTextField(20);
        panel.add(campoContacto, gbc);

        // Nota de campos requeridos
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        JLabel lblNota = new JLabel("* Campos requeridos");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNota.setForeground(new Color(150, 150, 150));
        panel.add(lblNota, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 1;
        btnGuardar = new JButton("Guardar Proveedor");
        btnGuardar.addActionListener(e -> guardarProveedor());
        panel.add(btnGuardar, gbc);

        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar, gbc);

        add(panel);
    }

    private void guardarProveedor() {
        String nombre = campoNombre.getText().trim();
        String ruc = campoRuc.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String email = campoEmail.getText().trim();
        String contacto = campoContacto.getText().trim();

        // Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa el nombre del proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ruc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa el RUC del proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ruc.matches("^[0-9]{11}$")) {
            JOptionPane.showMessageDialog(this, "El RUC debe tener exactamente 11 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Registrar proveedor
        if (compraControlador.registrarNuevoProveedor(nombre, ruc, direccion, telefono, email, contacto)) {
            guardado = true;
            JOptionPane.showMessageDialog(this, "✅ Proveedor registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar el proveedor. Verifica que el RUC no exista.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean fueGuardado() {
        return guardado;
    }
}
