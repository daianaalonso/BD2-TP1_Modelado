package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> productos;

    public Carrito() {
        productos = new ArrayList<>();
    }

    public void agregarProductoAlCarrito(Producto p) {
        if (p == null) {
            throw new RuntimeException("El producto no puede ser vacio.");
        }
        this.productos.add(new Producto(p.descripcion(), p.codigo(), p.precio(), p.marca(), p.categoria()));
    }

    public double calcularMontoCarrito(MarcaPromocion marcaPromocion, PagoPromocion pagoPromocion, Tarjeta tarjeta) {
        if (marcaPromocion == null || pagoPromocion == null)
            throw new RuntimeException("Las promociones no pueden ser vacias.");
        if (tarjeta == null)
            throw new RuntimeException("La tarjeta no puede ser vacia.");

        double precio = 0;
        for (Producto producto : this.productos) {
            precio += producto.precio() - (producto.precio() * marcaPromocion.aplicarDescuento(producto));
        }
        return pagoPromocion.aplicarDescuento(precio, tarjeta);
    }

    public Venta pagar(MarcaPromocion marcaPromocion, PagoPromocion pagoPromocion, Cliente cliente, Tarjeta tarjeta) {
        return new Venta(LocalDateTime.now(), cliente, tarjeta, productos, calcularMontoCarrito(marcaPromocion, pagoPromocion, tarjeta));
    }
}
