package com.project.srv.service;

import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import com.project.srv.dao.PaiementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaiementService {

    @Autowired
    private PaiementDao paiementDao;

    @Autowired
    private ReservationService reservationService;

    public Paiement findByRef(String ref) {
        return paiementDao.findByRef(ref);
    }

    public List<Paiement> findByModePaiement(String modePaiement) {
        return paiementDao.findByModePaiement(modePaiement);
    }

    public List<Paiement> findByMontant(double montant) {
        return paiementDao.findByMontant(montant);
    }

    public List<Paiement> findByNumeroCarte(String numeroCarte) {
        return paiementDao.findByNumeroCarte(numeroCarte);
    }

    public List<Paiement> findByDateExpiration(String dateExpiration) {
        return paiementDao.findByDateExpiration(dateExpiration);
    }

    public List<Paiement> findByTitulaireCarte(String titulaireCarte) {
        return paiementDao.findByTitulaireCarte(titulaireCarte);
    }

    public Paiement findByReservation(Reservation reservation) {
        return paiementDao.findByReservation(reservation);
    }

    @Transactional
    public void deleteByModePaiement(String modePaiement) {
        paiementDao.deleteByModePaiement(modePaiement);
    }

    @Transactional
    public void deleteByMontant(double montant) {
        paiementDao.deleteByMontant(montant);
    }

    @Transactional
    public void deleteByNumeroCarte(String numeroCarte) {
        paiementDao.deleteByNumeroCarte(numeroCarte);
    }

    @Transactional
    public void deleteByDateExpiration(String dateExpiration) {
        paiementDao.deleteByDateExpiration(dateExpiration);
    }

    public List<Paiement> findByDatePaiement(LocalDateTime datePaiement) {
        return paiementDao.findByDatePaiement(datePaiement);
    }

    @Transactional
    public int deleteByDatePaiement(LocalDateTime datePaiement) {
        return paiementDao.deleteByDatePaiement(datePaiement);
    }

    @Transactional
    public void deleteByTitulaireCarte(String titulaireCarte) {
        paiementDao.deleteByTitulaireCarte(titulaireCarte);
    }

    @Transactional
    public int savePaiement(Paiement paiement) {
        LocalDateTime localDateTime = LocalDateTime.now();
        paiement.setDatePaiement(localDateTime);

        // Vérifie si la réservation associée au paiement existe
        if (paiement.getReservation() == null || paiement.getReservation().getId() == null) {
            return -2; // Retourne -2 pour indiquer qu'aucune réservation n'est associée au paiement
        }

        Reservation reservation = reservationService.findReservationById(paiement.getReservation().getId());
        if (reservation == null) {
            return -2; // Retourne -2 si la réservation n'existe pas
        }

        // Vérifie si un paiement pour la même réservation existe déjà
        Paiement existingPaiement = paiementDao.findByReservation(reservation);
        if (existingPaiement != null) {
            return -1; // Retourne -1 pour indiquer qu'un paiement pour la même réservation existe déjà
        }

        // Vérifie si le montant du paiement est valide
        BigDecimal montant = BigDecimal.valueOf(paiement.getMontant());
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            return -3; // Retourne -3 pour indiquer que le montant du paiement est invalide
        }

        // Sauvegarde le paiement dans la base de données
        paiementDao.save(paiement);

        // Attribution d'une référence unique au paiement
        paiement.setRef("p-" + paiement.getId());

        // Sauvegarde à nouveau pour mettre à jour la référence
        paiementDao.save(paiement);

        return 1; // Retourne 1 pour indiquer que le paiement a été sauvegardé avec succès
    }

    // Vous pouvez ajouter d'autres méthodes de service personnalisées ici si nécessaire
}
