package com.es.diecines.utils;

import com.es.diecines.dto.PeliculaDTO;
import com.es.diecines.dto.SesionDTO;
import com.es.diecines.model.Pelicula;
import com.es.diecines.model.Sesion;

public class Mapper {

    public static PeliculaDTO entityToDTO(Pelicula p) {
        PeliculaDTO pDto = new PeliculaDTO();
        pDto.setDirector(p.getDirector());
        pDto.setId(p.getId());
        pDto.setRating(p.getRating());
        pDto.setSynopsis(p.getSynopsis());
        pDto.setTime(p.getTime());
        pDto.setScreenshot(p.getScreenshot());
        pDto.setPosterImage(p.getPosterImage());
        pDto.setTrailer(p.getTrailer());
        pDto.setTitle(p.getTitle());
        return pDto;
    }

    public static Pelicula DtoToEntity(PeliculaDTO pDto) {
        Pelicula p = new Pelicula();
        p.setDirector(  pDto.getDirector());
        p.setId(        pDto.getId());
        p.setRating(    pDto.getRating());
        p.setSynopsis(  pDto.getSynopsis());
        p.setTime(      pDto.getTime());
        p.setScreenshot(pDto.getScreenshot());
        p.setPosterImage(pDto.getPosterImage());
        p.setTrailer(   pDto.getTrailer());
        p.setTitle(     pDto.getTitle());
        return p;
    }

    public static SesionDTO entityToDTO(Sesion s) {
        SesionDTO sDto = new SesionDTO();
        sDto.setId(s.getId());
        sDto.setMovieId(s.getPelicula().getId());
        sDto.setRoomId(s.getRoomId());
        sDto.setDate(s.getDate());
        return sDto;
    }

    public static Sesion DtoToEntity(SesionDTO sDto, Pelicula p) {
        Sesion s = new Sesion();
        s.setId(     sDto.getId());
        s.setPelicula(p);
        s.setRoomId( sDto.getRoomId());
        s.setDate(   sDto.getDate());
        return s;
    }
}
