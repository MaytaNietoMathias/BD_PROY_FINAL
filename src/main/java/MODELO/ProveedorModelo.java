
package MODELO;

public class ProveedorModelo {
    private int idProveedor;
    private String nombreProveedor;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String contacto;
    private String estado;

    // Constructor vacío
    public ProveedorModelo() {
    }

    // Constructor con parámetros
    public ProveedorModelo(int idProveedor, String nombreProveedor, String ruc, 
                          String direccion, String telefono, String email, 
                          String contacto, String estado) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.ruc = ruc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contacto = contacto;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "ProveedorModelo{" +
                "idProveedor=" + idProveedor +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", ruc='" + ruc + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contacto='" + contacto + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
