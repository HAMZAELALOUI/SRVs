package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {
    public List<Ville> findByNom(String nom) { return villeRepository.findByNom(nom); }

    public List<Ville> findByPays(String pays) { return villeRepository.findByPays(pays); }

    public List<Ville> findByNomContainingIgnoreCase(String nom) { return villeRepository.findByNomContainingIgnoreCase(nom); }

    // public List<Ville> findByNomAndPays(String nom, String pays) { return villeRepository.findByNomAndPays(nom,pays); }

    public void deleteVilleByNom(String nom){ villeRepository.deleteByNom(nom); }
    public void deleteVilleByPays(String pays){ villeRepository.deleteByPays(pays); }
    public void deleteAllVilles() { villeRepository.deleteAll(); }

    @Autowired
    VilleRepository villeRepository;
}
