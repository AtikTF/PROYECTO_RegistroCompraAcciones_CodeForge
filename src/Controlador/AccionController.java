package Controlador;

import BD.AccionBD;
import Modelo.Accion;
import Modelo.AccionAPI;
import Vista.JFAcciones;
import Vista.JFRegistrarAccion;
import Vista.JFResumen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class AccionController implements ActionListener {

    private AccionBD accionBD;
    private JFAcciones jfAcciones;
    private JFRegistrarAccion jfRegistrarAccion;
    private Accion accion;
    private AccionAPI accionAPI;
    private JFResumen jfResumen;

    public AccionController(AccionBD accionBD, JFAcciones jfAcciones, JFRegistrarAccion jfRegistrarAccion, Accion accion, AccionAPI accionAPI, JFResumen jfResumen) {
        this.jfAcciones = jfAcciones;
        this.accionBD = accionBD;
        this.jfRegistrarAccion = jfRegistrarAccion;
        this.accion = accion;
        this.accionAPI = accionAPI;
        this.jfResumen = jfResumen;

        this.jfAcciones.jBRegistrarAcciones.addActionListener(this);
        this.jfRegistrarAccion.jBGuardarAccion.addActionListener(this);
        this.jfRegistrarAccion.jBSalirA.addActionListener(this);
        this.jfAcciones.jBOrdenar.addActionListener(this);
        this.jfAcciones.jCBOrdenamiento.addActionListener(this);
        this.jfAcciones.jBResumen.addActionListener(this);
        this.jfResumen.jBVolverResumen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jfAcciones.jBRegistrarAcciones) {
            jfRegistrarAccion.jTidU.setText(jfAcciones.jTMostrarID.getText());
            jfAcciones.setVisible(false);
            jfRegistrarAccion.setVisible(true);
        }

        if (e.getSource() == jfRegistrarAccion.jBGuardarAccion) {
            String nombreAccion = jfRegistrarAccion.jTNombreA.getText();
            String fechaCompra = jfRegistrarAccion.jTFechaCompraA.getText();
            String cantidadTexto = jfRegistrarAccion.jTCantidadA.getText();
            int id = Integer.parseInt(jfAcciones.jTMostrarID.getText());
            double valor = Double.parseDouble(jfRegistrarAccion.jTValorA.getText());

            if (!accion.esFechaValida(fechaCompra)) {
                JOptionPane.showMessageDialog(null, "La fecha ingresada no es válida.");
                return;
            }

            if (!accionAPI.validarNombreEmpresa(nombreAccion)) {
                JOptionPane.showMessageDialog(null, "Ingresar un tickers valido de una empresa");
                return;
            }

            if (!accion.esValorValido(valor)) {
                JOptionPane.showMessageDialog(null, "Ingresar solo numeros positivos y diferentes de cero");
                return;
            }

            if (accion.esCantidadValido(cantidadTexto)) {
                int cantidad = Integer.parseInt(cantidadTexto);
                double precio_actual = accionAPI.obtenerPrecioActual(nombreAccion);
                double ganancia_perdida = accion.gananciaPerdida(precio_actual, valor, cantidad);
                double ganancia_perdida_porcentaje = accion.gananciaPerdidaPorcentaje(precio_actual, valor, cantidad);

                Accion nuevaAccion = new Accion(id, nombreAccion, fechaCompra, cantidad, valor, precio_actual, ganancia_perdida, ganancia_perdida_porcentaje);

                boolean registroExitoso = accionBD.registrarCompra(nuevaAccion);
                if (registroExitoso) {
                    jfRegistrarAccion.jTCantidadA.setText("");
                    jfRegistrarAccion.jTNombreA.setText("");
                    jfRegistrarAccion.jTFechaCompraA.setText("");
                    jfRegistrarAccion.jTValorA.setText("");
                    JOptionPane.showMessageDialog(null, "Se registró con éxito la nueva acción.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar la nueva acción.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingresar la cantidad como un número entero positivo");
                return;
            }

        }

        if (e.getSource() == jfRegistrarAccion.jBSalirA) {
            jfRegistrarAccion.dispose();
            jfAcciones.setVisible(true);
            int id = Integer.parseInt(jfAcciones.jTMostrarID.getText());
            accionBD.mostrarCompras(jfAcciones.jTableAcciones, id);
        }

        if (e.getSource() == jfAcciones.jBOrdenar) {
            int opcionSeleccionada = jfAcciones.jCBOrdenamiento.getSelectedIndex();
            switch (opcionSeleccionada) {
                case 0 -> accion.ordenarAlfabetico(jfAcciones.jTableAcciones, 2);
                case 1 -> accion.ordenarAscendente(jfAcciones.jTableAcciones, 7);
                case 2 -> accion.ordenarDescendente(jfAcciones.jTableAcciones, 7);
                default -> JOptionPane.showMessageDialog(null, "Seleccionar una opción de ordenamiento");
            }
        }
        
        if (e.getSource() == jfResumen.jBVolverResumen) {
            jfResumen.dispose();
            jfAcciones.setVisible(true);
        }
        
        if (e.getSource() == jfAcciones.jBResumen) {
            jfAcciones.setVisible(false);
            jfResumen.setVisible(true);
            int id = Integer.parseInt(jfAcciones.jTMostrarID.getText());
            accionBD.mostrarComprasAgrupadas(jfResumen.jTableResumen, id);
        }
    }
}
