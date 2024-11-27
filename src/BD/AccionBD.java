package BD;

import Modelo.Accion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AccionBD {

    public boolean registrarCompra(Accion accion) {
        PreparedStatement ps = null;
        Connection con = Conexion.getConexion();
        String sql = "INSERT INTO compras (id_usuario, nombre_accion, fecha_compra, cantidad, valor) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, accion.getId_usuario());
            ps.setString(2, accion.getNombre_accion());
            ps.setString(3, accion.getFecha_compra());
            ps.setInt(4, accion.getCantidad());
            ps.setDouble(5, accion.getValor());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra de la acción");
            return false;
        }
    }

    public void mostrarCompras(JTable tabla, int idUsuario) {
        Connection con = Conexion.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, id_usuario, nombre_accion, fecha_compra, cantidad, valor FROM compras WHERE id_usuario = ?";

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("ID Usuario");
        modelo.addColumn("Nombre Acción");
        modelo.addColumn("Fecha Compra");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor");

        tabla.setModel(modelo);

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario); // Se establece el parámetro para el ID del usuario
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getInt("id_usuario");
                fila[2] = rs.getString("nombre_accion");
                fila[3] = rs.getDate("fecha_compra").toString();
                fila[4] = rs.getInt("cantidad");
                fila[5] = rs.getDouble("valor");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos de compras: " + e.getMessage());
        }
    }

    public Accion obtenerCompraPorId(int idCompra) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = Conexion.getConexion();
        String sql = "SELECT id, id_usuario, nombre_accion, fecha_compra, cantidad, valor FROM compras WHERE id = ?";
        Accion accion = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCompra); // Establece el ID de la compra
            rs = ps.executeQuery();

            if (rs.next()) {
                accion = new Accion();
                accion.setId(rs.getInt("id"));
                accion.setId_usuario(rs.getInt("id_usuario"));
                accion.setNombre_accion(rs.getString("nombre_accion"));
                accion.setFecha_compra(rs.getString("fecha_compra"));
                accion.setCantidad(rs.getInt("cantidad"));
                accion.setValor(rs.getDouble("valor"));
            } else {
                JOptionPane.showMessageDialog(null, "Acción no encontrada", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la compra", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return accion;
    }
}
