package Modelo;

import BD.AccionBD;
import Controlador.AccionController;
import Vista.JFAcciones;
import Vista.JFDetalleAccion;
import Vista.JFRegistrarAccion;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFAcciones jfAcciones = new JFAcciones();
        AccionBD accionBD = new AccionBD();
        JFRegistrarAccion jfRegistrarAccion = new JFRegistrarAccion();
        JFDetalleAccion jfDetalleAccion = new JFDetalleAccion();
        Accion accion = new Accion();
        AccionController accionController = new AccionController(accionBD, jfAcciones,jfRegistrarAccion,jfDetalleAccion,accion);
        jfAcciones.setVisible(true);
        jfAcciones.setLocationRelativeTo(null);
        jfDetalleAccion.setLocationRelativeTo(null);
        jfRegistrarAccion.setLocationRelativeTo(null);
    } 
}
