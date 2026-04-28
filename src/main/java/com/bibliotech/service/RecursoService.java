package main.java.com.bibliotech.service;

import main.java.com.bibliotech.exception.RecursoYaExisteException;
import main.java.com.bibliotech.model.Categoria;
import main.java.com.bibliotech.model.Recurso;
import main.java.com.bibliotech.repository.RecursoRepository;
import java.util.List;

public class RecursoService {

    private final RecursoRepository recursoRepo;

    public RecursoService(RecursoRepository recursoRepo) {
        this.recursoRepo = recursoRepo;
    }


    public void registrarRecurso(Recurso recurso) {
        if (recursoRepo.buscarPorId(recurso.isbn()).isPresent()) {
            throw new RecursoYaExisteException(recurso.isbn());
        }
        recursoRepo.guardar(recurso);
    }


    public List<Recurso> buscarAvanzada(String criterio, String valor) {
        return switch (criterio.toLowerCase()) {
            case "titulo" -> recursoRepo.buscarPorTitulo(valor);
            case "autor" -> recursoRepo.buscarPorAutor(valor);
            case "categoria" -> {
                try {
                    yield recursoRepo.buscarPorCategoria(Categoria.valueOf(valor.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    yield List.of();
                }
            }
            default -> throw new IllegalArgumentException("Criterio de búsqueda no soportado: " + criterio);
        };
    }
}