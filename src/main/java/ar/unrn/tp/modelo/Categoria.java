package ar.unrn.tp.modelo;

public class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public boolean esCategoria(Categoria categoria) {
        return this.nombre.equals(categoria.nombre);
    }

    private String nombre() {
        return this.nombre;
    }
}
