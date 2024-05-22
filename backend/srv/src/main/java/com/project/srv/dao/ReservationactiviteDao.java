package com.project.srv.dao;

import com.project.srv.bean.Reservation;
import com.project.srv.bean.Reservationactivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationactiviteDao extends JpaRepository<Reservationactivite, Long> {

    // Already defined methods:

    Reservationactivite findReservationById(Long id);

    // Additional methods based on Reservation bean:

    // Find reservations by activity ID
    List<Reservationactivite> findByActiviteId(Long activiteId);

    // Delete reservations by activity ID
    void deleteByActiviteId(Long activiteId);

    // Find reservations by date
    List<Reservationactivite> findByDateReservation(Date dateReservation);

    // Find reservations by number of persons
    List<Reservationactivite> findByNombrePersonnes(int nombrePersonnes);

    // Find reservations within a price range
    List<Reservationactivite> findByPrixTotalBetween(double prixMin, double prixMax);

    List<Reservationactivite> findByActiviteIdAndDateReservation(Long id, Date dateReservation);
}
