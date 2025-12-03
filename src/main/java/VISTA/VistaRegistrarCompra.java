/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.Conexion;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class VistaRegistrarCompra extends JFrame{
    private JTable tablaCompras;
    private DefaultTableModel modelo;
    private JFrame menuPrincipal;
    private JComboBox<String> campoProveedor;
    private JComboBox<String> campoProducto;
    private JTextField campoCantidad;
    private JTextField campoMonto;
    private JSpinner campoFecha;
    
    public VistaRegistrarCompra(JFrame menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        setTitle("Registrar Compra");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JLabel lblTitulo = new JLabel("Compras Registradas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCompras = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaCompras);
        
        // Panel de registro (campos de entrada)
        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBorder(
            BorderFactory.createTitledBorder("Registrar nueva compra")
        );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelRegistro.add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1;
        campoProveedor = new JComboBox<>();
        campoProveedor.setPreferredSize(new Dimension(150, 25));
        panelRegistro.add(campoProveedor, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelRegistro.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        campoProducto = new JComboBox<>();
        campoProducto.setPreferredSize(new Dimension(150, 25));
        panelRegistro.add(campoProducto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelRegistro.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        campoCantidad = new JTextField(10);
        panelRegistro.add(campoCantidad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panelRegistro.add(new JLabel("Monto Total (S/):"), gbc);
        gbc.gridx = 1;
        campoMonto = new JTextField(10);
        panelRegistro.add(campoMonto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        panelRegistro.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        campoFecha = new JSpinner(new SpinnerDateModel());
        campoFecha.setEditor(new JSpinner.DateEditor(campoFecha, "yyyy-MM-dd"));
        campoFecha.setPreferredSize(new Dimension(120, 25));
        panelRegistro.add(campoFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar Compra");
        // 🔹 sin acción por ahora
        btnRegistrar.addActionListener(e ->
                JOptionPane.showMessageDialog(this,"La funcionalidad 'Registrar Compra' aún no implementada."));
        
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
        
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        cargarCompras();
    }
    
    private void cargarCompras() {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        modelo.addColumn("ID Compra");
        modelo.addColumn("ID Proveedor");
        modelo.addColumn("ID Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Monto");
        modelo.addColumn("Fecha");

        Conexion conexion = new Conexion();
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT ID_Compra, ID_Proveedor, ID_Producto, Cantidad, Monto, Fecha FROM Compras")) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_Compra"),
                    rs.getInt("ID_Proveedor"),
                    rs.getInt("ID_Producto"),
                    rs.getInt("Cantidad"),
                    rs.getBigDecimal("Monto"),
                    rs.getDate("Fecha")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar compras:\n" + e.getMessage());
        }
    }
}
