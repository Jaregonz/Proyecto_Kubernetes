package com.es.diecines.dto;

import java.time.LocalDate;

public class SesionDTO {
    private Long id;
    private Long movieId;
    private Long roomId;
    private LocalDate date;

    public SesionDTO(){}
    public SesionDTO(Long id, Long movieId, Long roomId, LocalDate date) {
        this.id = id;
        this.movieId = movieId;
        this.roomId = roomId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
