package com.project.srv.service;




import com.project.srv.bean.Activite;
import com.project.srv.dao.ActiviteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService {

    // addactivité(save)----->traitement- update----->admin

    @Autowired
    private ActiviteDao activiteDao;

    public Optional<Activite> findById(Long Id) {
        return activiteDao.findById(Id);
    }

    public List<Activite> findByPrixActviteBetween(double prixMin, double prixMax) {
        return activiteDao.findByPrixBetween(prixMin, prixMax);
    }

    public List<Activite> findByNom(String nom) {
        return activiteDao.findByNom(nom);
    }

    public List<Activite> findByLieu(String lieu) {
        return activiteDao.findByLieu(lieu);
    }

    public List<Activite> findByDescription(String description) {
        return activiteDao.findByDescription(description);
    }
    public int saveActivite(Activite activite) {

        if (activite.getNom() == null || activite.getNom().isEmpty()) {
            return -2; // Le nom est requis
        }
        if (activite.getLieu() == null || activite.getLieu().isEmpty()) {
            return -3; // Le lieu est requis
        }
        if (activite.getPrix() <= 0) {
            return -4; // Le prix doit être positif
        }
        // Sauvegarde de l'activité dans la base de données
        activiteDao.save(activite);
        return 1; // Succès
    }
    public int updateActivite(Activite activite) {
        Activite existingActivite = activiteDao.findById(activite.getId()).orElse(null);
        if (existingActivite != null) {
            existingActivite.setNom(activite.getNom());
            existingActivite.setLieu(activite.getLieu());
            existingActivite.setDescription(activite.getDescription());
            existingActivite.setHoraire(activite.getHoraire());
            existingActivite.setPrix(activite.getPrix());

            activiteDao.save(existingActivite);
            return 1; // Mise à jour réussie
        } else {
            return -1; // L'activité n'existe pas
        }
    }
    public List<Activite> findByPrixLessThan(double prixMax) {
        return activiteDao.findByPrixLessThan(prixMax);
    }

    public List<Activite> findByHoraire(String horaire) {
        return activiteDao.findByHoraire(horaire);
    }

    public List<Activite> findByPrix(double prix) {
        return activiteDao.findByPrix(prix);
    }

    @Transactional
    public void deleteById(long id) {
        activiteDao.deleteById(id);
    }
    @Transactional
    public void deleteByNom(String nom) {
        activiteDao.deleteByNom(nom);
    }

    @Transactional
    public void deleteByLieu(String lieu) {
        activiteDao.deleteByLieu(lieu);
    }

    @Transactional
    public void deleteByDescription(String description) {
        activiteDao.deleteByDescription(description);
    }

    @Transactional
    public void deleteByHoraire(String horaire) {
        activiteDao.deleteByHoraire(horaire);
    }

    @Transactional
    public void deleteByPrix(double prix) {
        activiteDao.deleteByPrix(prix);
    }

    // Vous pouvez ajouter d'autres méthodes de service personnalisées ici si nécessaire
}
