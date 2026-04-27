package main.java.com.bibliotech;

import main.java.com.bibliotech.model.*;
import main.java.com.bibliotech.repository.impl.*;
import main.java.com.bibliotech.service.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicialización de Repositorios (In-memory)
        var recursoRepo = new RecursoRepositoryInMemory();
        var socioRepo = new SocioRepositoryInMemory();
        var prestamoRepo = new PrestamoRepositoryInMemory();

        // 2. Inicialización de Servicios con Inyección de Dependencias
        var recursoService = new RecursoService(recursoRepo);
        var socioService = new SocioService(socioRepo);
        var prestamoService = new PrestamoService(prestamoRepo, recursoRepo, socioRepo);

        System.out.println("=== BiblioTech: Sistema de Gestión ===\n");

        // 3. Test de Carga de Datos
        try {
            // Registrar Libros (Issue 1 y 2)
            recursoService.registrarRecurso(new LibroFisico("123", "Limpieza de Sangre", "Arturo Perez-Reverte", 1996, Categoria.LITERATURA, "Pasillo A1"));
            recursoService.registrarRecurso(new EBook("456", "Clean Code", "Robert C. Martin", 2008, Categoria.TECNOLOGIA, "PDF", 2.5));

            // Registrar Socios (Issue 4 y 5)
            socioService.registrarSocio(new Estudiante("30123456", "Josias", "Vilches", "josias@um.edu.ar"));
            socioService.registrarSocio(new Docente("20123456", "Silvina", "Moyano", "smoyano@um.edu.ar"));

            // 4. Test de Préstamos (Issue 6)
            System.out.println("Registrando préstamos...");
            prestamoService.realizarPrestamo("123", "30123456");
            prestamoService.realizarPrestamo("456", "20123456");

            // 5. Test de Devoluciones y Retraso (Issue 7)
            System.out.println("Procesando devolución...");
            // Obtenemos el ID del primer préstamo para probar la devolución
            String idPrestamo = prestamoService.obtenerHistorial().get(0).id();
            long retraso = prestamoService.procesarDevolucion(idPrestamo);
            System.out.println("Libro devuelto. Días de retraso: " + retraso);

            // 6. Registro Histórico (Issue 8)
            System.out.println("\n=== HISTORIAL DE TRANSACCIONES ===");
            List<Prestamo> historial = prestamoService.obtenerHistorial();
            historial.forEach(p -> System.out.println(
                    "Socio: " + p.socioDni() + " | Libro: " + p.recursoIsbn() + " | Estado: " + (p.devuelto() ? "Devuelto" : "Activo")
            ));

        } catch (RuntimeException e) {
            System.err.println("Error en la operación: " + e.getMessage());
        }
    }
}