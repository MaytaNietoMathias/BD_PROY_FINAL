/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONTROLADOR.LogueoUsuariosControlador;
import MODELO.LogueoUsuarios;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class VistaLogueoUsuarios extends JFrame{

    private JTable tablaLogueos;
    private DefaultTableModel modelo;
    private LogueoUsuariosControlador controlador = new LogueoUsuariosControlador();
    private JFrame menuPrincipal;

    
    public VistaLogueoUsuarios(JFrame menuPrincipal) {
        this.menuPrincipal = menuPrincipal;

        setTitle("Historial de Logueos de Usuarios");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ---- Título ----
        JLabel lblTitulo = new JLabel("Logueos de Usuarios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        // ---- Tabla y Modelo ----
        modelo = new DefaultTableModel(
                new Object[]{"ID Log", "ID Usuario", "Nombre", "Rol", "Fecha y Hora"},
                0
        );

        tablaLogueos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaLogueos);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(lblTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
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
        add(panelInferior, BorderLayout.SOUTH);
        // Margen
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        cargarLogueos();
    }
    
    private void cargarLogueos() {
        modelo.setRowCount(0);
        List<LogueoUsuarios> lUsuarios = controlador.listarLogueos();
        for (LogueoUsuarios lu : lUsuarios) {
            modelo.addRow(new Object[]{lu.getIdLog(), lu.getIdUsuario(), lu.getNombre(), lu.getRol(), lu.getFecha()});
        }
    }
}
