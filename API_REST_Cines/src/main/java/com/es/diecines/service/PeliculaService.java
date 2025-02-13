package com.es.diecines.service;

import com.es.diecines.dto.PeliculaDTO;
import com.es.diecines.exceptions.BadRequestException;
import com.es.diecines.exceptions.NotFoundException;
import com.es.diecines.model.Pelicula;
import com.es.diecines.repository.PeliculaRepository;
import com.es.diecines.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public PeliculaDTO getById(String id) {

        // 1 Aplicamos la logica de negocio
        // -> El id debe ser Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El formato del id: "+id+" es incorrecto");
        }

        // 2 obtenemos la Pelicula
        Pelicula p = peliculaRepository
                .findById(idL)
                .orElse(null);

        if(p == null) {
            throw new NotFoundException("id "+id+" no encontrado");
        } else {
            // 3 Convertir p (Pelicula) a PeliculaDTO
            return Mapper.entityToDTO(p);
        }
    }

    public List<PeliculaDTO> getAll() {

        List<Pelicula> pelis = peliculaRepository.findAll().stream().toList();
        List<PeliculaDTO> pelisDto = new ArrayList<>();

        for (Pelicula p : pelis) {

            pelisDto.add(Mapper.entityToDTO(p));

        }

        return pelisDto;

    }

    public PeliculaDTO insert(PeliculaDTO pDto) {

        if (pDto.getTitle() == null || pDto.getTitle().isEmpty()
                || pDto.getDirector() == null || pDto.getDirector().isEmpty()
                || pDto.getRating() < 0 || pDto.getRating() > 10
                || pDto.getSynopsis() == null || pDto.getSynopsis().length()>100) {
            throw new BadRequestException("Los campos del cuerpo no son correctos");
        }

        Pelicula p = peliculaRepository.save(Mapper.DtoToEntity(pDto));

        return Mapper.entityToDTO(p);

    }
}
