package com.project.srv.bean;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class DetailsBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ReservationBean reservation;

    @ManyToOne
    private ActiviteBean activite;

    private int nombreParticipants;
    private double prixTotalActivite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationBean getReservation() {
        return reservation;
    }

    public void setReservation(ReservationBean reservation) {
        this.reservation = reservation;
    }

    public ActiviteBean getActiviteTouristique() {
        return activite;
    }

    public void setActiviteTouristique(ActiviteBean activite) {
        this.activite = activite;
    }

    public int getNombreParticipants() {
        return nombreParticipants;
    }

    public void setNombreParticipants(int nombreParticipants) {
        this.nombreParticipants = nombreParticipants;
    }

    public double getPrixTotalActivite() {
        return prixTotalActivite;
    }

    public void setPrixTotalActivite(double prixTotalActivite) {
        this.prixTotalActivite = prixTotalActivite;
    }
    // Ajoutez les getters et setters pour les autres attributs si nécessaire
}
