package VISTA;

import CONTROLADOR.Conexion;
import MODELO.ProductoDAO;
import MODELO.ProductoModelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VistaRegistrarVenta extends JFrame {
    private JTable tablaVentas;
    private DefaultTableModel modelo;
    private JFrame menuPrincipal;
    private JTextField campoCliente;
    private JTextField campoEdadCliente;
    private JTextField campoDniCliente;
    private JTextField campoTelefonoCliente;
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

        // Edad del Cliente
        gbc.gridx = 0; gbc.gridy = 1;
        panelRegistro.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        campoEdadCliente = new JTextField(5);
        panelRegistro.add(campoEdadCliente, gbc);

        // DNI del Cliente
        gbc.gridx = 0; gbc.gridy = 2;
        panelRegistro.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        campoDniCliente = new JTextField(10);
        panelRegistro.add(campoDniCliente, gbc);

        // Teléfono del Cliente
        gbc.gridx = 0; gbc.gridy = 3;
        panelRegistro.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        campoTelefonoCliente = new JTextField(12);
        panelRegistro.add(campoTelefonoCliente, gbc);

        // ID Trabajador (ComboBox con lista de trabajadores)
        gbc.gridx = 0; gbc.gridy = 4;
        panelRegistro.add(new JLabel("ID Trabajador:"), gbc);
        gbc.gridx = 1;
        campoIdTrabajador = new JComboBox<>();
        panelRegistro.add(campoIdTrabajador, gbc);

        // Categoría
        gbc.gridx = 0; gbc.gridy = 5;
        panelRegistro.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        campoCategoria = new JComboBox<>();
        panelRegistro.add(campoCategoria, gbc);

        // Producto
        gbc.gridx = 0; gbc.gridy = 6;
        panelRegistro.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        campoProducto = new JComboBox<>();
        panelRegistro.add(campoProducto, gbc);

        // Cantidad
        gbc.gridx = 0; gbc.gridy = 7;
        panelRegistro.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        campoCantidad = new JTextField(5);
        panelRegistro.add(campoCantidad, gbc);

        // Precio (solo lectura - se carga automáticamente)
        gbc.gridx = 0; gbc.gridy = 8;
        panelRegistro.add(new JLabel("Precio (S/):"), gbc);
        gbc.gridx = 1;
        campoPrecio = new JTextField(10);
        campoPrecio.setEditable(false);
        campoPrecio.setBackground(new Color(240, 240, 240));
        panelRegistro.add(campoPrecio, gbc);

        // Monto total (se calcula automáticamente)
        gbc.gridx = 0; gbc.gridy = 9;
        panelRegistro.add(new JLabel("Monto Total (S/):"), gbc);
        gbc.gridx = 1;
        campoMonto = new JTextField(10);
        campoMonto.setEditable(false);
        campoMonto.setBackground(new Color(240, 240, 240));
        panelRegistro.add(campoMonto, gbc);

        // Fecha (fecha actual automática)
        gbc.gridx = 0; gbc.gridy = 10;
        panelRegistro.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        campoFecha = new JTextField(10);
        campoFecha.setEditable(false);
        campoFecha.setBackground(new Color(240, 240, 240));
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        campoFecha.setText(formatoFecha.format(new Date()));
        panelRegistro.add(campoFecha, gbc);

        // Botón registrar
        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar Venta");
        btnRegistrar.addActionListener(e -> {
            registrarVenta();
            cargarVentas();
            limpiarCampos();
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

        // 🔹 Integración con ProductoDAO
        ProductoDAO productoDAO = new ProductoDAO();
        
        // ✅ Llenar combo de trabajadores
        cargarTrabajadores();
        
        // ✅ Llenar combo de categorías (SIN REPETIR)
        cargarCategoriasUnicas();

        // ✅ Listener para cargar productos según categoría seleccionada
        campoCategoria.addActionListener(e -> {
            campoProducto.removeAllItems();
            campoPrecio.setText("");
            campoMonto.setText("");
            
            String categoriaSeleccionada = (String) campoCategoria.getSelectedItem();
            if (categoriaSeleccionada != null && !categoriaSeleccionada.isEmpty()) {
                for (ProductoModelo producto : productoDAO.obtenerProductosPorCategoria(categoriaSeleccionada)) {
                    campoProducto.addItem(producto.getNombre());
                }
            }
        });

        // ✅ Listener para mostrar precio según producto seleccionado
        campoProducto.addActionListener(e -> {
            String categoriaSeleccionada = (String) campoCategoria.getSelectedItem();
            String productoSeleccionado = (String) campoProducto.getSelectedItem();
            if (categoriaSeleccionada != null && productoSeleccionado != null) {
                for (ProductoModelo producto : productoDAO.obtenerProductosPorCategoria(categoriaSeleccionada)) {
                    if (producto.getNombre().equals(productoSeleccionado)) {
                        campoPrecio.setText(String.format("%.2f", producto.getPrecio()));
                        calcularMonto(); // Auto-calcular monto si hay cantidad
                        break;
                    }
                }
            }
        });

        // ✅ Listener para calcular monto automáticamente al cambiar cantidad
        campoCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calcularMonto();
            }
        });

        // 🔹 Cargar tabla ventas con columna EDAD
        cargarVentas();
    }

    // ✅ Método para cargar SOLO trabajadores del área de Ventas
    private void cargarTrabajadores() {
        Conexion conexion = new Conexion();
        String sql = "SELECT ID_Trabajador, Nombre_Trabajador FROM Trabajadores WHERE Rol = 'Ventas' ORDER BY Nombre_Trabajador";
        
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            campoIdTrabajador.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("ID_Trabajador");
                String nombre = rs.getString("Nombre_Trabajador");
                // Formato: "ID - Nombre"
                campoIdTrabajador.addItem(id + " - " + nombre);
            }
            
            if (campoIdTrabajador.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No hay trabajadores asignados al área de Ventas.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar trabajadores:\n" + e.getMessage());
        }
    }

    // ✅ Método para cargar categorías ÚNICAS (sin repetir)
    private void cargarCategoriasUnicas() {
        Conexion conexion = new Conexion();
        String sql = "SELECT DISTINCT Tipo_Producto FROM Productos ORDER BY Tipo_Producto";
        
        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            campoCategoria.removeAllItems();
            while (rs.next()) {
                String categoria = rs.getString("Tipo_Producto");
                if (categoria != null && !categoria.trim().isEmpty()) {
                    campoCategoria.addItem(categoria);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías:\n" + e.getMessage());
        }
    }

    // ✅ Método para calcular monto automáticamente
    private void calcularMonto() {
        try {
            String cantidadStr = campoCantidad.getText().trim();
            String precioStr = campoPrecio.getText().trim();
            
            if (!cantidadStr.isEmpty() && !precioStr.isEmpty()) {
                int cantidad = Integer.parseInt(cantidadStr);
                double precio = Double.parseDouble(precioStr);
                double monto = cantidad * precio;
                campoMonto.setText(String.format("%.2f", monto));
            }
        } catch (NumberFormatException e) {
            campoMonto.setText("");
        }
    }

    // ✅ Método para asignar el ID del trabajador desde el login (OPCIONAL)
    public void setEmpleadoID(int idEmpleado) {
        // Buscar y seleccionar el trabajador en el combo
        for (int i = 0; i < campoIdTrabajador.getItemCount(); i++) {
            String item = campoIdTrabajador.getItemAt(i);
            if (item.startsWith(idEmpleado + " - ")) {
                campoIdTrabajador.setSelectedIndex(i);
                break;
            }
        }
    }

    // ✅ Método para registrar venta con validaciones
    private void registrarVenta() {
        // Validaciones
        if (campoCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre del cliente.");
            return;
        }
        
        if (campoEdadCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la edad del cliente.");
            return;
        }
        
        if (campoDniCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el DNI del cliente.");
            return;
        }
        
        if (campoTelefonoCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el teléfono del cliente.");
            return;
        }
        
        if (campoIdTrabajador.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un trabajador.");
            return;
        }
        
        if (campoProducto.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto.");
            return;
        }
        
        if (campoCantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cantidad.");
            return;
        }

        try {
            String cliente = campoCliente.getText().trim();
            int edad = Integer.parseInt(campoEdadCliente.getText().trim());
            String dni = campoDniCliente.getText().trim();
            String telefono = campoTelefonoCliente.getText().trim();
            
            // ✅ Extraer ID del trabajador del ComboBox (formato: "ID - Nombre")
            String trabajadorSeleccionado = (String) campoIdTrabajador.getSelectedItem();
            int idTrabajador = Integer.parseInt(trabajadorSeleccionado.split(" - ")[0]);
            
            String producto = (String) campoProducto.getSelectedItem();
            int cantidad = Integer.parseInt(campoCantidad.getText());
            double precio = Double.parseDouble(campoPrecio.getText());
            double monto = cantidad * precio;
            String fecha = campoFecha.getText();

            Conexion conexion = new Conexion();
            
            // ✅ Verificar/crear cliente con todos los datos
            Integer idCliente = obtenerOCrearCliente(cliente, edad, dni, telefono);
            if (idCliente == null) {
                JOptionPane.showMessageDialog(this, "Error al procesar el cliente.");
                return;
            }

            // ✅ Insertar venta en la BD
            String sql = "INSERT INTO Ventas (ID_Trabajador, ID_Producto, Cantidad, Monto, Fecha, Cliente) " +
                         "VALUES (?, (SELECT ID_Producto FROM Productos WHERE Nombre_Producto=?), ?, ?, ?, ?)";

            try (Connection con = conexion.establecerConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idTrabajador);
                ps.setString(2, producto);
                ps.setInt(3, cantidad);
                ps.setDouble(4, monto);
                ps.setDate(5, java.sql.Date.valueOf(fecha));
                ps.setInt(6, idCliente);
                ps.executeUpdate();
                
                // ✅ Incrementar el contador de compras del cliente
                incrementarComprasCliente(idCliente);
                
                JOptionPane.showMessageDialog(this, "✅ Venta registrada correctamente.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que edad, cantidad y precio sean números válidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar venta:\n" + e.getMessage());
        }
    }

    // ✅ Método para obtener o crear cliente con TODOS los datos
    private Integer obtenerOCrearCliente(String nombreCliente, int edad, String dni, String telefono) {
        Conexion conexion = new Conexion();
        
        try (Connection con = conexion.establecerConexion()) {
            // Verificar si el cliente existe por DNI
            String sqlBuscar = "SELECT ID_Cliente FROM Clientes WHERE Nombre = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlBuscar)) {
                ps.setString(1, nombreCliente);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    // Cliente existe, actualizar sus datos
                    int idCliente = rs.getInt("ID_Cliente");
                    String sqlActualizar = "UPDATE Clientes SET Edad = ?, DNI = ?, Telefono = ? WHERE ID_Cliente = ?";
                    try (PreparedStatement psUpdate = con.prepareStatement(sqlActualizar)) {
                        psUpdate.setInt(1, edad);
                        psUpdate.setString(2, dni);
                        psUpdate.setString(3, telefono);
                        psUpdate.setInt(4, idCliente);
                        psUpdate.executeUpdate();
                    }
                    return idCliente;
                }
            }
            
            // Si no existe, crear nuevo cliente
            String sqlInsertar = "INSERT INTO Clientes (Nombre, Edad, DNI, Telefono, Compras_Realizadas) VALUES (?, ?, ?, ?, 0)";
            try (PreparedStatement ps = con.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nombreCliente);
                ps.setInt(2, edad);
                ps.setString(3, dni);
                ps.setString(4, telefono);
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar cliente:\n" + e.getMessage());
        }
        return null;
    }

    // ✅ Método para incrementar el contador de compras del cliente
    private void incrementarComprasCliente(int idCliente) {
        Conexion conexion = new Conexion();
        String sql = "UPDATE Clientes SET Compras_Realizadas = Compras_Realizadas + 1 WHERE ID_Cliente = ?";
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al incrementar compras: " + e.getMessage());
        }
    }

    // ✅ Método para cargar ventas con columna EDAD
    private void cargarVentas() {
        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        // ✅ Agregar columna EDAD del cliente
        modelo.addColumn("ID Venta");
        modelo.addColumn("Cliente");
        modelo.addColumn("Edad"); // ✅ NUEVO: Columna Edad
        modelo.addColumn("Trabajador");
        modelo.addColumn("Producto");
        modelo.addColumn("Categoría");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Monto");
        modelo.addColumn("Fecha");

        Conexion conexion = new Conexion();
        String sql = "SELECT v.ID_Venta, c.Nombre AS Cliente, c.Edad, " +
                     "t.Nombre_Trabajador AS Trabajador, " +
                     "p.Nombre_Producto AS Producto, " +
                     "p.Tipo_Producto AS Categoria, " +
                     "v.Cantidad, p.Precio, v.Monto, v.Fecha " +
                     "FROM Ventas v " +
                     "JOIN Clientes c ON v.Cliente = c.ID_Cliente " +
                     "JOIN Trabajadores t ON v.ID_Trabajador = t.ID_Trabajador " +
                     "JOIN Productos p ON v.ID_Producto = p.ID_Producto " +
                     "ORDER BY v.ID_Venta DESC";

        try (Connection con = conexion.establecerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("ID_Venta"),
                        rs.getString("Cliente"),
                        rs.getInt("Edad"), // ✅ NUEVO: Edad del cliente
                        rs.getString("Trabajador"),
                        rs.getString("Producto"),
                        rs.getString("Categoria"),
                        rs.getInt("Cantidad"),
                        String.format("%.2f", rs.getDouble("Precio")),
                        String.format("%.2f", rs.getDouble("Monto")),
                        rs.getDate("Fecha")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ventas:\n" + e.getMessage());
        }
    }

    // ✅ Método para limpiar campos después de registrar
    private void limpiarCampos() {
        campoCliente.setText("");
        campoEdadCliente.setText("");
        campoDniCliente.setText("");
        campoTelefonoCliente.setText("");
        campoCantidad.setText("");
        campoMonto.setText("");
        // Mantener categoría y producto seleccionados para facilitar múltiples ventas
    }
}