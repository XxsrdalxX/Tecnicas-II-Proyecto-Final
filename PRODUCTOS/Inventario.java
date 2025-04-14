import java.util.HashMap;
import java.util.Scanner;
public class Inventario {
    HashMap<String, Productos> productos = new HashMap<>();
    HashMap<String, Integer> cantidades = new HashMap<>(); // Mapa para almacenar las cantidades de cada producto
    
    public void AgregarProducto (String nombre, Productos p){
        if (productos.containsKey(nombre)) {
            System.out.println("El producto ya existe en el inventario.");
        } else {
            System.out.println("El producto no existe en el inventario.");
        }
        System.out.println("¿Cuántas unidades desea agregar?");
        Scanner scanner = new Scanner(System.in);
        int cantidad = scanner.nextInt();
         // Aquí deberías obtener la cantidad de alguna manera, por ejemplo, a través de un escáner
        p.setCantidadEnStock(cantidad);
            System.out.println("Producto agregado al inventario: " + nombre);
            productos.put(nombre, p);
    } 


    public void EliminarProducto (String nombre){
        if (productos.containsKey(nombre)) {
            productos.remove(nombre);
            System.out.println("Producto eliminado del inventario: " + nombre);
        } else {
            System.out.println("El producto no existe en el inventario.");
        }
    }
    public void ModificarProducto (String nombre, Productos p){
        if (productos.containsKey(nombre)) {
            productos.put(nombre, p);
            System.out.println("Producto modificado en el inventario: " + nombre);
        } else {
            System.out.println("El producto no existe en el inventario.");
        }
    }

    public void ActualizarCantidad(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto:");
        String nombre = scanner.nextLine();
        if (productos.containsKey(nombre)) {
            System.out.println("Ingrese la nueva cantidad:");
            int nuevaCantidad = scanner.nextInt();
            productos.get(nombre).setCantidadEnStock(nuevaCantidad);
            System.out.println("Cantidad actualizada para el producto: " + nombre);
        } else {
            System.out.println("El producto no existe en el inventario.");
        }
    }

    public void VerProductos(){

        if(productos.isEmpty()){
            System.out.println("No hay productos en el inventario.");
        } else {
            System.out.println("Productos en el inventario:");
            for (String nombre : productos.keySet()) {
                Productos p = productos.get(nombre);
                System.out.println("Nombre: " + p.getNombre() + ", Descripción: " + p.getDescripcion() + ", Precio: " + p.getPrecio() + ", Cantidad en stock: " + p.getCantidadEnStock());
            }
        }
    }
    public void BuscarProducto(String nombre) {
        if (productos.containsKey(nombre)) {
            Productos p = productos.get(nombre);
            System.out.println("Producto encontrado: " + p.getNombre() + ", Descripción: " + p.getDescripcion() + ", Precio: " + p.getPrecio() + ", Cantidad en stock: " + p.getCantidadEnStock());
        } else {
            System.out.println("El producto no existe en el inventario.");
        }
    }

 public void AplicarDescuento(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del producto:");
        String nombre = scanner.nextLine();
        if (productos.containsKey(nombre)) {
            System.out.println("Ingrese el porcentaje de descuento:");
            double porcentajeDescuento = scanner.nextDouble();
            Productos p = productos.get(nombre);
            double precioConDescuento = p.getPrecio() - (p.getPrecio() * (porcentajeDescuento / 100));
            System.out.println("Precio original: " + p.getPrecio() + ", Precio con descuento: " + precioConDescuento);
        } else {
            System.out.println("El producto no existe en el inventario.");
         }


 }

}