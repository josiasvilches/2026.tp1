package main.java.com.bibliotech.model;

// Implementamos Recurso usando Records inmutables
public record LibroFisico(
        String isbn,
        String titulo,
        String autor,
        int anio,
        Categoria categoria,
        String ubicacionFisica // Ej: "Pasillo A, Estante 3"
) implements Recurso {}