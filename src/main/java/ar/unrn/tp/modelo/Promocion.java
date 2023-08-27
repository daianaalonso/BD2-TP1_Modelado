package ar.unrn.tp.modelo;

import java.time.LocalDate;

public abstract class Promocion {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double porcentaje;

    public Promocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje) {
        if (!validarFecha(fechaInicio, fechaFin))
            throw new RuntimeException("Las fechas de la promoción no son validas.");
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentaje = porcentaje;
    }

    private boolean validarFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return fechaInicio.isBefore(fechaFin);
    }

    public boolean estaEnCurso() {
        LocalDate hoy = LocalDate.now();
        return hoy.isAfter(fechaInicio) && hoy.isBefore(fechaFin);
    }

    protected double porcentaje() {
        return this.porcentaje;
    }
}
