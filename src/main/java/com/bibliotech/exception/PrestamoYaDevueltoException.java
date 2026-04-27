package main.java.com.bibliotech.exception;

public class PrestamoYaDevueltoException extends RuntimeException {
    public PrestamoYaDevueltoException(String id) {
        super("El préstamo con ID " + id + " ya se encuentra registrado como devuelto.");
    }
}