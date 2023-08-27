package ar.unrn.tp.modelo;

public class Tarjeta {
    private String nombre;
    private int numero;

    public Tarjeta(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public boolean esTarjeta(Tarjeta tarjeta) {
        return this.nombre.equals(tarjeta.nombre());
    }

    private String nombre() {
        return this.nombre;
    }
}
