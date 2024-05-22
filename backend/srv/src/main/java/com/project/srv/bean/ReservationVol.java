package com.project.srv.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ReservationVol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne
    private Paiement paiement;
    @ManyToOne
    @JoinColumn(name = "vol") // Assurez-vous que le nom de la colonne est correct
    private Vol vol;


    private Long volId;

    public Long getVolId() {
        return volId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public void setVolId(Long volId) {
        this.volId = volId;
    }
}
