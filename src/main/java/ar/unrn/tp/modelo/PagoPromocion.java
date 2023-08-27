package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class PagoPromocion extends Promocion {

    private Tarjeta tarjeta;


    public PagoPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Tarjeta tarjeta) {
        super(fechaInicio, fechaFin, porcentaje);
        this.tarjeta = tarjeta;
    }

    public double aplicarDescuento(double total, Tarjeta tarjeta) {
        if (tarjeta.esTarjeta(this.tarjeta))
            return total - (total * descuento());
        return total;
    }

    private double descuento() {
        if (estaEnCurso())
            return super.porcentaje();
        return 0;
    }
}
