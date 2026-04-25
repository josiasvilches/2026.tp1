package main.java.com.bibliotech.model;

import main.java.com.bibliotech.exception.ValidacionException;
import java.util.regex.Pattern;

public record Socio(String dni, String nombre, String apellido, String email) {

    // Regex básico para validar la estructura del correo
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Compact constructor para validación
    public Socio {
        if (dni == null || dni.isBlank()) {
            throw new ValidacionException("El DNI no puede estar vacío.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidacionException("El formato del email no es válido: " + email);
        }
    }
}