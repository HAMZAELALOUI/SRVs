package com.project.srv.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@JsonIgnoreProperties
public class Vol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idVol;
    private String origin;
    private String destination;
    private LocalDate heureDepart;
    private LocalDate heureArrivee;
    private float prix;
    private int placesDisponibles;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ville ville;


    public Vol(int idVol,String origin, String destination, LocalDate heureDepart, LocalDate heureArrivee, float prix, int placesDisponibles, Ville ville) {
        this.idVol = idVol;
        this.origin = origin;
        this.destination = destination;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.prix = prix;
        this.placesDisponibles = placesDisponibles;
        this.ville = ville;
    }

    public Vol() {

    }


    public int getIdVol() {
        return idVol;
    }

    public void setIdVol(int idVol) {
        this.idVol = idVol;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalDate heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalDate getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(LocalDate heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getPlacesDisponibles() {
        return placesDisponibles;
    }

    public void setPlacesDisponibles(int placesDisponibles) {
        this.placesDisponibles = placesDisponibles;
    }
}