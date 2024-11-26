package BD;

import Modelo.Usuario;
import java.sql.*;

public class UsuarioBD {

    private Connection conexion;

    // Constructor que recibe una conexión
    public UsuarioBD(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener un usuario por nombre de usuario
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        try {
            String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contraseña")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
