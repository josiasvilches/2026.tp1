package main.java.com.bibliotech.repository;

import main.java.com.bibliotech.model.Prestamo;

public interface PrestamoRepository extends Repository<Prestamo, String> {
    long contarPrestamosActivos(String socioDni);
    boolean estaDisponible(String recursoIsbn);
}