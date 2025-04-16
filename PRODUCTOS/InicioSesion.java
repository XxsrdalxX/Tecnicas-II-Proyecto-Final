import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class InicioSesion extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JButton iniciarSesionButton;
    private JButton registrarButton;
    private boolean inicioSesionExitoso = false;
    // Mapa para almacenar usuarios y contraseñas
    private HashMap<String, String> usuarios = new HashMap<>();

    public InicioSesion() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // Componentes
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField();

        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaField = new JPasswordField();

        iniciarSesionButton = new JButton("Iniciar Sesión");
        registrarButton = new JButton("Registrar");

        // Añadir componentes al JFrame
        add(usuarioLabel);
        add(usuarioField);
        add(contrasenaLabel);
        add(contrasenaField);
        add(iniciarSesionButton);
        add(registrarButton);

        // Acción para el botón de iniciar sesión
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
        
                if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contrasena)) {
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                    inicioSesionExitoso = true; // Marca el inicio de sesión como exitoso
                    dispose(); // Cierra la ventana de inicio de sesión
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });

        // Acción para el botón de registrar
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRegistro();
            }
        });
    }

    // Método para mostrar una ventana de registro
    private void mostrarVentanaRegistro() {
        // Crear un nuevo JFrame para el registro
        JFrame registroFrame = new JFrame("Registrar Usuario");
        registroFrame.setSize(300, 200);
        registroFrame.setLocationRelativeTo(this);
        registroFrame.setLayout(new GridLayout(3, 2));

        // Componentes para el registro
        JLabel nuevoUsuarioLabel = new JLabel("Nuevo Usuario:");
        JTextField nuevoUsuarioField = new JTextField();

        JLabel nuevaContrasenaLabel = new JLabel("Nueva Contraseña:");
        JPasswordField nuevaContrasenaField = new JPasswordField();

        JButton registrarNuevoButton = new JButton("Registrar");

        // Añadir componentes al JFrame de registro
        registroFrame.add(nuevoUsuarioLabel);
        registroFrame.add(nuevoUsuarioField);
        registroFrame.add(nuevaContrasenaLabel);
        registroFrame.add(nuevaContrasenaField);
        registroFrame.add(new JLabel()); // Espacio vacío
        registroFrame.add(registrarNuevoButton);

        // Acción para el botón de registrar en la ventana de registro
        registrarNuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoUsuario = nuevoUsuarioField.getText();
                String nuevaContrasena = new String(nuevaContrasenaField.getPassword());

                if (usuarios.containsKey(nuevoUsuario)) {
                    JOptionPane.showMessageDialog(registroFrame, "El usuario ya existe. Intente con otro nombre.");
                } else {
                    usuarios.put(nuevoUsuario, nuevaContrasena);
                    JOptionPane.showMessageDialog(registroFrame, "Usuario registrado exitosamente.");
                    registroFrame.dispose(); // Cierra la ventana de registro
                }
            }
        });

        registroFrame.setVisible(true);
    }

    
    public boolean isInicioSesionExitoso() {
        return inicioSesionExitoso;
    }
    
}