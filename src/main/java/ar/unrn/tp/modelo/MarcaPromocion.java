package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class MarcaPromocion extends Promocion {

    private Marca marca;

    public MarcaPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Marca marca) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    public double aplicarDescuento(Producto producto) {
        if (producto.suMarcaEs(this.marca)) {
            return descuento();
        }
        return 0;
    }

    private double descuento() {
        if (estaEnCurso())
            return super.porcentaje();
        return 0;
    }
}
