package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private List<Tarjeta> tarjetas;

    public Cliente(String nombre, String apellido, String dni, String email) {
        if (nombre == null || nombre.isEmpty())
            throw new RuntimeException("El nombre debe ser valido");
        this.nombre = nombre;

        if (apellido == null || apellido.isEmpty())
            throw new RuntimeException("El apellido debe ser valido");
        this.apellido = apellido;

        if (dni == null || nombre.isEmpty())
            throw new RuntimeException("El DNI debe ser valido");
        this.dni = dni;

        if (!validarEmail(email))
            throw new RuntimeException("El email debe ser valido");
        this.email = email;

        this.tarjetas = new ArrayList<>();
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validarEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void agregarTarjeta(Tarjeta tarjeta) {
        this.tarjetas.add(tarjeta);
    }
}
