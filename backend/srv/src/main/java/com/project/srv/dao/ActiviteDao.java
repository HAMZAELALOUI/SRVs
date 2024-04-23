package com.project.srv.dao;





import com.project.srv.bean.Activite;
import com.project.srv.bean.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActiviteDao extends JpaRepository<Activite, Long> {
    // Méthode pour rechercher des activités par nom
    Optional<Activite> findById(Long id);
    List<Activite> findByPrixBetween(double minPrix, double maxPrix);

    List<Activite> findByNom(String nom);
    List<Activite> findByVille(Ville ville);

    // Méthode pour rechercher des activités par lieu
    List<Activite> findByLieu(String lieu);



    // Méthode pour rechercher des activités par description
    List<Activite> findByDescription(String description);

    // Méthode pour rechercher des activités par horaire
    List<Activite> findByHoraire(String horaire);
    List<Activite> findByVilleAndHoraire(Ville ville,String horaire);

    // Méthode pour rechercher des activités par prix
    List<Activite> findByPrix(double prix);

    // Méthode pour supprimer des activités par nom
    List<Activite> findByPrixLessThan(double prix);
    List<Activite> findByVilleNom(String nomVille);

    @Query("SELECT a FROM Activite a JOIN FETCH a.ville WHERE a.id = :activiteId")
    Optional<Activite> findActiviteWithVilleById(@Param("activiteId") Long activiteId);
    void deleteById(long id);

    void deleteByNom(String nom);
    void deleteByVille(Ville ville);

    // Méthode pour supprimer des activités par lieu
    void deleteByLieu(String lieu);

    // Méthode pour supprimer des activités par description
    void deleteByDescription(String description);

    // Méthode pour supprimer des activités par horaire
    void deleteByHoraire(String horaire);

    // Méthode pour supprimer des activités par prix
    void deleteByPrix(double prix);


}
