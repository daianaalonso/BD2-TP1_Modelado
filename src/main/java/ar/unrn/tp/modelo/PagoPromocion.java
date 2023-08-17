package ar.unrn.tp.modelo;

import java.time.LocalDate;

public class PagoPromocion extends Promocion {
    private Tarjeta tarjeta;


    public PagoPromocion(LocalDate fechaInicio, LocalDate fechaFin, Tarjeta tarjeta) {
        super(fechaInicio, fechaFin);
        this.tarjeta = tarjeta;
    }

    public double aplicarDescuento(double precio, Tarjeta tarjeta) {
        if (tarjeta.equals(this.tarjeta))
            return precio - (precio * descuento());
        return precio;
    }

    public double descuento() {
        LocalDate hoy = LocalDate.now();
        if (hoy.isAfter(this.fechaFin()) || hoy.isBefore(this.fechaInicio())) {
            return 0;
        }
        return 0.08;
    }
}
