package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VolService {


    public List<Vol> findByDestination(String destination) {
        return volRepository.findByDestination(destination);
    }

    public List<Vol> findByHeureDepart(LocalDate heureDepart) {
        return volRepository.findByHeureDepart(heureDepart);
    }

    public List<Vol> findByHeureArrivee(LocalDate heureArrivee) {
        return volRepository.findByHeureArrivee(heureArrivee);
    }

    public List<Vol> findByPrix(float prix) {
        return volRepository.findByPrix(prix);
    }

    public List<Vol> findByVille(Ville ville) {
        return volRepository.findByVille(ville);
    }

    @Transactional
    public void deleteVolByDestination(String destination) { volRepository.deleteByDestination(destination); }
    @Transactional
    public void deleteVolByHeureDepart(LocalDate heureDepart) { volRepository.deleteByHeureDepart(heureDepart); }
    @Transactional
    public void deleteVolByHeureArrivee(LocalDate heureArrivee) { volRepository.deleteByHeureArrivee(heureArrivee); }
    @Transactional
    public void deleteVolByPrix(float prix) { volRepository.deleteByPrix(prix); }
    @Transactional
    public void deleteAllVols() { volRepository.deleteAll(); }

    public int save(Vol vol) {

        if (vol.getDestination() == null || vol.getDestination().isEmpty()) {
            return -2; // Le nom est requis
        }

        if (vol.getHeureDepart() == null || vol.getHeureDepart().isBefore(LocalDate.now())) {
            return -3; // Le lieu est requis et ne doit pas être dans le passé
        }

        if (vol.getHeureArrivee() == null || vol.getHeureArrivee().isBefore(LocalDate.now())) {
            return -3; // Le lieu est requis et ne doit pas être dans le passé
        }

        if (vol.getPrix() <= 0) {
            return -3; // Le prix doit être positif
        }

        if (vol.getPlacesDisponibles() <= 0) {
            return -4;
        }
        // Sauvegarde de l'activité dans la base de données

        volRepository.save(vol);
        return 1;
        }


    @Autowired
    private VolRepository volRepository;
}
