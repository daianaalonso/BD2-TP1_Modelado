package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private LocalDateTime fecha;
    private Cliente cliente;
    private Tarjeta tarjeta;
    private List<Producto> productos;
    private double montoTotal;

    public Venta(LocalDateTime fecha, Cliente cliente, Tarjeta tarjeta, List<Producto> productos, double montoTotal) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
        this.productos = productos;
        this.montoTotal = montoTotal;
    }
}
