/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.util.Date;

/**
 *
 * @author diego
 */
public class LogueoUsuarios {
    private int idLog;
    private int idUsuario;
    private String nombre;
    private String rol;
    private Date fecha; // <-- AHORA ES DATE

    public LogueoUsuarios() {
    }

    public LogueoUsuarios(int idUsuario, String nombre, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
    }

    public LogueoUsuarios(int idLog, int idUsuario, String nombre, String rol, Date fecha) {
        this.idLog = idLog;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
        this.fecha = fecha;
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
