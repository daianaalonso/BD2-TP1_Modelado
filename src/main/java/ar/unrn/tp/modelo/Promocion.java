package ar.unrn.tp.modelo;

import java.time.LocalDate;

public abstract class Promocion {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Promocion(LocalDate fechaInicio, LocalDate fechaFin) {
        if (!validarFecha(fechaInicio, fechaFin))
            throw new RuntimeException("Las fechas de la promoción no son validas.");
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    private boolean validarFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return fechaInicio.isBefore(fechaFin);
    }
}
