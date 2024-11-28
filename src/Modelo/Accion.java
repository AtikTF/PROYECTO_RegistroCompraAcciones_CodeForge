package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Accion {

    private int id;
    private int id_usuario;
    private String nombre_accion;
    private String fecha_compra;
    private int cantidad;
    private double valor;

    public Accion() {
    }

    public Accion(int id_usuario, String nombre_accion, String fecha_compra, int cantidad, double valor) {
        this.id_usuario = id_usuario;
        this.nombre_accion = nombre_accion;
        this.fecha_compra = fecha_compra;
        this.cantidad = cantidad;
        this.valor = valor;
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

    public String obtenerFechaActual() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaActual.format(formato);
    }

    public double valorActualTotal(int cantidad, double precioActual) {
        double precioActualTotal = precioActual * cantidad;
        return Math.round(precioActualTotal * 100.0) / 100.0;
    }
    
    public double gananciaPerdida(double valorActual, double valorCompra){
        double diferencia = valorActual - valorCompra;
        return Math.round(diferencia * 100.0) / 100.0;
    }
    
    public double gananciaPerdidaPorcentaje(double valorActual, double valorCompra){
        double porcentaje = (gananciaPerdida(valorActual, valorCompra)/valorCompra)*100;
        return Math.round(porcentaje * 100.0) / 100.0;
    }
}
