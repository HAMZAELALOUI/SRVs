package com.project.srv.dao;


import com.project.srv.bean.ActiviteBean;
import com.project.srv.bean.DetailsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsDao extends JpaRepository<DetailsBean, Long> {
    // Méthode pour rechercher des détails par le nombre de participants
    List<DetailsBean> findByNombreParticipants(int nombreParticipants);

    // Méthode pour rechercher des détails par le prix total de l'activité
    List<DetailsBean> findByPrixTotalActivite(double prixTotalActivite);

    // Méthode pour supprimer des détails par le nombre de participants
    void deleteByNombreParticipants(int nombreParticipants);

    // Méthode pour supprimer des détails par le prix total de l'activité
    void deleteByPrixTotalActivite(double prixTotalActivite);
}
