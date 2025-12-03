/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VistaRegistrarVenta extends JFrame {
    private JTable tablaVentas;
    private DefaultTableModel modelo;
    private JFrame menuPrincipal;
    private JTextField campoCliente;
    private JComboBox<String> campoIdTrabajador;
    private JComboBox<String> campoCategoria;
    private JComboBox<String> campoProducto;
    private JTextField campoCantidad;
    private JTextField campoPrecio;
    private JTextField campoMonto;
    private JTextField campoFecha;

    public VistaRegistrarVenta(JFrame menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        setTitle("Registrar Venta");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Ventas Registradas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaVentas);

        // Panel de registro
        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registrar nueva venta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cliente
        gbc.gridx = 0; gbc.gridy = 0;
        panelRegistro.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        campoCliente = new JTextField(15);
        panelRegistro.add(campoCliente, gbc);

        // ID Trabajador
        gbc.gridx = 0; gbc.gridy = 1;
        panelRegistro.add(new JLabel("ID Trabajador:"), gbc);
        gbc.gridx = 1;
        campoIdTrabajador = new JComboBox<>();
        panelRegistro.add(campoIdTrabajador, gbc);

        // Categoría
        gbc.gridx = 0; gbc.gridy = 2;
        panelRegistro.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        campoCategoria = new JComboBox<>(new String[]{"Galletas", "Golosinas", "Chocolates", "Gaseosas", "Snacks"});
        panelRegistro.add(campoCategoria, gbc);

        // Producto
        gbc.gridx = 0; gbc.gridy = 3;
        panelRegistro.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        campoProducto = new JComboBox<>();
        panelRegistro.add(campoProducto, gbc);

        // Cantidad
        gbc.gridx = 0; gbc.gridy = 4;
        panelRegistro.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        campoCantidad = new JTextField(5);
        panelRegistro.add(campoCantidad, gbc);

        // Precio (solo lectura)
        gbc.gridx = 0; gbc.gridy = 5;
        panelRegistro.add(new JLabel("Precio (S/):"), gbc);
        gbc.gridx = 1;
        campoPrecio = new JTextField(10);
        campoPrecio.setEditable(false);
        panelRegistro.add(campoPrecio, gbc);

        // Monto total (solo lectura)
        gbc.gridx = 0; gbc.gridy = 6;
        panelRegistro.add(new JLabel("Monto Total (S/):"), gbc);
        gbc.gridx = 1;
        campoMonto = new JTextField(10);
        campoMonto.setEditable(false);
        panelRegistro.add(campoMonto, gbc);

        // Fecha (solo lectura, fecha actual)
        gbc.gridx = 0; gbc.gridy = 7;
        panelRegistro.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        campoFecha = new JTextField(10);
        campoFecha.setEditable(false);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        campoFecha.setText(formatoFecha.format(new Date()));
        panelRegistro.add(campoFecha, gbc);

        // Botón registrar
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar Venta");
        btnRegistrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de 'Registrar Venta' aún no implementada.");
        });
        panelRegistro.add(btnRegistrar, gbc);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> {
            this.dispose();
            if (menuPrincipal != null) menuPrincipal.setVisible(true);
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCerrar);

        setLayout(new BorderLayout(15, 15));
        add(lblTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelRegistro, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 🔹 Cargar tabla ventas
        cargarVentas();
    }

    private void cargarVentas() {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        modelo.addColumn("ID Venta");
        modelo.addColumn("Cliente");
        modelo.addColumn("ID Trabajador");
        modelo.addColumn("Producto");
        modelo.addColumn("Categoría");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Monto");
        modelo.addColumn("Fecha");

        Conexion conexion = new Conexion();
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT ID_Venta, Cliente, ID_Trabajador, Producto, Categoria, Cantidad, Precio, Monto, Fecha FROM Ventas"
             )) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("ID_Venta"),
                        rs.getString("Cliente"),
                        rs.getInt("ID_Trabajador"),
                        rs.getString("Producto"),
                        rs.getString("Categoria"),
                        rs.getInt("Cantidad"),
                        rs.getBigDecimal("Precio"),
                        rs.getBigDecimal("Monto"),
                        rs.getDate("Fecha")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ventas:\n" + e.getMessage());
        }
    }
}
