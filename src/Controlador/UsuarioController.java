package Controlador;

import BD.AccionBD;
import BD.UsuarioBD;
import Modelo.Accion;
import Modelo.AccionAPI;
import Modelo.Usuario;
import Vista.JFAcciones;
import Vista.JFLogin;
import Vista.JFRegistrarAccion;
import Vista.JFRegistro;
import Vista.JFResumen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class UsuarioController implements ActionListener {

    private JFLogin jfLogin;
    private JFRegistro jfRegistro;
    private JFAcciones jfAcciones;
    private Usuario usuario;
    private UsuarioBD usuarioBD;
    private AccionBD accionBD;

    public UsuarioController(JFLogin jfLogin, JFAcciones jfAcciones, Usuario usuario, UsuarioBD usuarioBD, AccionBD accionBD, JFRegistro jfRegistro) {
        this.jfLogin = jfLogin;
        this.jfAcciones = jfAcciones;
        this.usuario = usuario;
        this.usuarioBD = usuarioBD;
        this.accionBD = accionBD;
        this.jfRegistro = jfRegistro;

        this.jfLogin.jBIniciarSesion.addActionListener(this);
        this.jfLogin.jBRegistrarse.addActionListener(this);
        this.jfRegistro.jBRegistrarUsuario.addActionListener(this);
        this.jfRegistro.jBVolverLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jfLogin.jBIniciarSesion) {
            String usuarioI = jfLogin.jTUsuario.getText();
            String contrasenia = jfLogin.jPContrasenia.getText();

            if (usuarioBD.validarExistencia(usuarioI, contrasenia)) {
                JOptionPane.showMessageDialog(null, "Inicio de Sesión existoso");

                int id = usuarioBD.obtenerIdUsuario(usuarioI, contrasenia);
                jfAcciones.jTMostrarID.setText(String.valueOf(id));
                accionBD.mostrarCompras(jfAcciones.jTableAcciones, id);
                jfLogin.dispose();
                jfAcciones.setVisible(true);
                jfAcciones.jTMostrarID.setVisible(false);
                jfLogin.jTUsuario.setText("");
                jfLogin.jPContrasenia.setText("");

            } else {
                JOptionPane.showMessageDialog(null, "Error en las credenciales");
            }
        }

        if (e.getSource() == jfLogin.jBRegistrarse) {
            jfLogin.setVisible(false);
            jfRegistro.setVisible(true);
        }

        if (e.getSource() == jfRegistro.jBRegistrarUsuario) {
            String nombreN = jfRegistro.jTNombreN.getText();
            String contraseniaN = jfRegistro.jPFContraseniaN.getText();
            String telefonoN = jfRegistro.jTTelefonoN.getText();
            String emailN = jfRegistro.jTEmailN.getText();

            if (!usuario.esNumeroValido(telefonoN)) {
                JOptionPane.showMessageDialog(null, "Ingresar un numero de telefono valido");
                return;
            }

            if (!usuario.esSoloLetras(nombreN)) {
                JOptionPane.showMessageDialog(null, "Para el nombre ingresar solo letras");
                return;
            }

            if (usuario.esEmailValido(emailN)) {

                Usuario usuarioNuevo = new Usuario(nombreN, contraseniaN, telefonoN, emailN);
                boolean registroUsuario = usuarioBD.registrarUsuario(usuarioNuevo);

                if (registroUsuario) {
                    jfRegistro.jTNombreN.setText("");
                    jfRegistro.jPFContraseniaN.setText("");
                    jfRegistro.jTTelefonoN.setText("");
                    jfRegistro.jTEmailN.setText("");
                    JOptionPane.showMessageDialog(null, "Se registró con éxito el nuevo usuario");
                }else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el nuevo usuario.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingresar Email valido");
                return;
            }

        }

        if (e.getSource() == jfRegistro.jBVolverLogin) {
            jfRegistro.dispose();
            jfLogin.setVisible(true);
        }

    }

    public void iniciar() {
        JFRegistrarAccion jfRegistrarAccion = new JFRegistrarAccion();
        Accion accion = new Accion();
        AccionAPI accionAPI = new AccionAPI();
        JFResumen jfResumen = new JFResumen();
        AccionController accionController = new AccionController(accionBD, jfAcciones, jfRegistrarAccion, accion, accionAPI, jfResumen, jfLogin);
        jfAcciones.setLocationRelativeTo(null);
        jfRegistrarAccion.setLocationRelativeTo(null);
        jfResumen.setLocationRelativeTo(null);
    }
}
