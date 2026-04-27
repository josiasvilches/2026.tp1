package main.java.com.bibliotech.model;

import java.time.LocalDate;
import java.util.UUID;

public record Prestamo(String id, String socioDni, String recursoIsbn, LocalDate fechaPrestamo, boolean devuelto) {

    // Constructor auxiliar para crear préstamos nuevos fácilmente
    public Prestamo(String socioDni, String recursoIsbn) {
        this(UUID.randomUUID().toString(), socioDni, recursoIsbn, LocalDate.now(), false);
    }
}