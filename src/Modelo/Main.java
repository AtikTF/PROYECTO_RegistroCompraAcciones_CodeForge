package Modelo;

import BD.AccionBD;
import BD.UsuarioBD;
import Controlador.UsuarioController;
import Vista.JFAcciones;
import Vista.JFLogin;
import Vista.JFRegistro;

public class Main {

    public static void main(String[] args) {
        
        JFLogin jfLogin = new JFLogin();
        JFAcciones jFAcciones = new JFAcciones();
        Usuario usuario = new Usuario();
        UsuarioBD usuarioBD = new UsuarioBD();
        AccionBD accionBD = new AccionBD();
        JFRegistro jfRegistro = new JFRegistro();
        UsuarioController usuarioController = new UsuarioController(jfLogin, jFAcciones, usuario, usuarioBD, accionBD, jfRegistro);   
        usuarioController.iniciar();
        jfLogin.setVisible(true);
        jfLogin.setLocationRelativeTo(null);
        jfRegistro.setLocationRelativeTo(null);
    }
}
