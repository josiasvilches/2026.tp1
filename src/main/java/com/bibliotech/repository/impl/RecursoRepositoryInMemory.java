package main.java.com.bibliotech.repository.impl;
import main.java.com.bibliotech.model.Recurso;
import main.java.com.bibliotech.model.Categoria;
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
    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return storage.values().stream()
                .filter(r -> r.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return storage.values().stream()
                .filter(r -> r.autor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return storage.values().stream()
                .filter(r -> r.categoria() == categoria)
                .toList();
    }
}