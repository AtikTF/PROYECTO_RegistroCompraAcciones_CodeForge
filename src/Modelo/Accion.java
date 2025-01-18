package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Accion {

    private int id;
    private int id_usuario;
    private String nombre_accion;
    private String fecha_compra;
    private int cantidad;
    private double valor;
    private double valor_actual;
    private double ganancia_perdida;
    private double ganancia_perdida_porcentaje;

    public Accion() {
    }

    public Accion(int id_usuario, String nombre_accion, String fecha_compra, int cantidad, double valor, double valor_actual, double ganancia_perdida, double ganancia_perdida_porcentaje) {
        this.id_usuario = id_usuario;
        this.nombre_accion = nombre_accion;
        this.fecha_compra = fecha_compra;
        this.cantidad = cantidad;
        this.valor = valor;
        this.valor_actual = valor_actual;
        this.ganancia_perdida = ganancia_perdida;
        this.ganancia_perdida_porcentaje = ganancia_perdida_porcentaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_accion() {
        return nombre_accion;
    }

    public void setNombre_accion(String nombre_accion) {
        this.nombre_accion = nombre_accion;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor_actual() {
        return valor_actual;
    }

    public void setValor_actual(double valor_actual) {
        this.valor_actual = valor_actual;
    }

    public double getGanancia_perdida() {
        return ganancia_perdida;
    }

    public void setGanancia_perdida(double ganancia_perdida) {
        this.ganancia_perdida = ganancia_perdida;
    }

    public double getGanancia_perdida_porcentaje() {
        return ganancia_perdida_porcentaje;
    }

    public void setGanancia_perdida_porcentaje(double ganancia_perdida_porcentaje) {
        this.ganancia_perdida_porcentaje = ganancia_perdida_porcentaje;
    }

    public boolean esFechaValida(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate fechaIngresada = LocalDate.parse(fecha, formato);
            LocalDate fechaActual = LocalDate.now();

            if (fechaIngresada.isAfter(fechaActual)) {
                return false;
            }
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean esValorValido(double numero) {
        return numero > 0;
    }

    public boolean esCantidadValido(String texto) {
        if (texto == null) {
            return false;
        }
        String regex = "^[1-9][0-9]*$";
        return texto.matches(regex);
    }

    public double valorActualTotal(int cantidad, double precioActual) {
        double precioActualTotal = precioActual * cantidad;
        return Math.round(precioActualTotal * 100.0) / 100.0;
    }

    public double gananciaPerdida(double valorActual, double valorCompra, int cantidad) {
        double valorCompraUnitario = valorCompra / cantidad;
        double diferencia = valorActual - valorCompraUnitario;
        double gananciaPerdidaTotal = diferencia * cantidad;
        return Math.round(gananciaPerdidaTotal * 100.0) / 100.0;
    }

    public double gananciaPerdidaPorcentaje(double valorActual, double valorCompra, int cantidad) {
        double valorCompraUnitario = valorCompra / cantidad;
        double porcentaje = ((valorActual - valorCompraUnitario) / valorCompraUnitario) * 100;
        return Math.round(porcentaje * 100.0) / 100.0;
    }

    public void ordenarAlfabetico(JTable tabla, int columna) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int rowCount = modelo.getRowCount();
        List<Object[]> filas = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fila[j] = modelo.getValueAt(i, j);
            }
            filas.add(fila);
        }

        filas.sort((fila1, fila2) -> fila1[columna].toString().compareToIgnoreCase(fila2[columna].toString()));

        modelo.setRowCount(0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
    }

    public void ordenarAscendente(JTable tabla, int columna) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int rowCount = modelo.getRowCount();
        List<Object[]> filas = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fila[j] = modelo.getValueAt(i, j);
            }
            filas.add(fila);
        }

        filas.sort((fila1, fila2) -> {
            double num1 = Double.parseDouble(fila1[columna].toString());
            double num2 = Double.parseDouble(fila2[columna].toString());
            return Double.compare(num1, num2);
        });

        modelo.setRowCount(0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
    }

    public void ordenarDescendente(JTable tabla, int columna) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int rowCount = modelo.getRowCount();
        List<Object[]> filas = new ArrayList<>();

        // Copiar las filas de la tabla a una lista
        for (int i = 0; i < rowCount; i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fila[j] = modelo.getValueAt(i, j);
            }
            filas.add(fila);
        }

        // Ordenar las filas en orden descendente
        filas.sort((fila1, fila2) -> {
            double num1 = Double.parseDouble(fila1[columna].toString());
            double num2 = Double.parseDouble(fila2[columna].toString());
            return Double.compare(num2, num1); // Se invierte el orden
        });

        // Limpiar la tabla y volver a llenarla con los datos ordenados
        modelo.setRowCount(0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
    }
}
