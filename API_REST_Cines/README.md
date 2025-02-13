
# Actividad: Implementación de un CRUD para "Películas" y "Sesiones" con uso de DTO

## Objetivo:
Desarrollar una API REST en Spring Boot que permita realizar operaciones CRUD sobre las entidades **Películas** y **Sesiones**. Los estudiantes deben aprender a estructurar y organizar el código siguiendo las buenas prácticas, utilizando DTOs para la transferencia de datos.

---

## 1. Requerimientos

1. **Endpoints para Películas**:
    - Crear una película.
    - Consultar todas las películas.
    - Consultar una película por su ID.
    - Modificar una película por su ID.
    - Eliminar una película por su ID.
    - Consultar las peliculas con una nota mayor a una dada

2. **Endpoints para Sesiones**:
    - Crear una sesión.
    - Consultar todas las sesiones.
    - Modificar una sesión por su ID.
    - Eliminar una sesión por su ID.
    - Consultar las sesiones del día de hoy.

3. **Práctica con DTOs**:
    - Crear clases DTO para **Películas** y **Sesiones**.
    - Mapear las entidades hacia DTOs y viceversa en los controladores.

4. **Persistencia**:
    - Usar una base de datos **MySQL**.
    - Configurar JPA/Hibernate para manejar las operaciones con la base de datos.

---

## 2. Guía de Desarrollo

### 2.1. Configuración del Proyecto
1. Crear un proyecto en **Spring Boot** con las dependencias:
    - Spring Web.
    - Spring Data JPA.
    - MySQL Driver.
    - Lombok (opcional, para simplificar getters/setters).

    2. Configurar el archivo `application.properties`:
        ```properties
        # Configuracion para el acceso a la Base de Datos
       spring.jpa.hibernate.ddl-auto=create
       spring.jpa.properties.hibernate.globally_quoted_identifiers=true
       spring.jpa.show-sql=true
    
       # Puerto donde escucha el servidor una vez se inicie
       server.port=8080
    
       # Datos de conexion con la base de datos MySQL
       spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
       spring.datasource.url=jdbc:mysql://localhost:3306/cines_bd
       spring.datasource.username=root
       spring.datasource.password=
    
       # Para popular la BBDD
       spring.jpa.properties.javax.persistence.sql-load-script-source=sql/datos-prueba.sql
        ```

---

### 2.2. Modelado de Entidades

**Películas**:
```java
@Entity
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String director;

    private String time;

    private String trailer;

    private String posterImage;

    private String screenshot;

    @Column(length = 1000)
    private String synopsis;

    @Column(nullable = false)
    private Double rating;

    // Getters y setters
}
```

**Sesiones**:
```java
@Entity
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Pelicula pelicula;

    private Long roomId;

    private LocalDate date;
}
```

---

### 2.3. Crear Repositorios
```java
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {}
public interface SesionRepository extends JpaRepository<Sesion, Long> {}
```

---

### 2.4. Crear DTOs
**DTO para Película**:
```java
public class PeliculaDTO {
    private Long id;
    private String title;
    private String director;
    private String time;
    private String trailer;
    private String posterImage;
    private String screenshot;
    private String synopsis;
    private Double rating;
}
```

**DTO para Sesión**:
```java
public class SesionDTO {
    private Long id;
    private Long movieId;
    private Long roomId;
    private LocalDate date;
}
```

---

### 2.5. Crear Servicios
Implementar servicios que manejan la lógica de negocio, incluyendo la conversión entre entidades y DTOs.

```java
@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public PeliculaDTO createPelicula(PeliculaDTO peliculaDTO) {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitle(peliculaDTO.getTitle());
        pelicula.setDirector(peliculaDTO.getDirector());
        pelicula.setTime(peliculaDTO.getTime());
        pelicula.setTrailer(peliculaDTO.getTrailer());
        pelicula.setPosterImage(peliculaDTO.getPosterImage());
        pelicula.setScreenshot(peliculaDTO.getScreenshot());
        pelicula.setSynopsis(peliculaDTO.getSynopsis());
        pelicula.setRating(peliculaDTO.getRating());
        pelicula = peliculaRepository.save(pelicula);

        return mapToDTO(pelicula);
    }

    private PeliculaDTO mapToDTO(Pelicula pelicula) {
        PeliculaDTO peliculaDTO = new PeliculaDTO();
        peliculaDTO.setId(pelicula.getId());
        peliculaDTO.setTitle(pelicula.getTitle());
        peliculaDTO.setDirector(pelicula.getDirector());
        peliculaDTO.setTime(pelicula.getTime());
        peliculaDTO.setTrailer(pelicula.getTrailer());
        peliculaDTO.setPosterImage(pelicula.getPosterImage());
        peliculaDTO.setScreenshot(pelicula.getScreenshot());
        peliculaDTO.setSynopsis(pelicula.getSynopsis());
        peliculaDTO.setRating(pelicula.getRating());
        return peliculaDTO;
    }
}
```

---

### 2.6. Crear Controladores
```java
@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @PostMapping
    public ResponseEntity<PeliculaDTO> createPelicula(@RequestBody PeliculaDTO peliculaDTO) {
        return ResponseEntity.ok(peliculaService.createPelicula(peliculaDTO));
    }

    // Implementar otros endpoints...
}
```


## 3. Endpoints para Películas y Sesiones

### 3.1. Endpoints para Películas

| Operación                                   | Método HTTP | Endpoint                           | Descripción                                          |
|--------------------------------------------|-------------|------------------------------------|-----------------------------------------------------|
| Crear una película                         | `POST`      | `/peliculas`                      | Recibe los datos de una película en el cuerpo de la solicitud y la guarda. |
| Consultar todas las películas              | `GET`       | `/peliculas`                      | Devuelve la lista completa de películas.           |
| Consultar una película por su ID           | `GET`       | `/peliculas/{id}`                 | Devuelve los detalles de una película específica por su ID. |
| Modificar una película por su ID           | `PUT`       | `/peliculas/{id}`                 | Recibe datos actualizados en el cuerpo y modifica la película con el ID especificado. |
| Eliminar una película por su ID            | `DELETE`    | `/peliculas/{id}`                 | Elimina la película con el ID especificado.        |
| Consultar películas con una nota mayor a X | `GET`       | `/peliculas/rating/{minRating}`   | Devuelve todas las películas con una puntuación mayor a un valor dado (`minRating`). |

---

### 3.2. Endpoints para Sesiones

| Operación                                   | Método HTTP | Endpoint                           | Descripción                                          |
|--------------------------------------------|-------------|------------------------------------|-----------------------------------------------------|
| Crear una sesión                           | `POST`      | `/sesiones`                       | Recibe los datos de una sesión en el cuerpo de la solicitud y la guarda. |
| Consultar todas las sesiones               | `GET`       | `/sesiones`                       | Devuelve la lista completa de sesiones.            |
| Modificar una sesión por su ID             | `PUT`       | `/sesiones/{id}`                  | Recibe datos actualizados en el cuerpo y modifica la sesión con el ID especificado. |
| Eliminar una sesión por su ID              | `DELETE`    | `/sesiones/{id}`                  | Elimina la sesión con el ID especificado.          |
| Consultar las sesiones del día de hoy      | `GET`       | `/sesiones/hoy`                   | Devuelve todas las sesiones programadas para el día actual. |

---

## Notas Adicionales

1. **Consulta por nota en películas**:  
   El endpoint `/peliculas/rating/{minRating}` debe incluir la lógica para validar que el `minRating` sea un número válido.

2. **Consulta de sesiones del día de hoy**:  
   El endpoint `/sesiones/hoy` usa la fecha actual del sistema para filtrar las sesiones. La lógica puede depender de `LocalDate.now()`.

3. **Pruebas**:  
   Usa herramientas como **Postman** o **Swagger** para verificar que estos endpoints cumplen con la funcionalidad esperada.


---

## 4. Actividades para los Estudiantes

1. **Configurar Proyecto:**
    - Crear el proyecto con Spring Boot y configurar las dependencias necesarias.

2. **Modelar las Entidades:**
    - Crear las clases de las entidades basándose en los JSON proporcionados.

3. **Implementar CRUD:**
    - Crear los servicios y controladores para manejar las operaciones CRUD.

4. **Usar DTOs:**
    - Crear clases DTO y mapear las entidades hacia DTOs y viceversa en los servicios.

5. **Pruebas:**
    - Probar los endpoints con Postman o Swagger.
    - Validar las respuestas y asegurarse de que se usan correctamente los DTOs.

6. **Extras (Opcional):**
    - Agregar validaciones a los DTOs usando anotaciones como `@NotNull`, `@Size`, etc.
    - Documentar la API usando Swagger.

---

## 4. Criterios de Evaluación

1. Correcta implementación de los endpoints.
2. Uso apropiado de DTOs para transferencia de datos.
3. Código limpio y organizado.
4. Pruebas exitosas de los endpoints.
5. Uso correcto de relaciones entre entidades (Película-Sesión).
6. Documentación y comentarios en el código.

---

Con estas actualizaciones, los estudiantes podrán trabajar con el nuevo campo `rating`, explorar cómo mapear entidades y DTOs, y probar la persistencia en MySQL.
