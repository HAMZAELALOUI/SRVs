package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.dao.VilleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    public List<Ville> findByNom(String nom) {
        return villeDao.findByNom(nom);
    }


    public Optional<Ville> findById(Long id) {
        return villeDao.findById(id);
    }


    public List<Ville> findByPays(String pays) {
        return villeDao.findByPays(pays);
    }

    public List<Ville> findByNomContainingIgnoreCase(String nom) {
        return villeDao.findByNomContainingIgnoreCase(nom);
    }

//    public List<Ville> findByNomAndPays(String nom, String pays) { return villeRepository.findByNomAndPays(nom,pays); }


    public void deleteVilleByNom(String nom) {
        villeDao.deleteByNom(nom);
    }

    public void deleteVilleByPays(String pays) {
        villeDao.deleteByPays(pays);
    }

    public void deleteAllVilles() {
        villeDao.deleteAll();
    }


    public Ville save(Ville ville) {
        // Check if a ville with the same name and country already exists
        Optional<Ville> existingVille = villeDao.findByNomAndPays(ville.getNom(), ville.getPays());
        if (existingVille.isPresent()) {
            // If it exists, return the existing ville without saving the new one
            return existingVille.get();
        } else {
            // If it doesn't exist, save the new ville
            return villeDao.save(ville);
        }
    }

    @Autowired
    VilleDao villeDao;
}
