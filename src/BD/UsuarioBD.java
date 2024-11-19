package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UsuarioBD {

//    public boolean registrarUsuario(String nombreUsuario, String contrasenia){
//        PreparedStatement ps = null;
//        Connection con = Conexion.getConexion();
//        String sql = "INSERT INTO USUARIO (NOMBRE_USUARIO, CONTRASEÑA) "
//                + "VALUES (?,?,?)";
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setString(1, nombreUsuario);
//            ps.setString(2, contrasenia);
//            ps.execute();
//            return true;
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error al crear la cuenta", "Crear cuenta", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        
//    }
    
    public boolean validarExistencia(String nombreUsuario, String contrasenia) {
        PreparedStatement ps = null;
        Connection con = Conexion.getConexion();
        ResultSet rs;
        String sql = "SELECT * FROM USUARIO WHERE NOMBRE_USUARIO = ? AND CONTRASEÑA = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenia);
            
            rs = ps.executeQuery();
            if (rs.next()) return true;
                
            return false;
        } catch (SQLException e) {
            System.err.println(e + " No se pudo conectar");
            return false;
        } 
    }
}
