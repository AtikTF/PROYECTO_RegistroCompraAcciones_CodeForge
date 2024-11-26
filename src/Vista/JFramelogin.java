package Vista;

import BD.AccionBD;
import BD.Conexion;
import BD.UsuarioBD;
import Controlador.AccionController;
import Controlador.UsuarioController;
import Modelo.Accion;
import listener.LoginSuccessListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;

public class JFramelogin extends JFrame {
    
    private LoginSuccessListener loginSuccessListener;

    private JPanel contentPane;
    private JTextField txtusuario;
    private JPasswordField txtpassword;

    private UsuarioController usuarioControlador;

    // Constructor modificado para recibir el listener
    public JFramelogin(LoginSuccessListener listener) {
        // Inicialización de conexión y controlador
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        UsuarioBD usuarioBD = new UsuarioBD(conn);
        usuarioControlador = new UsuarioController(usuarioBD);

        // Guardamos el listener recibido
        this.loginSuccessListener = listener;

        // Configuración básica del JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        setTitle("Login");

        // Panel principal con fondo degradado
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(135, 206, 235), getWidth(), getHeight(), new Color(75, 0, 130));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Panel central para el formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        contentPane.add(panelFormulario, BorderLayout.CENTER);

        // Icono de usuario
        JLabel icono = new JLabel(new ImageIcon(getClass().getResource("/Vista/2l.png")));
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.add(icono);

        // Espaciado
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo de usuario
        JLabel lblUsuario = new JLabel("USUARIO:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.add(lblUsuario);

        txtusuario = new JTextField();
        txtusuario.setMaximumSize(new Dimension(250, 30));
        txtusuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.add(txtusuario);

        // Espaciado
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo de contraseña
        JLabel lblPassword = new JLabel("PASSWORD:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.add(lblPassword);

        txtpassword = new JPasswordField();
        txtpassword.setMaximumSize(new Dimension(250, 30));
        txtpassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.add(txtpassword);

        // Espaciado
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botón de iniciar sesión
        JButton btnIniciar = new JButton("Iniciar sesión");
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.setBackground(new Color(30, 144, 255));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnIniciar.addActionListener(e -> {
            String usuario = txtusuario.getText();
            String contraseña = new String(txtpassword.getPassword());

            if (usuarioControlador.autenticar(usuario, contraseña)) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");

                // Llamar al método del listener en caso de éxito
                if (loginSuccessListener != null) {
                    loginSuccessListener.onLoginSuccess();
                }

                this.dispose(); // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelFormulario.add(btnIniciar);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Crear el listener e iniciar la ventana de login
                LoginSuccessListener listener = () -> {
                    JFAcciones jfAcciones = new JFAcciones();
                    AccionBD accionBD = new AccionBD();
                    JFRegistrarAccion jfRegistrarAccion = new JFRegistrarAccion();
                    JFDetalleAccion jfDetalleAccion = new JFDetalleAccion();
                    Accion accion = new Accion();
                    AccionController accionController = new AccionController(accionBD, jfAcciones, jfRegistrarAccion, jfDetalleAccion, accion);

                    jfAcciones.setVisible(true);
                    jfAcciones.setLocationRelativeTo(null);
                    jfRegistrarAccion.setLocationRelativeTo(null);
                    jfDetalleAccion.setLocationRelativeTo(null);
                };

                JFramelogin jfLogin = new JFramelogin(listener);
                jfLogin.setVisible(true); // Muestra la ventana de login
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
