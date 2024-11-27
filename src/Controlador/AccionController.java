package Controlador;

import BD.AccionBD;
import Modelo.Accion;
import Vista.JFAcciones;
import Vista.JFDetalleAccion;
import Vista.JFRegistrarAccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class AccionController implements ActionListener {

    private AccionBD accionBD;
    private JFAcciones jfAcciones;
    private JFRegistrarAccion jfRegistrarAccion;
    private JFDetalleAccion jfDetalleAccion;
    private Accion accion;

    public AccionController(AccionBD accionBD, JFAcciones jfAcciones, JFRegistrarAccion jfRegistrarAccion, JFDetalleAccion jfDetalleAccion, Accion accion) {
        this.jfAcciones = jfAcciones;
        this.accionBD = accionBD;
        this.jfRegistrarAccion = jfRegistrarAccion;
        this.jfDetalleAccion = jfDetalleAccion;
        this.accion = accion;

        this.jfAcciones.jBRegistrarAcciones.addActionListener(this);
        this.jfRegistrarAccion.jBGuardarAccion.addActionListener(this);
        this.jfRegistrarAccion.jBSalirA.addActionListener(this);
        this.jfDetalleAccion.jBSalirD.addActionListener(this);

        this.jfAcciones.jTableAcciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = jfAcciones.jTableAcciones.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idCompra = Integer.parseInt(jfAcciones.jTableAcciones.getValueAt(filaSeleccionada, 0).toString());
                    Accion accionSeleccionada = new Accion();
                    accionSeleccionada = accionBD.obtenerCompraPorId(idCompra);
                    jfAcciones.dispose();
                    jfDetalleAccion.setVisible(true);
                    jfDetalleAccion.setLocationRelativeTo(null);
                    jfDetalleAccion.jTNombreD.setText(String.valueOf(accionSeleccionada.getNombre_accion()));
                    jfDetalleAccion.jTCantidadD.setText(String.valueOf(accionSeleccionada.getCantidad()));
                    jfDetalleAccion.jTfechaCompraD.setText(String.valueOf(accionSeleccionada.getFecha_compra()));
                    jfDetalleAccion.jTValorCompraD.setText(String.valueOf(accionSeleccionada.getValor()));
                    jfDetalleAccion.jTFechaActualD.setText(accion.obtenerFechaActual());
                }
            }
        });
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

            if (!accion.esTextoValido(nombreAccion)) {
                JOptionPane.showMessageDialog(null, "El nombre ingresado no es valido: Ingresar solo letras.");
                return;
            }

            if (!accion.esValorValido(valor)) {
                JOptionPane.showMessageDialog(null, "Ingresar solo numeros positivos");
                return;
            }

            if (accion.esCantidadValido(cantidadTexto)) {
                int cantidad  = Integer.parseInt(cantidadTexto);
                
                Accion nuevaAccion = new Accion(id, nombreAccion, fechaCompra, cantidad, valor);

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
            accionBD.mostrarCompras(jfAcciones.jTableAcciones);
        }

        if (e.getSource() == jfDetalleAccion.jBSalirD) {
            jfDetalleAccion.dispose();
            jfAcciones.setVisible(true);
        }
    }
}
