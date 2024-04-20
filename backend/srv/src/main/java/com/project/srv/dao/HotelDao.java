package com.project.srv.dao;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelDao extends JpaRepository<Hotel, Long> {
    // Méthode pour rechercher des hôtels par emplacement
    List<Hotel> findByEmplacement(String emplacement);

    // Méthode pour rechercher des hôtels par nombre d'étoiles
    List<Hotel> findByNombreEtoiles(int nombreEtoiles);
    List<Hotel> findByVille(Ville ville);


    List<Hotel> findHotelByReservationsId(Long reservationId);

    // Méthode pour rechercher un hôtel par son identifiant
    Optional<Hotel> findById(Long id);

    // Méthode pour rechercher des hôtels par nom
    List<Hotel> findByNom(String nom);

    // Méthode pour supprimer des hôtels par emplacement
    void deleteByEmplacement(String emplacement);

    // Méthode pour supprimer un hôtel par son identifiant
    void deleteById(Long id);

    // Méthode pour supprimer des hôtels par nombre d'étoiles
    void deleteByNombreEtoiles(int nombreEtoiles);

    // Méthode pour supprimer des hôtels par nom
    void deleteByNom(String nom);

    List<Hotel> findByPrixChambresBetween(double prixMin, double prixMax);
    List<Hotel> findByPrixChambresLessThan(double prixMax);
}
