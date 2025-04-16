import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class VentanaInventario extends JFrame {
    Inventario inventario = new Inventario();
    private GestionFacturas gestorFacturas = new GestionFacturas(); // Instancia para manejar facturas

    JTextArea areaProductos;
    public VentanaInventario(){
    setTitle("Inventario de Productos");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    // Área de texto para mostrar productos
    areaProductos = new JTextArea();
    areaProductos.setEditable(false);
    add(new JScrollPane(areaProductos), BorderLayout.CENTER);

    // Botón para mostrar productos
    JButton btnVerProductos = new JButton("Ver Productos");
    btnVerProductos.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            mostrarProductos();
        }
    });


     // Panel inferior con botones
     JPanel panelBotones = new JPanel();
     panelBotones.add(btnVerProductos);

     add(panelBotones, BorderLayout.SOUTH); 

      // Botón para agregar producto
JButton btnAgregarProducto = new JButton("Agregar Producto");
btnAgregarProducto.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        agregarProducto();
    }
});
panelBotones.add(btnAgregarProducto); // Lo añadimos al panel de botones

// Botón para eliminar producto
JButton btnEliminarProducto = new JButton("Eliminar Producto");
btnEliminarProducto.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        eliminarProducto();
    }
});
panelBotones.add(btnEliminarProducto);

// Agregar botón para vender producto
JButton btnVenderProducto = new JButton("Vender Producto");
btnVenderProducto.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        venderProducto();
    }
});
panelBotones.add(btnVenderProducto); // Añadir el botón al panel de botones

// Botón para aplicar descuento
JButton btnAplicarDescuento = new JButton("Aplicar Descuento");
btnAplicarDescuento.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        aplicarDescuento();
    }
});
panelBotones.add(btnAplicarDescuento);

// Botón para buscar producto
JButton btnBuscarProducto = new JButton("Buscar Producto");
btnBuscarProducto.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        buscarProducto();
    }
});
panelBotones.add(btnBuscarProducto);
}

public void mostrarProductos() {
    StringBuilder sb = new StringBuilder();
    for (String nombre : inventario.productos.keySet()) {
        Productos p = inventario.productos.get(nombre);
        sb.append("Nombre: ").append(p.getNombre())
          .append(" | Descripción: ").append(p.getDescripcion())
          .append(" | Precio: ").append(p.getPrecio())
          .append(" | Stock: ").append(p.getCantidadEnStock()).append("\n");
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
        if (nombre == null || nombre.isEmpty()) return;

        String descripcion = JOptionPane.showInputDialog(this, "Ingrese la descripción:");
        if (descripcion == null || descripcion.isEmpty()) return;

        String precioStr = JOptionPane.showInputDialog(this, "Ingrese el precio:");
        double precio = Double.parseDouble(precioStr);

        String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad en stock:");
        int cantidad = Integer.parseInt(cantidadStr);

        // Crear el producto (usa una subclase anónima si tu clase Productos es abstracta)
        Productos nuevo = new Productos(nombre, descripcion, precio, cantidad) {};
        inventario.AgregarProducto(nombre, nuevo); // Agregar al inventario

        mostrarProductos(); // Actualizar el área de texto inmediatamente
        JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error: precio o cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar producto.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


public void eliminarProducto() {
    String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto a eliminar:");
    if (nombre == null || nombre.isEmpty()) return;

    if (inventario.productos.containsKey(nombre)) {
        inventario.EliminarProducto(nombre);
        mostrarProductos(); // Actualizar el área de texto
        JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.");
    } else {
        JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
    }
}
public void aplicarDescuento() {
    String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
    if (nombre == null || nombre.isEmpty()) return;

    if (inventario.productos.containsKey(nombre)) {
        try {
            String descuentoStr = JOptionPane.showInputDialog(this, "Ingrese el porcentaje de descuento (ej: 20):");
            double porcentaje = Double.parseDouble(descuentoStr);

            Productos p = inventario.productos.get(nombre);
            double precioOriginal = p.getPrecio();
            double nuevoPrecio = precioOriginal - (precioOriginal * (porcentaje / 100));
            p.setPrecio(nuevoPrecio);

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
    if (nombre == null || nombre.isEmpty()) return;

    if (inventario.productos.containsKey(nombre)) {
        Productos p = inventario.productos.get(nombre);
        String info = "Nombre: " + p.getNombre() +
                      "\nDescripción: " + p.getDescripcion() +
                      "\nPrecio: " + p.getPrecio() +
                      "\nCantidad en stock: " + p.getCantidadEnStock();
        JOptionPane.showMessageDialog(this, info, "Producto encontrado", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
    }
}

// Método para vender un producto
public void venderProducto() {
    String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto a vender:");
    if (nombre == null || nombre.isEmpty()) return;

    if (inventario.productos.containsKey(nombre)) {
        try {
            String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad a vender:");
            int cantidad = Integer.parseInt(cantidadStr);

            Productos producto = inventario.productos.get(nombre);

            if (producto.getCantidadEnStock() >= cantidad) {
                producto.setCantidadEnStock(producto.getCantidadEnStock() - cantidad);
                mostrarProductos(); // Actualizar el área de texto
                JOptionPane.showMessageDialog(this, "Venta realizada exitosamente.");

                 // Crear factura
                 gestorFacturas.crearFactura(nombre, cantidad, producto.getPrecio());
            } else {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock para realizar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "El producto no existe en el inventario.");
    }
}

}
