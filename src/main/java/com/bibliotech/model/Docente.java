package main.java.com.bibliotech.model;

public record Docente(String dni, String nombre, String apellido, String email) implements Socio {

    // Compact constructor usando la validación de la interfaz
    public Docente {
        Socio.validarDatosBase(dni, email);
    }

    @Override
    public int limitePrestamos() {
        return 5;
    }
}