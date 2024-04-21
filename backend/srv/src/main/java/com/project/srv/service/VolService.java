package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VolService {

    public List<Vol> findByOrigin(Ville origin) {
        return volDao.findByOrigin(origin);
    }

    public List<Vol> findByOriginName(Ville origin){
        return volDao.findByOrigin(origin);
    }
    public List<Vol> findByOriginPays(Ville origin){
        return volDao.findByOrigin(origin);
    }
    public List<Vol> findByDestination(Ville destination) {
        return volDao.findByDestination(destination);
    }

    public List<Vol> findByDestinatiomName(Ville origin){
        return volDao.findByOrigin(origin);
    }
    public List<Vol> findByDestinatiomPays(Ville origin){
        return volDao.findByOrigin(origin);
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

    public List<Vol> findByOriginAndDestination(Ville origin, Ville destination) {return volDao.findVolByOriginAndDestination(origin,destination);}
    public List<Vol> findByHeureDepartAndHeureArrivee(LocalDate heureDepart, LocalDate heureArrivee) {return volDao.findByHeureDepartAndHeureArrivee(heureDepart,heureArrivee);}

    public List<Vol> searchByAll(Ville origin, Ville destination, LocalDate heureDepart, LocalDate heureArrivee) {return volDao.searchByAll(origin,destination,heureDepart,heureArrivee);}
    public void deleteVolByOrigin(Ville origin) {
        volDao.deleteByDestination(origin);
    }
    public void deleteVolByDestination(Ville destination) {
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

        if (vol.getOrigin() == null ) {
            return -2; // Le nom est requis
        }

        if (vol.getDestination() == null ) {
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
