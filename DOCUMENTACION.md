# Documentación Técnica - Proyecto BiblioTech
## Sistema de Gestión de Biblioteca

---
### 1. Introducción
BiblioTech es un sistema diseñado para modernizar la gestión de recursos y usuarios de una biblioteca universitaria. El desarrollo se ha centrado en la creación de un núcleo (core) robusto, escalable y mantenible, aplicando estándares de la industria y principios de diseño orientado a objetos.

---

### 2. Arquitectura del Sistema
El proyecto sigue una arquitectura multicapa para garantizar la separación de responsabilidades:

* **`model`**: Contiene las definiciones de datos inmutables utilizando **Java Records** e interfaces para el comportamiento polimórfico.
* **`repository`**: Capa de persistencia. Define interfaces genéricas e implementaciones en memoria (`In-Memory`) que desacoplan el almacenamiento de la lógica de negocio.
* **`service`**: Orquestador de la lógica de negocio. Aquí se aplican las reglas de validación, cálculos de retraso y gestión de estados.
* **`exception`**: Jerarquía de excepciones personalizadas que heredan de `RuntimeException`, permitiendo un control de errores semántico y profesional.
* **`main`**: Punto de entrada de la aplicación que realiza la composición del sistema mediante la inyección de dependencias.

---

### 3. Aplicación de Principios SOLID

| Principio | Aplicación en BiblioTech |
| :--- | :--- |
| **Single Responsibility (SRP)** | Cada clase tiene un único propósito. Los repositorios solo persisten datos, los servicios solo ejecutan lógica y los records solo transportan datos. |
| **Open/Closed (OCP)** | El sistema es extensible para nuevos tipos de recursos (ej: Revistas) o socios sin modificar la lógica de los servicios, gracias al uso de interfaces como `Recurso` y `Socio`. |
| **Liskov Substitution (LSP)** | `Estudiante` y `Docente` pueden ser tratados como `Socio` indistintamente en el repositorio y servicio, respetando el contrato de la interfaz. |
| **Interface Segregation (ISP)** | Se definió una interfaz `Repository<T, ID>` genérica, y luego interfaces específicas como `RecursoRepository` que solo exponen los métodos necesarios para su contexto. |
| **Dependency Inversion (DIP)** | Los servicios dependen de abstracciones (`Interfaces`) y no de implementaciones concretas. Las dependencias se inyectan a través del constructor. |

---

### 4. Patrones de Diseño Implementados

* **Repository Pattern**: Utilizado para abstraer la lógica de almacenamiento. Esto permitiría, en el futuro, cambiar la persistencia de memoria a una base de datos real sin afectar la capa de servicios.
* **Dependency Injection (DI)**: Implementada manualmente en la clase `Main`. Esto facilita las pruebas unitarias y el desacoplamiento entre componentes.
* **Strategy / Polymorphism**: La lógica de límites de préstamos y tipos de recursos se gestiona mediante polimorfismo, permitiendo que el sistema decida en tiempo de ejecución qué comportamiento aplicar según el tipo de objeto.

---

### 5. Características de Java Moderno

* **Records**: Se utilizaron para definir `LibroFisico`, `EBook`, `Estudiante`, `Docente` y `Prestamo`. Garantizan inmutabilidad y reducen el código repetitivo (*boilerplate*).
* **Optional<T>**: Implementado en los retornos de búsqueda de los repositorios para evitar el uso de `null` y prevenir errores de `NullPointerException`.
* **Streams API**: Utilizada extensivamente en los repositorios para realizar filtrados, búsquedas y conteos de forma declarativa y eficiente.
* **Switch Expressions**: Empleadas en el `RecursoService` para gestionar los criterios de búsqueda de manera limpia y exhaustiva.

---

### 6. Manejo de Errores y Excepciones
Se implementó una jerarquía clara para manejar situaciones anómalas:
* `ValidacionException`: Para datos de entrada incorrectos (Email, DNI).
* `EntidadNoEncontradaException`: Cuando se intenta operar sobre recursos o socios inexistentes.
* `RecursoNoDisponibleException` / `LimitePrestamosExcedidoException`: Para validar reglas de negocio durante el ciclo de préstamo.

---
### 7. Interfaz de Usuario (CLI)
El sistema incluye un menú interactivo que permite realizar las siguientes operaciones en tiempo real:
1.  **Registro de Recursos**: Soporte para Libros Físicos (con ubicación) y E-Books (con formato y tamaño).
2.  **Gestión de Socios**: Registro con validación de DNI y Email, discriminando entre Estudiantes y Docentes.
3.  **Ciclo de Préstamos**: Validación de disponibilidad y límites polimórficos de cada socio.
4.  **Devoluciones**: Cálculo automático de días de retraso basado en la fecha esperada.

---

### 8. Estructura de Paquetes
```text
main/java/com/bibliotech/
├── model/          # Categoria, Recurso, LibroFisico, EBook, Socio, Estudiante, Docente, Prestamo
├── repository/     # Repository (Base), RecursoRepository, SocioRepository, PrestamoRepository
│   └── impl/       # Implementaciones InMemory
├── service/        # RecursoService, SocioService, PrestamoService
├── exception/      # Jerarquía de excepciones personalizadas
└── Main.java       # Orquestador y punto de entrada
```
