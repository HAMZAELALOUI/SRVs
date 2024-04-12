package com.project.srv.service;

import com.project.srv.bean.DetailsBean;
import com.project.srv.dao.DetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DetailsService {
    // nombreparticipant tout seul
    @Autowired
    private DetailsDao detailsDao;

    public List<DetailsBean> findByNombreParticipants(int nombreParticipants) {
        return detailsDao.findByNombreParticipants(nombreParticipants);
    }

    public List<DetailsBean> findByPrixTotalActivite(double prixTotalActivite) {
        return detailsDao.findByPrixTotalActivite(prixTotalActivite);
    }

    @Transactional
    public void deleteByNombreParticipants(int nombreParticipants) {
        detailsDao.deleteByNombreParticipants(nombreParticipants);
    }

    @Transactional
    public void deleteByPrixTotalActivite(double prixTotalActivite) {
        detailsDao.deleteByPrixTotalActivite(prixTotalActivite);
    }

    // Vous pouvez ajouter d'autres méthodes de service personnalisées ici si nécessaire
}
