package com.project.srv.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Paiement paiement;
    @ManyToOne
    @JsonIgnore // Add this annotation
    private Hotel hotel;

    private Long hotelId;

    public Long getHotelId() {
        return hotelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}
