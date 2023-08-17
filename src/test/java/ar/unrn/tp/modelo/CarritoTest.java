package ar.unrn.tp.modelo;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CarritoTest {

    //monto del carrito sin descuentos vigentes y con descuentos caducados
    @Test
    public void descuentosCaducados() {
        Categoria categoria1 = new Categoria("Indumentaria");
        Categoria categoria2 = new Categoria("Calzado");
        Marca comarca = new Marca("Comarca");
        Marca acme = new Marca("Acme");
        Producto producto1 = new Producto("Remera", "1", 4000.0, comarca, categoria1);
        Producto producto2 = new Producto("Zapatillas", "2", 30000.0, acme, categoria2);
        Tarjeta memeCard = Tarjeta.MemeCard;
        Carrito carrito = new Carrito();

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);

        MarcaPromocion descuentoMarcaFinalizado = new MarcaPromocion(LocalDate.now().minusDays(20), LocalDate.now().minusDays(10), comarca);
        PagoPromocion descuentoPagoFinalizado = new PagoPromocion(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), memeCard);

        assertEquals(34000, carrito.calcularMontoCarrito(descuentoMarcaFinalizado, descuentoPagoFinalizado, memeCard));
    }

    //monto del carrito con un descuento vigente para marca Acme
    @Test
    public void descuentoMarca() {
        Categoria categoria1 = new Categoria("Indumentaria");
        Categoria categoria2 = new Categoria("Calzado");
        Marca comarca = new Marca("Comarca");
        Marca acme = new Marca("Acme");
        Producto producto1 = new Producto("Remera", "1", 4000.0, comarca, categoria1);
        Producto producto2 = new Producto("Zapatillas", "2", 30000.0, acme, categoria2);
        Tarjeta memeCard = Tarjeta.MemeCard;
        Carrito carrito = new Carrito();

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);

        MarcaPromocion descuentoMarcaFinalizado = new MarcaPromocion(LocalDate.now().minusDays(2), LocalDate.now().plusDays(5), acme);
        PagoPromocion descuentoPagoFinalizado = new PagoPromocion(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), memeCard);

        assertEquals(32500, carrito.calcularMontoCarrito(descuentoMarcaFinalizado, descuentoPagoFinalizado, memeCard));
    }

    //monto del carrito con un descuento vigente de medio de pago
    @Test
    public void descuentoPago() {
        Categoria categoria1 = new Categoria("Indumentaria");
        Categoria categoria2 = new Categoria("Calzado");
        Marca comarca = new Marca("Comarca");
        Marca acme = new Marca("Acme");
        Producto producto1 = new Producto("Remera", "1", 4000.0, comarca, categoria1);
        Producto producto2 = new Producto("Zapatillas", "2", 30000.0, acme, categoria2);
        Tarjeta visa = Tarjeta.VISA;
        Carrito carrito = new Carrito();

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);

        MarcaPromocion descuentoMarcaFinalizado = new MarcaPromocion(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), acme);
        PagoPromocion descuentoPagoFinalizado = new PagoPromocion(LocalDate.now().minusDays(2), LocalDate.now().plusDays(5), visa);

        assertEquals(31280, carrito.calcularMontoCarrito(descuentoMarcaFinalizado, descuentoPagoFinalizado, visa));
    }

    //monto del carrito con un descuento vigente para marca Comarca y tarjeta MemeCard
    @Test
    public void descuentoMarcaYProducto() {
        Categoria categoria1 = new Categoria("Indumentaria");
        Categoria categoria2 = new Categoria("Calzado");
        Marca comarca = new Marca("Comarca");
        Marca acme = new Marca("Acme");
        Producto producto1 = new Producto("Remera", "1", 4000.0, comarca, categoria1);
        Producto producto2 = new Producto("Zapatillas", "2", 30000.0, acme, categoria2);
        Tarjeta memeCard = Tarjeta.MemeCard;
        Carrito carrito = new Carrito();

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);

        MarcaPromocion descuentoMarca1 = new MarcaPromocion(LocalDate.now().minusDays(2), LocalDate.now().plusDays(5), comarca);
        PagoPromocion descuentoPago1 = new PagoPromocion(LocalDate.now().minusDays(3), LocalDate.now().plusDays(2), memeCard);

        assertEquals(31096, carrito.calcularMontoCarrito(descuentoMarca1, descuentoPago1, memeCard));
    }

    //realizar pago y validar venta
    @Test
    public void pagar() {
        Categoria categoria1 = new Categoria("Indumentaria");
        Categoria categoria2 = new Categoria("Calzado");
        Marca comarca = new Marca("Comarca");
        Marca acme = new Marca("Acme");
        Producto producto1 = new Producto("Remera", "1", 4000.0, comarca, categoria1);
        Producto producto2 = new Producto("Zapatillas", "2", 30000.0, acme, categoria2);
        Tarjeta naranja = Tarjeta.NARANJA;
        Carrito carrito = new Carrito();
        Tienda tienda = new Tienda();
        Cliente cliente = new Cliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        cliente.agregarTarjeta(naranja);

        MarcaPromocion descuentoMarca1 = new MarcaPromocion(LocalDate.now().minusDays(2), LocalDate.now().plusDays(7), comarca);
        PagoPromocion descuentoPago1 = new PagoPromocion(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), naranja);

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);

        Venta venta = carrito.pagar(descuentoMarca1, descuentoPago1, cliente, naranja);
        tienda.agregarVenta(venta);

        assertTrue(tienda.existeVenta(venta));
    }

    //producto sin categoria, precio y descripcion
    @Test
    public void productoSinCategoria() {
        Marca comarca = new Marca("Comarca");

        assertThrows(RuntimeException.class, () -> {
            new Producto("Remera", "1", 4000.0, comarca, null);
        });
    }

    @Test
    public void productoSinPrecio() {
        Marca comarca = new Marca("Comarca");
        Categoria categoria1 = new Categoria("Indumentaria");

        assertThrows(RuntimeException.class, () -> {
            new Producto("Remera", "1", null, comarca, categoria1);
        });
    }

    @Test
    public void productoSinDescripcion() {
        Marca comarca = new Marca("Comarca");
        Categoria categoria1 = new Categoria("Indumentaria");

        assertThrows(RuntimeException.class, () -> {
            new Producto(null, "1", 4000.0, comarca, categoria1);
        });
    }

    //cliente sin dni, nombre y apellido
    @Test
    public void clienteSinDNI() {
        assertThrows(RuntimeException.class, () -> {
            new Cliente("Daiana", "Alonso", null, "dalonso@gmail.com");
        });
    }

    @Test
    public void clienteSinNombre() {
        assertThrows(RuntimeException.class, () -> {
            new Cliente(null, "Alonso", "42448077", "dalonso@gmail.com");
        });
    }

    @Test
    public void clienteSinApellido() {
        assertThrows(RuntimeException.class, () -> {
            new Cliente("Daiana", null, "42448077", "dalonso@gmail.com");
        });
    }

    @Test
    public void clienteConMailValido() {
        assertThrows(RuntimeException.class, () -> {
            new Cliente("Daiana", "Alonso", "42448077", "123");
        });
    }

    // descuento de pago con fechas invalidas
    @Test
    public void descuentoConFechaInvalida() {
        assertThrows(RuntimeException.class, () -> {
            new PagoPromocion(LocalDate.now(), LocalDate.now().minusDays(2), Tarjeta.NARANJA);
        });
    }
}
