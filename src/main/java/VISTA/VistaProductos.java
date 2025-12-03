/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import MODELO.ProductoModelo;
import CONTROLADOR.ProductoControlador;
import CONTROLADOR.ControladorLogin;
import MODELO.UsuarioBD;

public class VistaProductos extends JFrame {
    private JComboBox<String> comboCategorias;
    private JTable tablaProductos;
    private DefaultTableModel modelo;
    private JFrame menuPrincipal;
    private ProductoControlador productoControlador = new ProductoControlador();

    private JButton btnAnadir, btnEditar, btnBorrar, btnCerrar;

    public VistaProductos(JFrame menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        setTitle("Productos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Categorías
        String[] categorias = {
            "Galletas", "Golosinas", "Chocolates", "Gaseosas", "Snacks","Limpieza","Abarrotes", "N/A"
        };
        comboCategorias = new JComboBox<>(categorias);

        // Tabla
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tablaProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Botones
        btnAnadir = new JButton("Añadir producto");
        btnEditar = new JButton("Editar producto");
        btnBorrar = new JButton("Borrar producto");
        btnCerrar = new JButton("Cerrar");

        btnCerrar.addActionListener(e -> {
            this.dispose();
            if (menuPrincipal != null) menuPrincipal.setVisible(true);
        });

        btnAnadir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de 'Añadir producto' aún no implementada.");
        });
        btnBorrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de 'Borrar producto' aún no implementada.");
        });
        btnEditar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de 'Editar producto' aún no implementada.");
        });

        // Panel superior (combo)
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Selecciona categoría:"));
        panelSuperior.add(comboCategorias);

        // Panel inferior (botones)
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnAnadir);
        panelInferior.add(btnEditar);
        panelInferior.add(btnBorrar);
        panelInferior.add(btnCerrar);

        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Ocultar botones para usuarios que no sean ADMIN
        UsuarioBD usuario = ControladorLogin.getUsuarioActual();
        if (usuario != null && !"ADMIN".equalsIgnoreCase(usuario.getRol())) {
            btnAnadir.setVisible(false);
            btnEditar.setVisible(false);
            btnBorrar.setVisible(false);
        }

        // Acción combo categorías
        comboCategorias.addActionListener(e -> {
            if (comboCategorias.getSelectedIndex() != -1) {
                cargarProductos((String) comboCategorias.getSelectedItem());
            }
        });

        // Cargar inicialmente
        if (comboCategorias.getItemCount() > 0) {
            comboCategorias.setSelectedIndex(0);
            cargarProductos((String) comboCategorias.getSelectedItem());
        }

        // Manejo ventana cerrada
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (menuPrincipal != null) menuPrincipal.setVisible(true);
            }
        });
    }

    private void cargarProductos(String categoria) {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Tipo");

        for (ProductoModelo p : productoControlador.obtenerTodosLosProductos()) {
            if ("N/A".equals(categoria) || p.getTipo().equalsIgnoreCase(categoria)) {
                modelo.addRow(new Object[] {
                    p.getId(),
                    p.getNombre(),
                    "S/. " + p.getPrecio(),
                    p.getTipo()
                });
            }
        }
    }
}
