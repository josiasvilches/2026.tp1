package main.java.com.bibliotech.repository.impl;
import main.java.com.bibliotech.model.Recurso;
import main.java.com.bibliotech.repository.RecursoRepository;
import java.util.*;

public class RecursoRepositoryInMemory implements RecursoRepository {
    private final Map<String, Recurso> storage = new HashMap<>();

    @Override
    public void guardar(Recurso entidad) {
        storage.put(entidad.isbn(), entidad);
    }

    @Override
    public Optional<Recurso> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(storage.values());
    }
}