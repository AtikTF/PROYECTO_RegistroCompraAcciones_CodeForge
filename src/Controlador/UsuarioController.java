package Controlador;

import BD.UsuarioBD;
import Modelo.Usuario;

public class UsuarioController {

    private UsuarioBD usuarioBD;

    // Constructor
    public UsuarioController(UsuarioBD usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    // Método para autenticar al usuario
    public boolean autenticar(String nombreUsuario, String contraseña) {
        Usuario usuario = usuarioBD.obtenerUsuarioPorNombre(nombreUsuario);
        return usuario != null && usuario.getContraseña().equals(contraseña);
    }
}
