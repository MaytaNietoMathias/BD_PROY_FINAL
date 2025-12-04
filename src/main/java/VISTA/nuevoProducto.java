/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.CompraControlador;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class nuevoProducto extends JDialog {
    private JTextField campoNombre;
    private JTextField campoTipo;
    private JTextField campoPrecio;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private CompraControlador compraControlador;
    private int idProveedor;
    private boolean guardado = false;

    public nuevoProducto(JFrame parent, CompraControlador compraControlador, int idProveedor) {
        super(parent, "Registrar Nuevo Producto", true);
        this.compraControlador = compraControlador;
        this.idProveedor = idProveedor;
        
        setSize(450, 300);
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
        JLabel lblTitulo = new JLabel("REGISTRO DE PRODUCTO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // Nombre Producto
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Nombre Producto:*"), gbc);
        gbc.gridx = 1;
        campoNombre = new JTextField(20);
        panel.add(campoNombre, gbc);

        // Tipo
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Tipo/Categoría:*"), gbc);
        gbc.gridx = 1;
        campoTipo = new JTextField(20);
        panel.add(campoTipo, gbc);

        // Precio Unitario
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Precio Unitario (S/):*"), gbc);
        gbc.gridx = 1;
        campoPrecio = new JTextField(20);
        panel.add(campoPrecio, gbc);

        // Nota de campos requeridos
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JLabel lblNota = new JLabel("* Campos requeridos");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNota.setForeground(new Color(150, 150, 150));
        panel.add(lblNota, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        btnGuardar = new JButton("Guardar Producto");
        btnGuardar.addActionListener(e -> guardarProducto());
        panel.add(btnGuardar, gbc);

        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar, gbc);

        add(panel);
    }

    private void guardarProducto() {
        String nombre = campoNombre.getText().trim();
        String tipo = campoTipo.getText().trim();
        String precioStr = campoPrecio.getText().trim();

        // Validaciones
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa el nombre del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa el tipo/categoría del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa el precio del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BigDecimal precio = new BigDecimal(precioStr);
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Registrar producto
            if (compraControlador.registrarNuevoProducto(nombre, tipo, precio, idProveedor)) {
                guardado = true;
                JOptionPane.showMessageDialog(this, "✅ Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al registrar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un precio válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean fueGuardado() {
        return guardado;
    }
}
