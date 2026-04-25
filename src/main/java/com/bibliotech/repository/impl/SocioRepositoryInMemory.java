package main.java.com.bibliotech.repository.impl;

import main.java.com.bibliotech.model.Socio;
import main.java.com.bibliotech.repository.SocioRepository;
import java.util.*;

public class SocioRepositoryInMemory implements SocioRepository {
    private final Map<String, Socio> storage = new HashMap<>();

    @Override
    public void guardar(Socio entidad) {
        storage.put(entidad.dni(), entidad);
    }

    @Override
    public Optional<Socio> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(storage.values());
    }
}