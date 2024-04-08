package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolRepository;
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

    public void deleteVolByDestination(String destination) {
        volRepository.deleteByDestination(destination);
    }

    public void deleteVolByHeureDepart(LocalDate heureDepart) {
        volRepository.deleteByHeureDepart(heureDepart);
    }

    public void deleteVolByHeureArrivee(LocalDate heureArrivee) {
        volRepository.deleteByHeureArrivee(heureArrivee);
    }

    public void deleteVolByPrix(float prix) {
        volRepository.deleteByPrix(prix);
    }

    public void deleteAllVols() {
        volRepository.deleteAll();
    }

    @Autowired
    private VolRepository volRepository;
}
