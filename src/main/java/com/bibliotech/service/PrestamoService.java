package main.java.com.bibliotech.service;

import main.java.com.bibliotech.exception.*;
import main.java.com.bibliotech.model.Prestamo;
import main.java.com.bibliotech.model.Socio;
import main.java.com.bibliotech.repository.PrestamoRepository;
import main.java.com.bibliotech.repository.RecursoRepository;
import main.java.com.bibliotech.repository.SocioRepository;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class PrestamoService {

    private final PrestamoRepository prestamoRepo;
    private final RecursoRepository recursoRepo;
    private final SocioRepository socioRepo;

    public PrestamoService(PrestamoRepository prestamoRepo, RecursoRepository recursoRepo, SocioRepository socioRepo) {
        this.prestamoRepo = prestamoRepo;
        this.recursoRepo = recursoRepo;
        this.socioRepo = socioRepo;
    }

    public void realizarPrestamo(String isbn, String dni) {
        // 1. Validar que el recurso exista
        recursoRepo.buscarPorId(isbn)
                .orElseThrow(() -> new EntidadNoEncontradaException("Recurso no encontrado con ISBN: " + isbn));

        // 2. Validar que el socio exista (Aprovechamos el polimorfismo para tener el límite)
        Socio socio = socioRepo.buscarPorId(dni)
                .orElseThrow(() -> new EntidadNoEncontradaException("Socio no encontrado con DNI: " + dni));

        // 3. Verificar disponibilidad del recurso
        if (!prestamoRepo.estaDisponible(isbn)) {
            throw new RecursoNoDisponibleException(isbn);
        }

        // 4. Verificar el límite polimórfico del socio (3 para Estudiante, 5 para Docente)
        long activos = prestamoRepo.contarPrestamosActivos(dni);
        if (activos >= socio.limitePrestamos()) {
            throw new LimitePrestamosExcedidoException(dni, socio.limitePrestamos());
        }

        // 5. Registrar
        Prestamo nuevoPrestamo = new Prestamo(dni, isbn);
        prestamoRepo.guardar(nuevoPrestamo);
    }
    public long procesarDevolucion(String prestamoId) {
        // 1. Buscar el préstamo
        Prestamo prestamo = prestamoRepo.buscarPorId(prestamoId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Préstamo no encontrado con ID: " + prestamoId));

        // 2. Validar que no esté devuelto ya
        if (prestamo.devuelto()) {
            throw new PrestamoYaDevueltoException(prestamoId);
        }

        // 3. Registrar fecha y generar préstamo actualizado
        LocalDate fechaActual = LocalDate.now();
        Prestamo prestamoActualizado = prestamo.registrarDevolucion(fechaActual);

        // 4. Sobrescribir en el repositorio
        prestamoRepo.guardar(prestamoActualizado);

        // 5. Cálculo automático de días de retraso usando Java Time API
        long diasRetraso = ChronoUnit.DAYS.between(prestamo.fechaDevolucionEsperada(), fechaActual);

        // Si devolvió a tiempo (negativo) o el mismo día (0), retornamos 0 retraso
        return diasRetraso > 0 ? diasRetraso : 0;
    }
}