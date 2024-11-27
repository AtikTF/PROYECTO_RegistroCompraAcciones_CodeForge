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

    public boolean esTextoValido(String texto) {
        if (texto == null) {
            return false;
        }
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,50}$";
        return texto.matches(regex);
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
}
