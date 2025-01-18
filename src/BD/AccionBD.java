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

        String sql = "INSERT INTO compras (id_usuario, nombre_accion, fecha_compra, cantidad, valor, valor_actual, ganancia_perdida,ganancia_perdida_porcentaje) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, accion.getId_usuario());
            ps.setString(2, accion.getNombre_accion());
            ps.setString(3, accion.getFecha_compra());
            ps.setInt(4, accion.getCantidad());
            ps.setDouble(5, accion.getValor());
            ps.setDouble(6, accion.getValor_actual());
            ps.setDouble(7, accion.getGanancia_perdida());
            ps.setDouble(8, accion.getGanancia_perdida_porcentaje());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra de la acción: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    public void mostrarCompras(JTable tabla, int idUsuario) {
        Connection con = Conexion.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, id_usuario, nombre_accion, fecha_compra, cantidad, valor, valor_actual, ganancia_perdida, ganancia_perdida_porcentaje "
                + "FROM compras WHERE id_usuario = ?";

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("ID Usuario");
        modelo.addColumn("Nombre");
        modelo.addColumn("Fecha Compra");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor");
        modelo.addColumn("Valor Actual");
        modelo.addColumn("G - P");
        modelo.addColumn("(G - P)%");

        tabla.setModel(modelo);

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getInt("id_usuario");
                fila[2] = rs.getString("nombre_accion");
                fila[3] = rs.getDate("fecha_compra").toString();
                fila[4] = rs.getInt("cantidad");
                fila[5] = rs.getDouble("valor");
                fila[6] = rs.getDouble("valor_actual");
                fila[7] = rs.getDouble("ganancia_perdida");
                fila[8] = rs.getDouble("ganancia_perdida_porcentaje");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos de compras: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    public void mostrarComprasAgrupadas(JTable tabla, int idUsuario) {
        Connection con = Conexion.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT nombre_accion, "
                + "SUM(cantidad) AS total_cantidad, "
                + "SUM(valor) AS total_valor, "
                + "SUM(ganancia_perdida) AS total_ganancia_perdida, "
                + "SUM(ganancia_perdida_porcentaje) AS total_ganancia_perdida_porcentaje "
                + "FROM compras "
                + "WHERE id_usuario = ? "
                + "GROUP BY nombre_accion";

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Acción");
        modelo.addColumn("Cantidad T.");
        modelo.addColumn("Valor USD");
        modelo.addColumn("Precio de Costo");
        modelo.addColumn("( G - P )$");
        modelo.addColumn("( G - P )%");

        tabla.setModel(modelo);

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[6];
                int totalCantidad = rs.getInt("total_cantidad");
                double totalValor = rs.getDouble("total_valor");

                fila[0] = rs.getString("nombre_accion");
                fila[1] = totalCantidad;
                fila[2] = totalValor;
                fila[3] = totalCantidad > 0
                        ? Math.round((totalValor / totalCantidad) * 100.0) / 100.0
                        : 0.0;
                fila[4] = Math.round(rs.getDouble("total_ganancia_perdida") * 100.0) / 100.0;
                fila[5] = Math.round(rs.getDouble("total_ganancia_perdida_porcentaje") * 100.0) / 100.0;
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos agrupados: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}
