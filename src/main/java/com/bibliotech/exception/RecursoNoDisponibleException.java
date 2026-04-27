package main.java.com.bibliotech.exception;

public class RecursoNoDisponibleException extends RuntimeException {
    public RecursoNoDisponibleException(String isbn) {
        super("El recurso con ISBN " + isbn + " ya se encuentra prestado y no está disponible.");
    }
}