package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import com.project.srv.exeption.InvalidDataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VolService {

    @PersistenceContext
    private EntityManager entityManager;

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
    public void deleteVolById(Long id){volDao.deleteById(id);}
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

    @Transactional
    public Vol save(Vol vol) throws InvalidDataException {
        Ville origin = vol.getOrigin();
        if (origin != null && origin.getId() != null) {
            vol.setOrigin(entityManager.merge(origin));
        }

        // Merge destination
        Ville destination = vol.getDestination();
        if (destination != null && destination.getId() != null) {
            vol.setDestination(entityManager.merge(destination));
        }


        if (vol.getHeureDepart() == null || vol.getHeureDepart().isBefore(LocalDate.now())) {
            throw new InvalidDataException("Departure date is required and must not be in the past.");
        }

        if (vol.getHeureArrivee() == null || vol.getHeureArrivee().isBefore(LocalDate.now())) {
            throw new InvalidDataException("Arrival date is required and must not be in the past.");
        }

        if (vol.getPrix() <= 0) {
            throw new InvalidDataException("Price must be positive.");
        }

        if (vol.getPlacesDisponibles() <= 0) {
            throw new InvalidDataException("Number of available places must be positive.");
        }

        return volDao.save(vol);
    }



    @Autowired
    private VolDao volDao;



}
