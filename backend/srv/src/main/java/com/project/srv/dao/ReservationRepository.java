package com.project.srv.dao;

import com.project.srv.bean.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Méthode pour rechercher des réservations par ID de l'hôtel associé
    List<Reservation> findByHotelId(Long hotelId);

    // Méthode pour supprimer des réservations par ID de l'hôtel associé
    void deleteByHotelId(Long hotelId);

}
