package com.project.srv.bean;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ref;
    private LocalDateTime datePaiement;

    private String modePaiement;
    private double montant;
    private String numeroCarte;
    private String dateExpiration;
    private String titulaireCarte;

    @OneToOne
    private Reservation reservation;
    @OneToOne
    private ReservationVol reservationVol; // Ajout de la relation avec ReservationVol
    @OneToOne
    private Reservationactivite reservationactivite;

    // Getter et setter pour le champ ref
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    // Autres getter et setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getTitulaireCarte() {
        return titulaireCarte;
    }

    public void setTitulaireCarte(String titulaireCarte) {
        this.titulaireCarte = titulaireCarte;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservationVol(ReservationVol reservationVol) {
        this.reservationVol = reservationVol;
    }
    public ReservationVol getReservationVol() {
        return reservationVol;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }
    public Reservationactivite getReservationactivite() {
        return reservationactivite;
    }
    public void setReservationactivite(Reservationactivite reservationactivite){
        this.reservationactivite = reservationactivite;
    }
}
