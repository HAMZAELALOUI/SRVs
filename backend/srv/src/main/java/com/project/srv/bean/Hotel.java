package com.project.srv.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String image;

    private String emplacement;
    private String description;
    private int nombreEtoiles;
    private double prixChambres;


    private String dateD;
    private String dateA;

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        dateD = dateD;
    }

    public String getDateA() {
        return dateA;
    }

    public void setDateA(String dateA) {
        dateA = dateA;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }



    @OneToMany(mappedBy = "hotel")
    @JsonIgnore // Add this annotation
    private List<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "Ville")
    private Ville ville;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNombreEtoiles() {
        return nombreEtoiles;
    }

    public void setNombreEtoiles(int nombreEtoiles) {
        this.nombreEtoiles = nombreEtoiles;
    }

    public double getPrixChambres() {
        return prixChambres;
    }

    public void setPrixChambres(double prixChambres) {
        this.prixChambres = prixChambres;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image=image;
    }

}
