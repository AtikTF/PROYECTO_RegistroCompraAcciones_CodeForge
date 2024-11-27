package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UsuarioBD {

    public boolean validarExistencia(String nombreUsuario, String contrasenia) {
        PreparedStatement ps = null;
        Connection con = Conexion.getConexion();
        ResultSet rs;
        String sql = "SELECT * FROM USUARIOS WHERE NOMBRE_USUARIO = ? AND CONTRASEÑA = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenia);

            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerIdUsuario(String nombreUsuario, String contrasenia) {
        PreparedStatement ps = null;
        Connection con = Conexion.getConexion();
        ResultSet rs = null;
        String sql = "SELECT id FROM USUARIOS WHERE NOMBRE_USUARIO = ? AND CONTRASEÑA = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenia);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el ID del usuario");
        } 
        return -1;
    }

}
