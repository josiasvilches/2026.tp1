package main.java.com.bibliotech.model;

import main.java.com.bibliotech.exception.ValidacionException;
import java.util.regex.Pattern;

public interface Socio {
    String dni();
    String nombre();
    String apellido();
    String email();

    // Método polimórfico clave para el Issue 5
    int limitePrestamos();

    // Lógica de validación centralizada (DRY - Don't Repeat Yourself)
    Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    static void validarDatosBase(String dni, String email) {
        if (dni == null || dni.isBlank()) {
            throw new ValidacionException("El DNI no puede estar vacío.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidacionException("El formato del email no es válido: " + email);
        }
    }
}