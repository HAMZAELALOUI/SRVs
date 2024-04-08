package com.project.srv.dao;

import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long>{
    List<Paiement> findByModePaiement(String modePaiement);
    Paiement findByRef(String ref);

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

}
