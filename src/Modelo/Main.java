package Modelo;

import BD.AccionBD;
import Controlador.AccionController;
import Vista.JFAcciones;
import Vista.JFDetalleAccion;
import Vista.JFRegistrarAccion;
import Vista.JFramelogin;
import listener.LoginSuccessListener;

public class Main {

    public static void main(String[] args) {

        LoginSuccessListener listener = new LoginSuccessListener() {
            @Override
            public void onLoginSuccess() {
                JFAcciones jfAcciones = new JFAcciones();
                AccionBD accionBD = new AccionBD();
                JFRegistrarAccion jfRegistrarAccion = new JFRegistrarAccion();
                JFDetalleAccion jfDetalleAccion = new JFDetalleAccion();
                Accion accion = new Accion();
                AccionController accionController = new AccionController(accionBD, jfAcciones, jfRegistrarAccion, jfDetalleAccion, accion);

                jfAcciones.setVisible(true);
                jfAcciones.setLocationRelativeTo(null);
                jfRegistrarAccion.setLocationRelativeTo(null);
                jfDetalleAccion.setLocationRelativeTo(null);
            }
        };

        JFramelogin jfLogin = new JFramelogin(listener);
        jfLogin.setVisible(true);
    }
}
