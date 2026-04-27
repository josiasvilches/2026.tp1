package main.java.com.bibliotech.repository.impl;

import main.java.com.bibliotech.model.Prestamo;
import main.java.com.bibliotech.repository.PrestamoRepository;
import java.util.*;

public class PrestamoRepositoryInMemory implements PrestamoRepository {
    private final Map<String, Prestamo> storage = new HashMap<>();

    @Override
    public void guardar(Prestamo entidad) {
        storage.put(entidad.id(), entidad);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public long contarPrestamosActivos(String socioDni) {
        return storage.values().stream()
                .filter(p -> p.socioDni().equals(socioDni) && !p.devuelto())
                .count();
    }

    @Override
    public boolean estaDisponible(String recursoIsbn) {
        // Está disponible si NO hay ninguna coincidencia de un préstamo de ese ISBN que no esté devuelto
        return storage.values().stream()
                .noneMatch(p -> p.recursoIsbn().equals(recursoIsbn) && !p.devuelto());
    }
}