package ar.unrn.tp.modelo;

public class Marca {

    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public boolean esMarca(Marca marca) {
        return this.nombre.equals(marca.nombre());
    }

    private String nombre() {
        return this.nombre;
    }
}
