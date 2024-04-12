package com.project.srv.bean;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class ReservationBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date dateReservation;
    private int duree;
    private int nombrePersonnes;
    private double prixTotal;


    @OneToMany(mappedBy = "reservation")
    private List<DetailsBean> details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }



    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
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



    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }
}
