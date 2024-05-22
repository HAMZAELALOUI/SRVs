package com.project.srv.dao;

import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Reservationactivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaiementDao extends JpaRepository<Paiement, Long> {
    List<Paiement> findByModePaiement(String modePaiement);
    Paiement findByRef(String ref);
    Paiement findByReservationactivite(Reservationactivite reservationActivite);

    List<Paiement> findByDatePaiement(LocalDateTime datePaiement);

    int deleteByDatePaiement(LocalDateTime datePaiement);

    // Méthode pour rechercher des paiements par montant
    List<Paiement> findByMontant(double montant);

    // Méthode pour rechercher des paiements par numéro de carte
    List<Paiement> findByNumeroCarte(String numeroCarte);

    // Méthode pour rechercher des paiements par date d'expiration
    List<Paiement> findByDateExpiration(String dateExpiration);

    // Méthode pour rechercher des paiements par titulaire de la carte
    List<Paiement> findByTitulaireCarte(String titulaireCarte);

    // Méthode pour rechercher des paiements par réservation
    Paiement findByReservation(Reservation reservation);
    Paiement findByReservationVol(ReservationVol reservationVol); // Nouvelle méthode de recherche


    // Méthode pour supprimer des paiements par mode de paiement
    void deleteByModePaiement(String modePaiement);

    // Méthode pour supprimer des paiements par montant
    void deleteByMontant(double montant);

    // Méthode pour supprimer des paiements par numéro de carte
    void deleteByNumeroCarte(String numeroCarte);

    // Méthode pour supprimer des paiements par date d'expiration
    void deleteByDateExpiration(String dateExpiration);

    // Méthode pour supprimer des paiements par titulaire de la carte
    void deleteByTitulaireCarte(String titulaireCarte);

    // Méthode pour trouver un paiement par son identifiant
    Optional<Paiement> findById(Long id);

    // Méthode pour supprimer un paiement par son identifiant
    void deleteById(Long id);

}
