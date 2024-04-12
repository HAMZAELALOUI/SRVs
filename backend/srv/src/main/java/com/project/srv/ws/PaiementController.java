package com.project.srv.ws;

import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import com.project.srv.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Paiement")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @GetMapping("/Ref/{ref}")
    public Paiement findByRef(@PathVariable String ref) {
        return paiementService.findByRef(ref);
    }

    @GetMapping("/mode/{modePaiement}")
    public List<Paiement> findByModePaiement(@PathVariable String modePaiement) {
        return paiementService.findByModePaiement(modePaiement);
    }

    @GetMapping("/montant/{montant}")
    public List<Paiement> findByMontant(@PathVariable double montant) {
        return paiementService.findByMontant(montant);
    }

    @GetMapping("/numero/{numeroCarte}")
    public List<Paiement> findByNumeroCarte(@PathVariable String numeroCarte) {
        return paiementService.findByNumeroCarte(numeroCarte);
    }

    @GetMapping("/date/{dateExpiration}")
    public List<Paiement> findByDateExpiration(@PathVariable String dateExpiration) {
        return paiementService.findByDateExpiration(dateExpiration);
    }

    @GetMapping("/titulaire/{titulaireCarte}")
    public List<Paiement> findByTitulaireCarte(@PathVariable String titulaireCarte) {
        return paiementService.findByTitulaireCarte(titulaireCarte);
    }

    @GetMapping("/reservation")
    public Paiement findByReservation(@RequestBody Reservation reservation) {
        return paiementService.findByReservation(reservation);
    }

    @DeleteMapping("/mode/{modePaiement}")
    @Transactional
    public void deleteByModePaiement(@PathVariable String modePaiement) {
        paiementService.deleteByModePaiement(modePaiement);
    }

    @DeleteMapping("/montant/{montant}")
    @Transactional
    public void deleteByMontant(@PathVariable double montant) {
        paiementService.deleteByMontant(montant);
    }

    @DeleteMapping("/numero/{numeroCarte}")
    @Transactional
    public void deleteByNumeroCarte(@PathVariable String numeroCarte) {
        paiementService.deleteByNumeroCarte(numeroCarte);
    }

    @DeleteMapping("/date/{dateExpiration}")
    @Transactional
    public void deleteByDateExpiration(@PathVariable String dateExpiration) {
        paiementService.deleteByDateExpiration(dateExpiration);
    }

    @DeleteMapping("/titulaire/{titulaireCarte}")
    @Transactional
    public void deleteByTitulaireCarte(@PathVariable String titulaireCarte) {
        paiementService.deleteByTitulaireCarte(titulaireCarte);
    }

    @PostMapping("/save")
    @Transactional
    public int savePaiement(@RequestBody Paiement paiement) {
        return paiementService.savePaiement(paiement);
    }
}
