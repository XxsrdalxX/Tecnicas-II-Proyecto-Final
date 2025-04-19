import java.util.ArrayList;

public class GestionFacturas implements Facturas {
    private ArrayList<String> facturas = new ArrayList<>();

    // Getter para obtener la lista de facturas
    public ArrayList<String> getFacturas() {
        return facturas;
    }

    // Setter para establecer una nueva lista de facturas
    public void setFacturas(ArrayList<String> facturas) {
        this.facturas = facturas;
    }

    @Override
    public void crearFactura(String producto, int cantidad, double precioTotal) {
        String factura = "Producto: " + producto + "\nCantidad: " + cantidad + "\nTotal: $" + precioTotal;
        facturas.add(factura);
    }
    @Override
    public void mostrarFacturas() {
        System.out.println("Facturas generadas:");
        for (String factura : facturas) {
            System.out.println(factura);
        }
    }
}