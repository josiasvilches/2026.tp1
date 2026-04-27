package main.java.com.bibliotech.exception;

public class RecursoYaExisteException extends RuntimeException {
    public RecursoYaExisteException(String isbn) {
        super("Ya existe un recurso registrado en el sistema con el ISBN: " + isbn);
    }
}