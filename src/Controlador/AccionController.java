package Controlador;

import BD.AccionBD;
import Modelo.Accion;
import Modelo.AccionAPI;
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
    private AccionAPI accionAPI;

    public AccionController(AccionBD accionBD, JFAcciones jfAcciones, JFRegistrarAccion jfRegistrarAccion, JFDetalleAccion jfDetalleAccion, Accion accion, AccionAPI accionAPI) {
        this.jfAcciones = jfAcciones;
        this.accionBD = accionBD;
        this.jfRegistrarAccion = jfRegistrarAccion;
        this.jfDetalleAccion = jfDetalleAccion;
        this.accion = accion;
        this.accionAPI = accionAPI;

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
                    jfDetalleAccion.jTValorActualD.setText(String.valueOf(accionAPI.obtenerPrecioActual(jfDetalleAccion.jTNombreD.getText())));
                    
                    double valorActual = Double.parseDouble(jfDetalleAccion.jTValorActualD.getText());
                    double valorCompra = Double.parseDouble(jfDetalleAccion.jTValorCompraD.getText());
                    int cantidad = Integer.parseInt(jfDetalleAccion.jTCantidadD.getText());
                    jfDetalleAccion.jTValorUnidadD.setText(String.valueOf(accion.valorPorUnidad(cantidad, valorCompra)));
                    
                    if (accion.calcularGananciaPerdida(cantidad,valorCompra, valorActual) > 0) {
                        jfDetalleAccion.jTGananciaA.setText(String.valueOf(accion.calcularGananciaPerdida(cantidad,valorCompra, valorActual)));
                        jfDetalleAccion.jTPerdidaA.setText("0.0");
                    }else{
                        jfDetalleAccion.jTPerdidaA.setText(String.valueOf(accion.calcularGananciaPerdida(cantidad,valorCompra, valorActual)));
                        jfDetalleAccion.jTGananciaA.setText("0.0");
                    }
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

            if (!accionAPI.validarNombreEmpresa(nombreAccion)) {
                JOptionPane.showMessageDialog(null, "Ingresar un tickers valido de una empresa");
                return;
            }

            if (!accion.esValorValido(valor)) {
                JOptionPane.showMessageDialog(null, "Ingresar solo numeros positivos y diferentes de cero");
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
            int id = Integer.parseInt(jfAcciones.jTMostrarID.getText());
            accionBD.mostrarCompras(jfAcciones.jTableAcciones, id);
        }

        if (e.getSource() == jfDetalleAccion.jBSalirD) {
            jfDetalleAccion.dispose();
            jfAcciones.setVisible(true);
        }
    }
}
