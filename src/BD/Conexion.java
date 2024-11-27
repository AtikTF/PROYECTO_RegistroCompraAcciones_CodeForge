package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    private static final String base = "AccionesDB";
    private static final String user = "root";
    private static final String password = "root";
    private static final String url = "jdbc:mysql://localhost:3306/"+base;
    private static Connection con = null;
    
    public static Connection getConexion(){
        try{
            con = DriverManager.getConnection(url, user, password);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se ha podido conectar a la base de datos", "Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return con;
    }
}
