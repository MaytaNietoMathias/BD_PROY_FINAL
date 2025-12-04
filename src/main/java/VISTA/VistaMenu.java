/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.ControladorEmpleado;
import CONTROLADOR.ControladorLogin;
import CONTROLADOR.ControladorPermisosUsuarios;
import javax.swing.*;
import java.awt.*;

public class VistaMenu extends JFrame {

    public VistaMenu() {
        setTitle("Sistema MercaTop");
        setSize(700, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(224, 242, 241);
                Color color2 = new Color(144, 202, 249);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel lblTitulo = new JLabel("🛒 Sistema MercaTop");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(21, 101, 192));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);

        //inicio boton productos
        JButton btnProductos = new JButton("Productos");
        btnProductos.setFont(btnFont);
        btnProductos.setBackground(new Color(187, 222, 251));
        btnProductos.setForeground(new Color(21, 101, 192));
        btnProductos.setFocusPainted(false);
        btnProductos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProductos.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        btnProductos.addActionListener(e -> {
            if (this != null) {
                VistaProductos ventanaProductos = new VistaProductos(this);
                ventanaProductos.setVisible(true);
                this.setVisible(false);
            }
        });
        //fin boton productos

        //inicio boton clientes
        JButton btnClientes = new JButton("Clientes");
        btnClientes.setFont(btnFont);
        btnClientes.setBackground(new Color(225, 245, 254));
        btnClientes.setForeground(new Color(21, 101, 192));
        btnClientes.setFocusPainted(false);
        btnClientes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClientes.setIcon(UIManager.getIcon("FileView.fileIcon"));
        btnClientes.addActionListener(e -> {
            VistaCliente ventanaClientes = new VistaCliente(this);
            ventanaClientes.setVisible(true);
            this.setVisible(false);
        });
        //fin boton clientes

        //inicio boton registrar venta
        JButton btnRegistrarVenta = new JButton("Registrar Venta");
        btnRegistrarVenta.setFont(btnFont);
        btnRegistrarVenta.setBackground(new Color(200, 230, 201));
        btnRegistrarVenta.setForeground(new Color(21, 101, 192));
        btnRegistrarVenta.setFocusPainted(false);
        btnRegistrarVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrarVenta.setIcon(UIManager.getIcon("OptionPane.informationIcon"));

        btnRegistrarVenta.addActionListener(e -> {
            // Obtener el rol del usuario logeado
            String rol = ControladorLogin.getUsuarioActual().getRol();

            if (ControladorPermisosUsuarios.puedeRegistrarVenta(rol)) {
                VistaRegistrarVenta ventanaVenta = new VistaRegistrarVenta(this);
                ventanaVenta.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No tienes permisos para acceder a Registrar Venta.",
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //fin boton registrar venta
        //inicio boton registrar compra
        JButton btnRegistrarCompra = new JButton("Registrar Compra");
        btnRegistrarCompra.setFont(btnFont);
        btnRegistrarCompra.setBackground(new Color(255, 236, 179));
        btnRegistrarCompra.setForeground(new Color(21, 101, 192));
        btnRegistrarCompra.setFocusPainted(false);
        btnRegistrarCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrarCompra.setIcon(UIManager.getIcon("OptionPane.questionIcon"));

        btnRegistrarCompra.addActionListener(e -> {
            // Obtener el rol del usuario actual
            String rol = ControladorLogin.getUsuarioActual().getRol();

            if (ControladorPermisosUsuarios.puedeRegistrarCompra(rol)) {
                VistaRegistrarCompra ventanaCompra = new VistaRegistrarCompra(this);
                ventanaCompra.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No tienes permisos para acceder a Registrar Compra.",
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //fin boton registrar compra
        //inicio boton ultima venta
        JButton btnUltimaVenta = new JButton("Última Boleta de Venta");
        btnUltimaVenta.setFont(btnFont);
        btnUltimaVenta.setBackground(new Color(200, 222, 233));
        btnUltimaVenta.setForeground(new Color(21, 101, 192));
        btnUltimaVenta.setFocusPainted(false);
        btnUltimaVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUltimaVenta.setIcon(UIManager.getIcon("FileView.fileIcon"));
        btnUltimaVenta.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de 'Última Boleta de Venta' aún no implementada.");
        });
        //fin boton ultima venta

        //inicio boton ultima compra (Registro de Usuarios)
        JButton btnUltimaCompra = new JButton("Registro de Usuarios");
        btnUltimaCompra.setFont(btnFont);
        btnUltimaCompra.setBackground(new Color(225, 245, 215));
        btnUltimaCompra.setForeground(new Color(21, 101, 192));
        btnUltimaCompra.setFocusPainted(false);
        btnUltimaCompra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUltimaCompra.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
        btnUltimaCompra.addActionListener(e -> {
            String rol = ControladorLogin.getUsuarioActual().getRol();

            if (ControladorPermisosUsuarios.puedeAccederLogueoUsuarios(rol)) {
                VistaLogueoUsuarios ventanaLogueos = new VistaLogueoUsuarios(this);
                ventanaLogueos.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "No tienes permisos para acceder a Registro de Usuarios.",
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        //fin boton ultima compra

        //inicio boton empleados
        JButton btnEmpleados = new JButton("Empleados");
        btnEmpleados.setFont(btnFont);
        btnEmpleados.setBackground(new Color(255, 249, 196));
        btnEmpleados.setForeground(new Color(21, 101, 192));
        btnEmpleados.setFocusPainted(false);
        btnEmpleados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEmpleados.setIcon(UIManager.getIcon("FileView.computerIcon"));
        btnEmpleados.setPreferredSize(new Dimension(150, 40));

        // RESTAURACIÓN DE LA RESTRICCIÓN DE ACCESO
        btnEmpleados.addActionListener(e -> {
            String rolUsuario = ControladorLogin.getUsuarioActual().getRol();

            if ("Administrador".equalsIgnoreCase(rolUsuario)) {
                ControladorEmpleado controlador = new ControladorEmpleado(null);
                VistaEmpleados vista = new VistaEmpleados(controlador);
                vista.setVisible(true);
                controlador.setVista(vista);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Acceso Denegado. Solo el rol de Administrador puede gestionar empleados.",
                        "Permiso Insuficiente",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        //fin boton empleados
        //inicio boton salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnSalir.setBackground(new Color(255, 205, 210));
        btnSalir.setForeground(new Color(183, 28, 28));
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
        btnSalir.addActionListener(e -> System.exit(0));
        btnSalir.setPreferredSize(new Dimension(0, 50));
        //fin boton salir

        // Distribución en la cuadrícula
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(btnProductos, gbc);
        gbc.gridx = 1;
        panel.add(btnClientes, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(btnRegistrarVenta, gbc);
        gbc.gridx = 1;
        panel.add(btnRegistrarCompra, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(btnUltimaVenta, gbc);
        gbc.gridx = 1;
        panel.add(btnUltimaCompra, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnEmpleados, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnSalir, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
