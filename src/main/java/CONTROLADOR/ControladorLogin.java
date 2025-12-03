/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.ModeloConexion;
import MODELO.UsuarioBD;
import VISTA.VistaLogin;

public class ControladorLogin {

    private VistaLogin vista;
    private ModeloConexion conexion;

    private static UsuarioBD usuarioActual; // Guarda usuario + rol

    public ControladorLogin(VistaLogin vista) {
        this.vista = vista;
        this.conexion = new ModeloConexion(); // ✔ USANDO LA CLASE CORRECTA
    }

    public void iniciarSesion() {

        String nombreUsuario = vista.obtenerUsuario();
        String contrasena = vista.obtenerContrasena();

        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            vista.mostrarError("Usuario y contraseña no pueden estar vacíos.");
            return;
        }

        // ✔ ESTE MÉTODO YA EXISTE EN ModeloConexion
        UsuarioBD u = conexion.autenticarUsuario(nombreUsuario, contrasena);

        if (u != null) {

            usuarioActual = u; // guardamos el usuario completo con su rol

            vista.mostrarMenu();

        } else {

            vista.setIntentos(vista.getIntentos() + 1);
            vista.actualizarIntentos();

            if (vista.getIntentos() >= vista.getMaxIntentos()) {
                vista.mostrarError("Se alcanzó el límite de intentos.");
                System.exit(0);
            } else {
                vista.mostrarError("Usuario o contraseña incorrectos.");
            }
        }
    }

    public static UsuarioBD getUsuarioActual() {
        return usuarioActual;
    }
}
