package main.java.com.bibliotech.model;

import java.time.LocalDate;
import java.util.UUID;

public record Prestamo(
        String id,
        String socioDni,
        String recursoIsbn,
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucionEsperada,
        LocalDate fechaDevolucionReal,
        boolean devuelto
) {
    // Constructor auxiliar para préstamos nuevos (Asignamos 14 días de plazo estándar)
    public Prestamo(String socioDni, String recursoIsbn) {
        this(UUID.randomUUID().toString(), socioDni, recursoIsbn, LocalDate.now(), LocalDate.now().plusDays(14), null, false);
    }

    // Método para mantener la inmutabilidad: retorna un nuevo record con el estado final
    public Prestamo registrarDevolucion(LocalDate fechaReal) {
        return new Prestamo(id, socioDni, recursoIsbn, fechaPrestamo, fechaDevolucionEsperada, fechaReal, true);
    }
}