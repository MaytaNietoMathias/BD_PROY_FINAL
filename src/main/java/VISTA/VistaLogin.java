/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.ControladorLogin;
import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIniciar;
    private JLabel etiquetaIntentos;
    private int intentos = 0;
    private final int MAX_INTENTOS = 3;

    private ControladorLogin controlador;

    public VistaLogin() {
        setTitle("Iniciar Sesión - MercaTop");
        setSize(380, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        controlador = new ControladorLogin(this);

        // Panel con fondo personalizado (con gradiente)
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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLogo = new JLabel("🛒 MercaTop");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblLogo, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridx = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        campoUsuario = new JTextField(14);
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoUsuario.setBorder(BorderFactory.createLineBorder(new Color(144, 202, 249), 2, true));
        panel.add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        campoContrasena = new JPasswordField(14);
        campoContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoContrasena.setBorder(BorderFactory.createLineBorder(new Color(144, 202, 249), 2, true));
        panel.add(campoContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        botonIniciar = new JButton("Iniciar Sesión");
        botonIniciar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botonIniciar.setBackground(new Color(33, 150, 243));
        botonIniciar.setForeground(Color.WHITE);
        botonIniciar.setFocusPainted(false);
        botonIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(botonIniciar, gbc);

        gbc.gridy = 4;
        etiquetaIntentos = new JLabel("Intentos restantes: " + (MAX_INTENTOS - intentos), SwingConstants.CENTER);
        etiquetaIntentos.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        etiquetaIntentos.setForeground(new Color(100, 100, 100));
        panel.add(etiquetaIntentos, gbc);

        add(panel);

        botonIniciar.addActionListener(e -> controlador.iniciarSesion());
    }

    // Métodos getters y setters
    public String obtenerUsuario() {
        return campoUsuario.getText();
    }

    public String obtenerContrasena() {
        return new String(campoContrasena.getPassword());
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getMaxIntentos() {
        return MAX_INTENTOS;
    }

    // Actualizar la etiqueta de intentos
    public void actualizarIntentos() {
        etiquetaIntentos.setText("Intentos restantes: " + (MAX_INTENTOS - intentos));
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMenu() {
        VistaMenu menu = new VistaMenu();
        menu.setVisible(true);
        dispose();
    }
}


