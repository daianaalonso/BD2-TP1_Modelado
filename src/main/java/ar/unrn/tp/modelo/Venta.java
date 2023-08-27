package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private LocalDateTime fecha;
    private Cliente cliente;
    private Tarjeta tarjeta;
    private List<Producto> productosVendidos;
    private Double montoTotal;

    public Venta(LocalDateTime fecha, Cliente cliente, Tarjeta tarjeta, List<Producto> productosVendidos, Double montoTotal) {
        this.productosVendidos = productosVendidos;
        this.fecha = fecha;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
        this.montoTotal = montoTotal;
    }
}
