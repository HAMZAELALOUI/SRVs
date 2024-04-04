package com.project.srv.bean;


import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Vol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idVol;

    private String compagnieAerienne;
    private LocalDateTime heureDepart;
    private LocalDateTime heureArrivee;
    private float prix;
    private int placesDisponibles;
    @ManyToOne
    private Ville ville;

    public Vol() {

    }

    public Vol(int idVol, String compagnieAerienne, LocalDateTime heureDepart, LocalDateTime heureArrivee, float prix, int placesDisponibles, Ville ville) {
        this.idVol = idVol;
        this.compagnieAerienne = compagnieAerienne;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.prix = prix;
        this.placesDisponibles = placesDisponibles;
        this.ville = ville;
    }


    public int getIdVol() {
        return idVol;
    }

    public void setIdVol(int idVol) {
        this.idVol = idVol;
    }

    public String getCompagnieAerienne() {
        return compagnieAerienne;
    }

    public void setCompagnieAerienne(String compagnieAerienne) {
        this.compagnieAerienne = compagnieAerienne;
    }

    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalDateTime getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(LocalDateTime heureArrivee) {
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