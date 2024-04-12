package com.project.srv.dao;

import com.project.srv.bean.ReservationBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationDao extends JpaRepository<ReservationBean, Long> {
    // Méthode pour rechercher des réservations par date de réservation
    List<ReservationBean> findByDateReservation(Date dateReservation);

    // Méthode pour rechercher des réservations par durée
    List<ReservationBean> findByDuree(int duree);

    // Méthode pour rechercher des réservations par nombre de personnes
    List<ReservationBean> findByNombrePersonnes(int nombrePersonnes);

    // Méthode pour rechercher des réservations par prix total
    List<ReservationBean> findByPrixTotal(double prixTotal);

    // Méthode pour supprimer des réservations par date de réservation
    void deleteByDateReservation(Date dateReservation);

    // Méthode pour supprimer des réservations par durée
    void deleteByDuree(int duree);

    // Méthode pour supprimer des réservations par nombre de personnes
    void deleteByNombrePersonnes(int nombrePersonnes);

    // Méthode pour supprimer des réservations par prix total
    void deleteByPrixTotal(double prixTotal);
}
