package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class MarcaPromocion extends Promocion {
    private Marca marca;

    public MarcaPromocion(LocalDate fechaInicio, LocalDate fechaFin, Marca marca) {
        super(fechaInicio, fechaFin);
        this.marca = marca;
    }

    public double aplicarDescuento(Producto producto) {
        if (producto.esMarca(this.marca)) {
            return descuento();
        }
        return 0;
    }

    public double descuento() {
        if (estaEnCurso())
            return 0.05;
        return 0;
    }
}
