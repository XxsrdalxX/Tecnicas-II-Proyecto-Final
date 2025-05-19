package Modelo;
public abstract class Productos {
protected String nombre;
protected String descripcion;
protected double precio;
protected int cantidadEnStock;
protected String categoria;
protected int fechaDeCaducidad;

public Productos(String nombre, String descripcion, double precio, int cantidadEnStock) { 
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.cantidadEnStock = cantidadEnStock;
}






// Getters y Setters
public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}


public String getDescripcion() {
    return descripcion;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public double getPrecio() {
    return precio;
}

public void setPrecio(double precio) {
    this.precio = precio;
}

public int getCantidadEnStock() {
    return cantidadEnStock;
}

public void setCantidadEnStock(int cantidadEnStock) {
    this.cantidadEnStock = cantidadEnStock;
}
}