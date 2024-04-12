package com.project.srv.bean;


import jakarta.persistence.*;

@Entity
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private Activite activite;

    private int nombreParticipants;
    private double prixTotalActivite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Activite getActiviteTouristique() {
        return activite;
    }

    public void setActiviteTouristique(Activite activite) {
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
