package main.java.com.bibliotech.model;

public record EBook(
        String isbn,
        String titulo,
        String autor,
        int anio,
        Categoria categoria,
        String formatoArchivo, // Ej: "PDF", "EPUB"
        double tamanoMb
) implements Recurso {}