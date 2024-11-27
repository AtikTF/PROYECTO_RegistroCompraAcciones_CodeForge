package Controlador;

import BD.AccionBD;
import BD.UsuarioBD;
import Modelo.Accion;
import Modelo.Usuario;
import Vista.JFAcciones;
import Vista.JFDetalleAccion;
import Vista.JFLogin;
import Vista.JFRegistrarAccion;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
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
                accionBD.mostrarCompras(jfAcciones.jTableAcciones);
                jfLogin.dispose();
                jfAcciones.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Error en las credenciales");
            }
        }
    }

    public void iniciar() {
        JFRegistrarAccion jfRegistrarAccion = new JFRegistrarAccion();
        JFDetalleAccion jfDetalleAccion = new JFDetalleAccion();
        Accion accion = new Accion();
        AccionController accionController = new AccionController(accionBD, jfAcciones, jfRegistrarAccion, jfDetalleAccion, accion);
        jfAcciones.setLocationRelativeTo(null);
        jfRegistrarAccion.setLocationRelativeTo(null);
        jfDetalleAccion.setLocationRelativeTo(null);
    }
}
