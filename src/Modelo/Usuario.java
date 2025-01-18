package Modelo;

public class Usuario {

    private int id;
    private String nombreUsuario;
    private String contraseña;
    private String telefono;
    private String email;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contraseña, String telefono, String email) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean esNumeroValido(String numero) {
        String regex = "^\\d{10}$";
        return numero.matches(regex);
    }

    public boolean esSoloLetras(String dato) {
        return dato.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public boolean esEmailValido(String email) {
        String regex = "^[a-zA-Z0-9]+@epn\\.edu\\.ec$";
        return email.matches(regex);
    }
}
