package com.project.srv.service;




import com.project.srv.bean.ActiviteBean;
import com.project.srv.dao.ActiviteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActiviteService {

    // addactivité(save)----->traitement- update----->admin

    @Autowired
    private ActiviteDao activiteDao;

    public List<ActiviteBean> findByNom(String nom) {
        return activiteDao.findByNom(nom);
    }

    public List<ActiviteBean> findByLieu(String lieu) {
        return activiteDao.findByLieu(lieu);
    }

    public List<ActiviteBean> findByDescription(String description) {
        return activiteDao.findByDescription(description);
    }

    public List<ActiviteBean> findByHoraire(String horaire) {
        return activiteDao.findByHoraire(horaire);
    }

    public List<ActiviteBean> findByPrix(double prix) {
        return activiteDao.findByPrix(prix);
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
