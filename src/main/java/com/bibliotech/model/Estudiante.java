package main.java.com.bibliotech.model;

public record Estudiante(String dni, String nombre, String apellido, String email) implements Socio {

    // Compact constructor usando la validación de la interfaz
    public Estudiante {
        Socio.validarDatosBase(dni, email);
    }

    @Override
    public int limitePrestamos() {
        return 3;
    }
}