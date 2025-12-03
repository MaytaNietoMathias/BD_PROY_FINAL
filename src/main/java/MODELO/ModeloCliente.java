/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

public class ModeloCliente {
    private int idCliente;
    private String nombre;
    private int edad;
    private int comprasRealizadas;

    // Constructor vacío
    public ModeloCliente() {}

    // Constructor con parámetros
    public ModeloCliente(int idCliente, String nombre, int edad, int comprasRealizadas) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.edad = edad;
        this.comprasRealizadas = comprasRealizadas;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getComprasRealizadas() { return comprasRealizadas; }
    public void setComprasRealizadas(int comprasRealizadas) { this.comprasRealizadas = comprasRealizadas; }
}
