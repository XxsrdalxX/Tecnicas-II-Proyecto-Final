package Controlador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class InicioSesion extends JFrame {
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JButton iniciarSesionButton;
    private JButton registrarButton;
    private boolean inicioSesionExitoso = false;
    // Mapa para almacenar usuarios y contraseñas
    private HashMap<String, String> usuarios = new HashMap<>();
    private final String archivoUsuarios = "usuarios.txt"; // Archivo para guardar usuarios

    public InicioSesion() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        cargarUsuarios(); // Cargar usuarios desde el archivo al iniciar

        // Componentes
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField();

        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaField = new JPasswordField();

        iniciarSesionButton = new JButton("Iniciar Sesión");
        registrarButton = new JButton("Registrar");

        // Componentes del JFrame
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
                String contrasena = new String(contrasenaField.getPassword()).trim();



                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }
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

        // Componentes del JFrame de registro
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
                
                if (nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(registroFrame, "Por favor, complete todos los campos.");
                    return;
                }

                if (usuarios.containsKey(nuevoUsuario)) {
                    JOptionPane.showMessageDialog(registroFrame, "El usuario ya existe. Intente con otro nombre.");
                } else {
                    usuarios.put(nuevoUsuario, nuevaContrasena);
                    guardarUsuarioEnArchivo(nuevoUsuario, nuevaContrasena);
                    JOptionPane.showMessageDialog(registroFrame, "Usuario registrado exitosamente.");
                    registroFrame.dispose(); // Cierra la ventana de registro
                }
            }
        });

        registroFrame.setVisible(true);
    }

public void guardarUsuarioEnArchivo(String usuario, String contrasena) {
        try (FileWriter writer = new FileWriter(archivoUsuarios, true)) {
            writer.write(usuario + ":" + contrasena + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el usuario en el archivo.");
            e.printStackTrace();
        }
    }

       public void cargarUsuarios() {
        File archivo = new File(archivoUsuarios);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay usuarios que cargar
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    usuarios.put(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los usuarios desde el archivo.");
            e.printStackTrace();
        }
    }



    public boolean isInicioSesionExitoso() {
        return inicioSesionExitoso;
    }

}