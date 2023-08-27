package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class MarcaPromocion extends Promocion {
    private Marca marca;

    public MarcaPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Marca marca) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    protected MarcaPromocion() {

    }

    public double aplicarDescuento(Producto producto) {
        if (producto.esMarca(this.marca)) {
            return descuento();
        }
        return 0;
    }

    public double descuento() {
        if (estaEnCurso())
            return super.porcentaje();
        return 0;
    }
}
