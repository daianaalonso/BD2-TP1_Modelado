package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import org.junit.Test;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceTest {

    @Test
    public void persistirCliente() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente cliente = em.find(Cliente.class, 1L);
            assertTrue(cliente.suNombreEs("Daiana"));
            assertTrue(cliente.suApellidoEs("Alonso"));
            assertTrue(cliente.suDniEs("42448077"));
            assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Test
    public void modificarClientePersistido() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        clienteServiceJPA.modificarCliente(1L, "Dai", "Ramos", "42448078", "dramos@gmail.com");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente cliente = em.find(Cliente.class, 1L);
            assertTrue(cliente.suNombreEs("Dai"));
            assertTrue(cliente.suApellidoEs("Ramos"));
            assertTrue(cliente.suDniEs("42448078"));
            assertTrue(cliente.suEmailEs("dramos@gmail.com"));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Test
    public void agregarTarjetaAClientePersistido() {
        String nombreTarjeta = "VISA";
        String nroTarjeta = "123456789";
        Tarjeta tarjetaVisa = new Tarjeta(nombreTarjeta, Integer.parseInt(nroTarjeta));

        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        clienteServiceJPA.agregarTarjeta(1L, nroTarjeta, nombreTarjeta);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente cliente = em.find(Cliente.class, 1L);
            assertTrue(cliente.miTarjeta(tarjetaVisa));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

}
