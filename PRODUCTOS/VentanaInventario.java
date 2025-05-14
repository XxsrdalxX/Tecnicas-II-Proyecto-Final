import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

public class VentanaInventario extends JFrame {
    private HashMap<String, Productos> productos = new HashMap<>(); // Mapa para almacenar productos
    private JTextArea areaProductos;
    private GestionFacturas gestorFacturas = new GestionFacturas(); // Instancia para manejar facturas
    private final String Productos = "productos.txt"; // Archivo para guardar productos

    public VentanaInventario() {
        setTitle("Inventario de Productos");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto para mostrar productos
        areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        add(new JScrollPane(areaProductos), BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 filas, 2 columnas, separación de 10px
        panelBotones.setBorder(BorderFactory.createTitledBorder("Opciones del Inventario"));

        // Botón para ver facturas del día
        JButton btnVerFacturasDelDia = new JButton("Ver Facturas del Día");
        btnVerFacturasDelDia.addActionListener(e -> mostrarFacturas());
        panelBotones.add(btnVerFacturasDelDia);

        // Botón para agregar producto
        JButton btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        panelBotones.add(btnAgregarProducto);

        // Botón para eliminar producto
        JButton btnEliminarProducto = new JButton("Eliminar Producto");
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        panelBotones.add(btnEliminarProducto);

        // Botón para vender producto
        JButton btnVenderProducto = new JButton("Vender Producto");
        btnVenderProducto.addActionListener(e -> venderProducto());
        panelBotones.add(btnVenderProducto);

        // Botón para aplicar descuento
        JButton btnAplicarDescuento = new JButton("Aplicar Descuento");
        btnAplicarDescuento.addActionListener(e -> aplicarDescuento());
        panelBotones.add(btnAplicarDescuento);

        // Botón para buscar producto
        JButton btnBuscarProducto = new JButton("Buscar Producto");
        btnBuscarProducto.addActionListener(e -> buscarProducto());
        panelBotones.add(btnBuscarProducto);

        // Botón para ver facturas
        JButton btnVerFacturas = new JButton("Ver Facturas");
        btnVerFacturas.addActionListener(e -> mostrarFacturas());
        panelBotones.add(btnVerFacturas);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Métodos para manejar el inventario
    public void mostrarProductos() {
  cargarProductosDesdeArchivo();
        StringBuilder sb = new StringBuilder();
        for (String nombre : productos.keySet()) {
            Productos p = productos.get(nombre);
            sb.append("Nombre: ").append(p.getNombre())
                    .append(" | Descripción: ").append(p.getDescripcion())
                    .append(" | Precio: ").append(p.getPrecio())
                    .append(" | Stock: ").append(p.getCantidadEnStock()).append("\n");
            // Verificar si el stock es menor a 5
            if (p.getCantidadEnStock() < 5) {
                JOptionPane.showMessageDialog(this,
                        "El producto '" + p.getNombre() + "' tiene menos de 5 unidades en stock.",
                        "Advertencia de Stock Bajo",
                        JOptionPane.WARNING_MESSAGE);
            }
        }


        if (sb.length() == 0) {
            areaProductos.setText("No hay productos en el inventario.");
        } else {
            areaProductos.setText(sb.toString());
        }
    }

    public void agregarProducto() {
        try {

            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
            if (nombre == null || nombre.isEmpty())
                return;

            String descripcion = JOptionPane.showInputDialog(this, "Ingrese la descripción:");
            if (descripcion == null || descripcion.isEmpty())
                return;

            String precioStr = JOptionPane.showInputDialog(this, "Ingrese el precio:");
            double precio = Double.parseDouble(precioStr);

            String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad en stock:");
            int cantidad = Integer.parseInt(cantidadStr);

          
            if (productos.containsKey(nombre)) {
                JOptionPane.showMessageDialog(this, "Ya existe un producto con ese nombre.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Productos nuevo = new Productos(nombre, descripcion, precio, cantidad) {
            };
            productos.put(nombre, nuevo); // Agregar al inventario
  guardarProductosEnArchivo(nombre, descripcion, precio, cantidad); // Guardar en archivo
            mostrarProductos(); // Actualizar el área de texto inmediatamente
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: precio o cantidad inválida.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto a eliminar:");
        if (nombre == null || nombre.isEmpty())
            return;

        if (productos.containsKey(nombre)) {
            productos.remove(nombre);
            reescribirArchivoProductos();
            mostrarProductos(); // Actualizar el área de texto
            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
        }
    }

    public void venderProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto a vender:");
        if (nombre == null || nombre.isEmpty())
            return;

        if (productos.containsKey(nombre)) {
            try {
                String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a vender:");
                int cantidad = Integer.parseInt(cantidadStr);

                Productos producto = productos.get(nombre);

                if (producto.getCantidadEnStock() >= cantidad) {
                    double precioTotal = producto.getPrecio() * cantidad;
                    producto.setCantidadEnStock(producto.getCantidadEnStock() - cantidad);
                    reescribirArchivoProductos(); // Actualizar el archivo después de la venta
                    mostrarProductos(); // Actualizar el área de texto
                    JOptionPane.showMessageDialog(this, "Venta realizada exitosamente.");

                    // Crear factura
                    gestorFacturas.crearFactura(nombre, cantidad, precioTotal);
                    String factura = "Producto: " + nombre + "\nCantidad: " + cantidad + "\nTotal: $" + precioTotal;
                    JOptionPane.showMessageDialog(this, factura, "Factura Generada", JOptionPane.INFORMATION_MESSAGE);
                    // Verificar si el stock es menor a 5 después de la venta
                    if (producto.getCantidadEnStock() < 5) {
                        JOptionPane.showMessageDialog(this,
                                "El producto '" + producto.getNombre() + "' tiene menos de 5 unidades en stock.",
                                "Advertencia de Stock Bajo",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else if (producto.getCantidadEnStock() == 0) {
                    JOptionPane.showMessageDialog(this,
                            "El producto '" + producto.getNombre() + "' se ha agotado.",
                            "Producto Agotado",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No hay suficiente stock para realizar la venta.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
        }
    }

    public void aplicarDescuento() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
        if (nombre == null || nombre.isEmpty())
            return;

        if (productos.containsKey(nombre)) {
            try {
                String descuentoStr = JOptionPane.showInputDialog(this, "Ingrese el porcentaje de descuento (ej: 20):");
                double porcentaje = Double.parseDouble(descuentoStr);

                Productos p = productos.get(nombre);
                double precioOriginal = p.getPrecio();
                double nuevoPrecio = precioOriginal - (precioOriginal * (porcentaje / 100));
                p.setPrecio(nuevoPrecio);
reescribirArchivoProductos();
                mostrarProductos(); // Actualiza el área
                JOptionPane.showMessageDialog(this,
                        "Descuento aplicado.\nPrecio original: " + precioOriginal + "\nNuevo precio: " + nuevoPrecio);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Porcentaje inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
        }
    }

    public void buscarProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto a buscar:");
        if (nombre == null || nombre.isEmpty())
            return;

        if (productos.containsKey(nombre)) {
            Productos p = productos.get(nombre);
            String info = "Nombre: " + p.getNombre() +
                    "\nDescripción: " + p.getDescripcion() +
                    "\nPrecio: " + p.getPrecio() +
                    "\nCantidad en stock: " + p.getCantidadEnStock();
            JOptionPane.showMessageDialog(this, info, "Producto encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
        }
    }

    public void mostrarFacturas() {
        StringBuilder sb = new StringBuilder();
        for (String factura : gestorFacturas.getFacturas()) {
            sb.append(factura).append("\n\n");
        }

        if (sb.length() == 0) {
            JOptionPane.showMessageDialog(this, "No hay facturas generadas hoy.", "Facturas del Día",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, sb.toString(), "Facturas del Día", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void guardarProductosEnArchivo(String nombre, String descripcion, double precio, int cantidad) {
        try (FileWriter writer = new FileWriter(Productos, true)) {
            writer.write("Nombre: " + nombre + "\n");
            writer.write("Descripción: " + descripcion + "\n");
            writer.write("Precio: " + productos.get(nombre).getPrecio() + "\n");
            writer.write("Cantidad en stock: " + productos.get(nombre).getCantidadEnStock() + "\n");

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto en el archivo.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
private void cargarProductosDesdeArchivo() {
    File archivo = new File(Productos);
    if (!archivo.exists()) {
        return; // Si el archivo no existe, no hay productos que cargar
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        String nombre = null, descripcion = null;
        double precio = 0.0;
        int cantidadEnStock = 0;

        while ((linea = reader.readLine()) != null) {
            if (linea.startsWith("Nombre: ")) {
                nombre = linea.substring(8).trim(); // Extraer el nombre
            } else if (linea.startsWith("Descripción: ")) {
                descripcion = linea.substring(13).trim(); // Extraer la descripción
            } else if (linea.startsWith("Precio: ")) {
                precio = Double.parseDouble(linea.substring(8).trim()); // Extraer el precio
            } else if (linea.startsWith("Cantidad en stock: ")) {
                cantidadEnStock = Integer.parseInt(linea.substring(19).trim()); // Extraer la cantidad
            } else if (linea.trim().isEmpty() || linea.equals("---")) {
                // Si encontramos una línea vacía o un delimitador, creamos el producto
                if (nombre != null && descripcion != null) {
                    Productos producto = new Productos(nombre, descripcion, precio, cantidadEnStock) {};
                    productos.put(nombre, producto); // Agregar al HashMap
                }
                // Reiniciar las variables para el siguiente producto
                nombre = null;
                descripcion = null;
                precio = 0.0;
                cantidadEnStock = 0;
            }
        }

        // Agregar el último producto si no hay un delimitador al final
        if (nombre != null && descripcion != null) {
            Productos producto = new Productos(nombre, descripcion, precio, cantidadEnStock) {};
            productos.put(nombre, producto);
        }

        System.out.println("Productos cargados exitosamente desde el archivo.");
    } catch (IOException e) {
        System.out.println("Error al cargar los productos desde el archivo.");
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Error en el formato de los datos del archivo.");
        e.printStackTrace();
    }


}

private void reescribirArchivoProductos() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Productos))) {
        for (Productos p : productos.values()) {
            writer.write("Nombre: " + p.getNombre() + "\n");
            writer.write("Descripción: " + p.getDescripcion() + "\n");
            writer.write("Precio: " + p.getPrecio() + "\n");
            writer.write("Cantidad en stock: " + p.getCantidadEnStock() + "\n");
            writer.write("---\n");
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al actualizar el archivo de productos.", "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
}