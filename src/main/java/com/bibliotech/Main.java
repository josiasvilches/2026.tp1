package main.java.com.bibliotech;

import main.java.com.bibliotech.model.*;
import main.java.com.bibliotech.repository.impl.*;
import main.java.com.bibliotech.service.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static RecursoService recursoService;
    private static SocioService socioService;
    private static PrestamoService prestamoService;

    public static void main(String[] args) {
        var recursoRepo = new RecursoRepositoryInMemory();
        var socioRepo = new SocioRepositoryInMemory();
        var prestamoRepo = new PrestamoRepositoryInMemory();

        recursoService = new RecursoService(recursoRepo);
        socioService = new SocioService(socioRepo);
        prestamoService = new PrestamoService(prestamoRepo, recursoRepo, socioRepo);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarLibro();
                    case 2 -> registrarSocio();
                    case 3 -> buscarRecursos();
                    case 4 -> realizarPrestamo();
                    case 5 -> procesarDevolucion();
                    case 6 -> verHistorial();
                    case 0 -> salir = true;
                    default -> System.out.println("Opción no válida.");
                }
            } catch (RuntimeException e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== BIBLIOTECH CLI ===");
        System.out.println("1. Registrar Libro/E-Book");
        System.out.println("2. Registrar Socio (Estudiante/Docente)");
        System.out.println("3. Buscar Recursos");
        System.out.println("4. Realizar Préstamo");
        System.out.println("5. Procesar Devolución");
        System.out.println("6. Ver Historial de Transacciones");
        System.out.println("0. Salir");
    }

    private static void registrarLibro() {
        System.out.println("\n--- Registro de Recurso ---");
        String isbn = leerString("ISBN: ");
        String titulo = leerString("Título: ");
        String autor = leerString("Autor: ");
        int anio = leerEntero("Año: ");
        System.out.println("Categorías: CIENCIA, FICCION, TECNOLOGIA, HISTORIA, ARTE, LITERATURA");
        Categoria cat = Categoria.valueOf(leerString("Categoría: ").toUpperCase());

        int tipo = leerEntero("Tipo (1: Físico, 2: E-Book): ");
        if (tipo == 1) {
            String estante = leerString("Ubicación (Estante): ");
            recursoService.registrarRecurso(new LibroFisico(isbn, titulo, autor, anio, cat, estante));
        } else {
            String formato = leerString("Formato (PDF/EPUB): ");
            double mb = scanner.nextDouble(); scanner.nextLine();
            recursoService.registrarRecurso(new EBook(isbn, titulo, autor, anio, cat, formato, mb));
        }
        System.out.println("Recurso registrado con éxito.");
    }

    private static void registrarSocio() {
        System.out.println("\n--- Registro de Socio ---");
        String dni = leerString("DNI: ");
        String nombre = leerString("Nombre: ");
        String apellido = leerString("Apellido: ");
        String email = leerString("Email: ");

        int tipo = leerEntero("Tipo (1: Estudiante, 2: Docente): ");
        if (tipo == 1) {
            socioService.registrarSocio(new Estudiante(dni, nombre, apellido, email));
        } else {
            socioService.registrarSocio(new Docente(dni, nombre, apellido, email));
        }
        System.out.println("Socio registrado con éxito.");
    }

    private static void buscarRecursos() {
        String criterio = leerString("Criterio (titulo/autor/categoria): ");
        String valor = leerString("Valor a buscar: ");
        var resultados = recursoService.buscarAvanzada(criterio, valor);
        if (resultados.isEmpty()) System.out.println("No se encontraron resultados.");
        else resultados.forEach(System.out::println);
    }

    private static void realizarPrestamo() {
        String isbn = leerString("ISBN del Libro: ");
        String dni = leerString("DNI del Socio: ");
        prestamoService.realizarPrestamo(isbn, dni);
        System.out.println("Préstamo registrado exitosamente.");
    }

    private static void procesarDevolucion() {
        String id = leerString("ID del Préstamo: ");
        long retraso = prestamoService.procesarDevolucion(id);
        System.out.println("Devolución procesada. Días de retraso: " + retraso);
    }

    private static void verHistorial() {
        System.out.println("\n--- Historial de Transacciones ---");
        prestamoService.obtenerHistorial().forEach(System.out::println);
    }

    // Métodos auxiliares de lectura
    private static String leerString(String msj) {
        System.out.print(msj);
        return scanner.nextLine();
    }

    private static int leerEntero(String msj) {
        System.out.print(msj);
        int n = scanner.nextInt();
        scanner.nextLine();
        return n;
    }
}