package com.es.diecines.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sesiones")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Pelicula pelicula;

    @Column(name = "room_id")
    private Long roomId;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    public Sesion(){}

    public Sesion(Pelicula pelicula, Long roomId, LocalDate date) {
        this.pelicula = pelicula;
        this.roomId = roomId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
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
