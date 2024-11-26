package Modelo;

public class Usuario {
    
    private int id;
    private String nombreUsuario;
    private String contraseña;

    // Constructor, getters y setters
    public Usuario(int id, String nombreUsuario, String contraseña) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }
}
