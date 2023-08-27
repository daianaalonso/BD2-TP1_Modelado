package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class VentaServiceTest {

    private Cliente cliente;
    private Tarjeta visa;
    private Marca nike;
    private Categoria indumentaria;
    private Producto remera;
    private Producto pantalon;
    private List<Long> productos;
    private PagoPromocion pagoPromocion;
    private MarcaPromocion marcaPromocion;

    @BeforeEach
    public void setUp() {
        inTransactionExecute(
                (em) -> {
                    cliente = new Cliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
                    visa = new Tarjeta("VISA", 12345678);
                    cliente.agregarTarjeta(visa);
                    nike = new Marca("Nike");
                    indumentaria = new Categoria("Indumentaria");
                    remera = new Producto("Remera", "123", 3500.0, nike, indumentaria);
                    pantalon = new Producto("Pantalon", "345", 7000.0, nike, indumentaria);
                    pagoPromocion = new PagoPromocion(LocalDate.now().minusDays(4), LocalDate.now().plusDays(3), 0.08, visa);
                    marcaPromocion = new MarcaPromocion(LocalDate.now().minusDays(7), LocalDate.now().plusDays(3), 0.05, nike);

                    em.persist(pagoPromocion);
                    em.persist(marcaPromocion);
                    em.persist(cliente);
                    em.persist(remera);
                    em.persist(pantalon);
                }
        );
    }

    @Test
    public void crearVenta() {
        inTransactionExecute(
                (em) -> {
                    TypedQuery<Long> q = em.createQuery("SELECT p.id FROM Producto p", Long.class);
                    this.productos = q.getResultList();
                }
        );
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");
        ventaServiceJPA.realizarVenta(cliente.getId(), this.productos, visa.getId());

        inTransactionExecute(
                (em) -> {
                    Venta venta = em.find(Venta.class, 9L);
                    assertNotNull(venta);
                    assertTrue(venta.montoEs(9177.0));
                }
        );
    }

    @Test
    public void crearVentaConTarjetaInvalida() {
        Tarjeta naranja = new Tarjeta("Naranja");
        inTransactionExecute(
                (em) -> {
                    TypedQuery<Long> q = em.createQuery("SELECT p.id FROM Producto p", Long.class);
                    this.productos = q.getResultList();
                    em.persist(naranja);
                }
        );
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");

        assertThrows(RuntimeException.class, () -> ventaServiceJPA.realizarVenta(cliente.getId(), this.productos, naranja.getId()));
    }

    @Test
    public void crearVentaSinCliente() {
        inTransactionExecute(
                (em) -> {
                    TypedQuery<Long> q = em.createQuery("SELECT p.id FROM Producto p", Long.class);
                    this.productos = q.getResultList();
                }
        );
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");

        assertThrows(RuntimeException.class, () -> ventaServiceJPA.realizarVenta(null, this.productos, visa.getId()));
    }

    @Test
    public void calcularMontoConPromosVigentes() {
        inTransactionExecute(
                (em) -> {
                    TypedQuery<Long> q = em.createQuery("SELECT p.id FROM Producto p", Long.class);
                    this.productos = q.getResultList();
                }
        );
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");
        Double resultadoActual = ventaServiceJPA.calcularMonto(this.productos, visa.getId());

        assertEquals(9177.0, resultadoActual);
    }

    @Test
    public void calcularMontoSinProductos() {
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");

        assertThrows(RuntimeException.class, () -> ventaServiceJPA.calcularMonto(null, visa.getId()));
    }

    @Test
    public void calcularMontoSinTarjeta() {
        inTransactionExecute(
                (em) -> {
                    TypedQuery<Long> q = em.createQuery("SELECT p.id FROM Producto p", Long.class);
                    this.productos = q.getResultList();
                }
        );
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");

        assertThrows(RuntimeException.class, () -> ventaServiceJPA.calcularMonto(this.productos, null));
    }

    @Test
    public void listarVentasPersistidas() {
        inTransactionExecute(
                (em) -> {
                    TypedQuery<Long> q = em.createQuery("SELECT p.id FROM Producto p", Long.class);
                    this.productos = q.getResultList();
                }
        );
        VentaServiceJPA ventaServiceJPA = new VentaServiceJPA("objectdb:myDbTestFile.tmp;drop");
        ventaServiceJPA.realizarVenta(cliente.getId(), this.productos, visa.getId());
        List<Venta> ventasPersistidas = ventaServiceJPA.ventas();

        assertTrue(!ventasPersistidas.isEmpty());
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            bloqueDeCodigo.accept(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
