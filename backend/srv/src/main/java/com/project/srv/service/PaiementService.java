package com.project.srv.service;


import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import com.project.srv.dao.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    public List<Paiement> findByModePaiement(String modePaiement) {
        return paiementRepository.findByModePaiement(modePaiement);
    }

    public List<Paiement> findByMontant(double montant) {
        return paiementRepository.findByMontant(montant);
    }

    public List<Paiement> findByNumeroCarte(String numeroCarte) {
        return paiementRepository.findByNumeroCarte(numeroCarte);
    }

    public List<Paiement> findByDateExpiration(String dateExpiration) {
        return paiementRepository.findByDateExpiration(dateExpiration);
    }

    public List<Paiement> findByTitulaireCarte(String titulaireCarte) {
        return paiementRepository.findByTitulaireCarte(titulaireCarte);
    }

    public Paiement findByReservation(Reservation reservation) {
        return paiementRepository.findByReservation(reservation);
    }

    @Transactional
    public void deleteByModePaiement(String modePaiement) {
        paiementRepository.deleteByModePaiement(modePaiement);
    }

    @Transactional
    public void deleteByMontant(double montant) {
        paiementRepository.deleteByMontant(montant);
    }

    @Transactional
    public void deleteByNumeroCarte(String numeroCarte) {
        paiementRepository.deleteByNumeroCarte(numeroCarte);
    }

    @Transactional
    public void deleteByDateExpiration(String dateExpiration) {
        paiementRepository.deleteByDateExpiration(dateExpiration);
    }

    @Transactional
    public void deleteByTitulaireCarte(String titulaireCarte) {
        paiementRepository.deleteByTitulaireCarte(titulaireCarte);
    }

    // Vous pouvez ajouter d'autres méthodes de service personnalisées ici si nécessaire
}
