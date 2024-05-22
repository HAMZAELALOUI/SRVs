package com.project.srv.bean;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Reservationactivite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Activite activite;
    @ManyToOne
    private Utilisateur user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation = new Date(); // Initialisation par défaut à la date actuelle

    private int nombrePersonnes;

    private double prixTotal;

    // Constructeurs
    public Reservationactivite() {
        // Constructeur par défaut
    }

    public Reservationactivite(Activite activite,Utilisateur user, int nombrePersonnes, double prixTotal) {
        this.activite = activite;
        this.user = user;
        this.nombrePersonnes = nombrePersonnes;
        this.prixTotal = prixTotal;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
}
