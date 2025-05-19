import java.util.ArrayList;
import java.io.*;

public class GestionFacturas implements Facturas {
    private ArrayList<String> facturasSesion = new ArrayList<>(); // Solo de la sesión actual
    private ArrayList<String> facturasTodas = new ArrayList<>();  // Todas las facturas
    private final String archivoFacturas = "facturas.txt";

    public GestionFacturas() {
        cargarFacturasDesdeArchivo();
    }

    public ArrayList<String> getFacturasSesion() {
        return facturasSesion;
    }

    public ArrayList<String> getFacturasTodas() {
        return facturasTodas;
    }

    @Override
    public void crearFactura(String producto, int cantidad, double precioTotal) {
        String factura = "Producto: " + producto + "\nCantidad: " + cantidad + "\nTotal: $" + precioTotal;
        facturasSesion.add(factura);
        facturasTodas.add(factura);
        guardarFacturasEnArchivo();
    }

    @Override
    public void mostrarFacturas() {
        // No se usa en la interfaz gráfica
    }

    private void guardarFacturasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoFacturas))) {
            for (String factura : facturasTodas) {
                writer.write(factura);
                writer.write("\n---\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las facturas.");
        }
    }

    private void cargarFacturasDesdeArchivo() {
        File archivo = new File(archivoFacturas);
        if (!archivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            StringBuilder factura = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equals("---")) {
                    facturasTodas.add(factura.toString().trim());
                    factura.setLength(0);
                } else {
                    factura.append(linea).append("\n");
                }
            }
            if (factura.length() > 0) {
                facturasTodas.add(factura.toString().trim());
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las facturas.");
        }
    }
}