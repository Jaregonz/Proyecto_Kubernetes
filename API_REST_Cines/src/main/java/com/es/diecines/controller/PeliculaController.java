package com.es.diecines.controller;

import com.es.diecines.dto.PeliculaDTO;
import com.es.diecines.exceptions.BadRequestException;
import com.es.diecines.exceptions.NotFoundException;
import com.es.diecines.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    /**
     * Crear una pelicula
     * El método para insertar es un POST -> @PostMapping
     * El recurso para este método es localhost:8080/peliculas/
     * /peliculas lo tenemos en el @RequestMapping arriba
     * / en el método sólo ponemos la barra
     * <p>
     * La información de la nueva Pelicula viene en el cuerpo de la petición
     * Para obtener el cuerpo de la petición -> @RequestBody
     *
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<PeliculaDTO> insert(@RequestBody PeliculaDTO pDto) {

        // 1º Comprobar que pDto no es null
        if(pDto == null) {
            throw new BadRequestException("El cuerpo de la petición no puede estar vacío");
        }

        // 2º LLamar al service, que es donde se hace la logica de negocio
        // y por donde se accede a la BDD
        PeliculaDTO p = peliculaService.insert(pDto);

        // 3º Comprobar que lo que devuelve el service es null o no
        // y responder
        if(p == null) {
            throw new BadRequestException("No se ha podido insertar en base de datos");
        } else {
            ResponseEntity<PeliculaDTO> respuesta = new ResponseEntity<PeliculaDTO>(p, HttpStatus.CREATED);
            return respuesta;
        }
    }

    /**
     * Consultar todas las peliculas
     *
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<PeliculaDTO>> getAll() {
        // 1 Llamo al Service
        List<PeliculaDTO> p = peliculaService.getAll();

        // 2 Compruebo la validez de p para devolver una respuesta
        ResponseEntity<List<PeliculaDTO>> respuesta;
        if (p == null || p.isEmpty()) {
            respuesta = new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si viene vacío, devuelvo un no_content
        } else {
            respuesta = new ResponseEntity<>(p, HttpStatus.OK);
        }

        return respuesta;
    }

    /**
     * Consultar una pelicula por su ID
     *
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        // 1 Comprobar que el id no viene vacío
        if (id == null || id.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // 2 Si no viene vacio, llamo al Service
        PeliculaDTO p = peliculaService.getById(id);

        // 3 Compruebo la validez de p para devolver una respuesta
        if (p == null) {
            throw new NotFoundException("Pelicula con id " + id + " no encontrada");
        } else {
            ResponseEntity<PeliculaDTO> respuesta = new ResponseEntity<PeliculaDTO>(p, HttpStatus.OK);
            return respuesta;
        }
    }
}
