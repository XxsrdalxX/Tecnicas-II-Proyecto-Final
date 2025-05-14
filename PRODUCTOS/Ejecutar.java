import java.io.IOException;
import java.io.FileWriter;

public class Ejecutar {
    public static void main(String[] args) {
        // Crear la ventana de inicio de sesión
        InicioSesion inicioSesion = new InicioSesion();
        inicioSesion.setVisible(true);
        

        // Esperar hasta que la ventana de inicio de sesión se cierre
        while (inicioSesion.isVisible()) {
            try {
                Thread.sleep(100); // Pequeña pausa para evitar un bucle muy rápido
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Verificar si el inicio de sesión fue exitoso
        if (inicioSesion.isInicioSesionExitoso()) {
            // Crear y mostrar la ventana de inventario
            // Guardar los datos de inicio de sesión en un archivo
           
            VentanaInventario ventana = new VentanaInventario();
            ventana.setVisible(true);
        } else {
            System.out.println("El inicio de sesión no fue exitoso. Cerrando la aplicación.");
        }
    }

    
}