package com.project.srv.dao;





import com.project.srv.bean.ActiviteBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActiviteDao extends JpaRepository<ActiviteBean, Long> {
    // Méthode pour rechercher des activités par nom
    Optional<ActiviteBean> findById(Long id);
    List<ActiviteBean> findByPrixBetween(double minPrix, double maxPrix);

    List<ActiviteBean> findByNom(String nom);

    // Méthode pour rechercher des activités par lieu
    List<ActiviteBean> findByLieu(String lieu);

    // Méthode pour rechercher des activités par description
    List<ActiviteBean> findByDescription(String description);

    // Méthode pour rechercher des activités par horaire
    List<ActiviteBean> findByHoraire(String horaire);

    // Méthode pour rechercher des activités par prix
    List<ActiviteBean> findByPrix(double prix);

    // Méthode pour supprimer des activités par nom
    List<ActiviteBean> findByPrixLessThan(double prix);



    void deleteById(long id);

    void deleteByNom(String nom);

    // Méthode pour supprimer des activités par lieu
    void deleteByLieu(String lieu);

    // Méthode pour supprimer des activités par description
    void deleteByDescription(String description);

    // Méthode pour supprimer des activités par horaire
    void deleteByHoraire(String horaire);

    // Méthode pour supprimer des activités par prix
    void deleteByPrix(double prix);
}
