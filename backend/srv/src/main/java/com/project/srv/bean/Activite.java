package com.project.srv.bean;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Activite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;
    private String image;

    private String lieu;
    private String description;
    private String horaire;
    private double prix;
    @ManyToOne (fetch = FetchType.EAGER)
    private Ville ville;
    @OneToMany(mappedBy = "activite")

    private List<Details> details;
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

    public String getLieu() {
        return lieu;
    }
    public String getImage(){return image;}

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }
    public Ville getVille() {
        return ville;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
    }
    public void setImage(String image) {
        this.image = image;
    }


}
