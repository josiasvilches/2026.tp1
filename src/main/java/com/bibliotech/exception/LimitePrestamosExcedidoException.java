package main.java.com.bibliotech.exception;

public class LimitePrestamosExcedidoException extends RuntimeException {
    public LimitePrestamosExcedidoException(String dni, int limite) {
        super("El socio con DNI " + dni + " ha excedido su límite máximo de " + limite + " préstamos activos.");
    }
}