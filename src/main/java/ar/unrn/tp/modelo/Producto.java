package ar.unrn.tp.modelo;

public class Producto {
    private String descripcion;
    private String codigo;
    private Double precio;
    private Marca marca;
    private Categoria categoria;

    public Producto(String descripcion, String codigo, Double precio, Marca marca, Categoria categoria) {
        if (esDatoVacio(codigo))
            throw new RuntimeException("El codigo debe ser valido");
        this.codigo = codigo;

        if (descripcion == null || descripcion.isEmpty())
            throw new RuntimeException("La descripcion debe ser valida");
        this.descripcion = descripcion;

        if (esDatoVacio(String.valueOf(precio)) || esDatoNulo(precio))
            throw new RuntimeException("El precio debe ser valido");
        this.precio = precio;

        if (esDatoNulo(categoria))
            throw new RuntimeException("La categoria debe ser valida");
        this.categoria = categoria;

        if (esDatoNulo(marca))
            throw new RuntimeException("La marca debe ser valida");
        this.marca = marca;
    }

    private boolean esDatoVacio(String dato) {
        return dato.equals("");
    }

    private boolean esDatoNulo(Object dato) {
        return dato == null;
    }

    public String descripcion() {
        return descripcion;
    }

    public String codigo() {
        return codigo;
    }

    public double precio() {
        return precio;
    }

    public boolean esMarca(Marca marca) {
        return this.marca.equals(marca);
    }

    public Categoria categoria() {
        return categoria;
    }

    public Marca marca() {
        return marca;
    }
}
