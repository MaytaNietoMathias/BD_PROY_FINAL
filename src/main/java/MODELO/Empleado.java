/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

public class Empleado {
    private int id;
    private String nombre;
    private int edad;
    private String correo;
    private String rol;
    private String horario;

    public Empleado(int id, String nombre, int edad, String correo, String rol, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.correo = correo;
        this.rol = rol;
        this.horario = horario;
    }

    public Empleado() {} // Constructor vacío útil para DAO

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}



