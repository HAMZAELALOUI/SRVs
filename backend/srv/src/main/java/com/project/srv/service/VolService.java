package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VolService {

    public List<Vol> findByOrigin(String origin) {
        return volDao.findByOrigin(origin);
    }
    public List<Vol> findByDestination(String destination) {
        return volDao.findByDestination(destination);
    }

    public List<Vol> findByHeureDepart(LocalDate heureDepart) {
        return volDao.findByHeureDepart(heureDepart);
    }

    public List<Vol> findByHeureArrivee(LocalDate heureArrivee) {
        return volDao.findByHeureArrivee(heureArrivee);
    }

    public List<Vol> findByPrix(float prix) {
        return volDao.findByPrix(prix);
    }

    public List<Vol> findByVille(Ville ville) {
        return volDao.findByVille(ville);
    }

    public List<Vol> findByOriginAndDestination(String origin, String destination) {return volDao.findVolByOriginAndDestination(origin,destination);}
    public List<Vol> findByHeureDepartAndHeureArrivee(LocalDate heureDepart, LocalDate heureArrivee) {return volDao.findByHeureDepartAndHeureArrivee(heureDepart,heureArrivee);}


    public void deleteVolByDestination(String destination) {
        volDao.deleteByDestination(destination);
    }

    public void deleteVolByHeureDepart(LocalDate heureDepart) {
        volDao.deleteByHeureDepart(heureDepart);
    }

    public void deleteVolByHeureArrivee(LocalDate heureArrivee) {
        volDao.deleteByHeureArrivee(heureArrivee);
    }

    public void deleteVolByPrix(float prix) {
        volDao.deleteByPrix(prix);
    }

    public void deleteAllVols() {
        volDao.deleteAll();
    }




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

        volDao.save(vol);
        return 1;
        }


    @Autowired
    private VolDao volDao;
}
