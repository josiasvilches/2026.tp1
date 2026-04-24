package main.java.com.bibliotech.service;
import main.java.com.bibliotech.model.Categoria;
import main.java.com.bibliotech.model.Recurso;
import main.java.com.bibliotech.repository.RecursoRepository;
import java.util.List;

public class RecursoService {

    private final RecursoRepository recursoRepo;

    // Inyección de dependencias: dependemos de la abstracción (interfaz)
    public RecursoService(RecursoRepository recursoRepo) {
        this.recursoRepo = recursoRepo;
    }

    // Método centralizado de búsqueda avanzada
    public List<Recurso> buscarAvanzada(String criterio, String valor) {
        return switch (criterio.toLowerCase()) {
            case "titulo" -> recursoRepo.buscarPorTitulo(valor);
            case "autor" -> recursoRepo.buscarPorAutor(valor);
            case "categoria" -> {
                try {
                    yield recursoRepo.buscarPorCategoria(Categoria.valueOf(valor.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    yield List.of(); // Si envían una categoría que no existe en el Enum, devolvemos lista vacía
                }
            }
            default -> throw new IllegalArgumentException("Criterio de búsqueda no soportado: " + criterio);
        };
    }
}