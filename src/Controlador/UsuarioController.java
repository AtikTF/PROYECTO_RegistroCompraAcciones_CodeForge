package Controlador;

import BD.AccionBD;
import BD.UsuarioBD;
import Modelo.Accion;
import Modelo.AccionAPI;
import Modelo.Usuario;
import Vista.JFAcciones;
import Vista.JFLogin;
import Vista.JFRegistrarAccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class UsuarioController implements ActionListener {

    private JFLogin jfLogin;
    private JFAcciones jfAcciones;
    private Usuario usuario;
    private UsuarioBD usuarioBD;
    private AccionBD accionBD;

    public UsuarioController(JFLogin jfLogin, JFAcciones jfAcciones, Usuario usuario, UsuarioBD usuarioBD, AccionBD accionBD) {
        this.jfLogin = jfLogin;
        this.jfAcciones = jfAcciones;
        this.usuario = usuario;
        this.usuarioBD = usuarioBD;
        this.accionBD = accionBD;

        this.jfLogin.jBIniciarSesion.addActionListener(this);
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
                accionBD.mostrarCompras(jfAcciones.jTableAcciones ,id);
                jfLogin.dispose();
                jfAcciones.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Error en las credenciales");
            }
        }
    }

    public void iniciar() {
        JFRegistrarAccion jfRegistrarAccion = new JFRegistrarAccion();
        Accion accion = new Accion();
        AccionAPI accionAPI = new AccionAPI();
        AccionController accionController = new AccionController(accionBD, jfAcciones, jfRegistrarAccion, accion, accionAPI);
        jfAcciones.setLocationRelativeTo(null);
        jfRegistrarAccion.setLocationRelativeTo(null);
    }
}
