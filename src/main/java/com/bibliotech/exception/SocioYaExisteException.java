package main.java.com.bibliotech.exception;

public class SocioYaExisteException extends RuntimeException {
    public SocioYaExisteException(String dni) {
        super("Ya existe un socio registrado con el DNI: " + dni);
    }
}