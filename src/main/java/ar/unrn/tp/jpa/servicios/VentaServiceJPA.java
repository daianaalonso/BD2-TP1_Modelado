package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class VentaServiceJPA implements VentaService {

    private String servicio;
    public VentaServiceJPA(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.servicio);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Carrito carrito = new Carrito();
        try {
            tx.begin();

            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null)
                throw new RuntimeException("El cliente no existe.");

            Tarjeta tarjeta = em.find(Tarjeta.class, idTarjeta);
            if (tarjeta == null)
                throw new RuntimeException("La tarjeta no existe.");
            if (!cliente.miTarjeta(tarjeta))
                throw new RuntimeException("La tarjeta no es del cliente.");

            TypedQuery<Producto> q = em.createQuery("SELECT p FROM Producto p WHERE p.id IN :productos", Producto.class);
            q.setParameter("productos", productos);
            List<Producto> productosVendidos = q.getResultList();
            if (productosVendidos.isEmpty())
                throw new RuntimeException("Error. La lista de productos esta vacia.");
            carrito.agregarProductosAlCarrito(productosVendidos);

            TypedQuery<PagoPromocion> qp = em.createQuery("SELECT p FROM PagoPromocion p WHERE p.fechaInicio < :fecha and p.fechaFin > :fecha", PagoPromocion.class);
            qp.setParameter("fecha", LocalDate.now());
            PagoPromocion pagoPromocion = qp.getSingleResult();

            TypedQuery<MarcaPromocion> qm = em.createQuery("SELECT m FROM MarcaPromocion m", MarcaPromocion.class);
            List<MarcaPromocion> marcaPromociones = qm.getResultList();

            Venta v = carrito.pagar(marcaPromociones, pagoPromocion, cliente, tarjeta);
            em.persist(v);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public Double calcularMonto(List<Long> productos, Long idTarjeta) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.servicio);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Carrito carrito = new Carrito();
        try {
            tx.begin();
            Tarjeta tarjeta = em.find(Tarjeta.class, idTarjeta);
            if (tarjeta == null)
                throw new RuntimeException("La tarjeta no existe.");

            TypedQuery<Producto> q = em.createQuery("SELECT p FROM Producto p WHERE p.id IN :productos", Producto.class);
            q.setParameter("productos", productos);
            List<Producto> productosSeleccionados = q.getResultList();
            if (productosSeleccionados.isEmpty())
                throw new RuntimeException("Error. La lista de productos esta vacia.");
            carrito.agregarProductosAlCarrito(productosSeleccionados);

            TypedQuery<PagoPromocion> qp = em.createQuery("SELECT p FROM PagoPromocion p WHERE p.fechaInicio < :fecha and p.fechaFin > :fecha", PagoPromocion.class);
            qp.setParameter("fecha", LocalDate.now());
            PagoPromocion pagoPromocion = qp.getSingleResult();

            TypedQuery<MarcaPromocion> qm = em.createQuery("SELECT m FROM MarcaPromocion m WHERE m.fechaInicio < :fecha and m.fechaFin > :fecha", MarcaPromocion.class);
            qm.setParameter("fecha", LocalDate.now());
            List<MarcaPromocion> marcaPromociones = qm.getResultList();

            tx.commit();
            return carrito.calcularMontoCarrito(marcaPromociones, pagoPromocion, tarjeta);
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public List ventas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.servicio);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Venta> q = em.createQuery("SELECT v FROM Venta v", Venta.class);
            List<Venta> ventas = q.getResultList();
            tx.commit();
            return ventas;
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}