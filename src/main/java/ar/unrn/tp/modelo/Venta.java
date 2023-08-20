package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private LocalDateTime fecha;
    private Cliente cliente;
    private Tarjeta tarjeta;
    private List<Producto> productosVendidos;
    private double montoTotal;

    public Venta(LocalDateTime fecha, Cliente cliente, Tarjeta tarjeta, List<Producto> productosVendidos, double montoTotal) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
        this.productosVendidos = productosVendidos;
        this.montoTotal = montoTotal;
    }
}
