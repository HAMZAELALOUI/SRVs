package com.project.srv.service;

import com.project.srv.bean.Activite;
import com.project.srv.bean.Reservationactivite;
import com.project.srv.dao.ActiviteDao;
import com.project.srv.dao.ReservationDao;
import com.project.srv.dao.ReservationactiviteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReservationactiviteService {

    @Autowired
    private ReservationactiviteDao reservationactiviteDao;

    @Autowired
    private ActiviteDao activiteDao;

    // ... existing methods ...

    public List<Reservationactivite> findByActiviteId(Long activiteId) {
        return reservationactiviteDao.findByActiviteId(activiteId);
    }

    @Transactional
    public void deleteByActiviteId(Long activiteId) {
        reservationactiviteDao.deleteByActiviteId(activiteId);
    }

    public List<Reservationactivite> findByDateReservation(Date dateReservation) {
        return reservationactiviteDao.findByDateReservation(dateReservation);
    }

    public List<Reservationactivite> findByNombrePersonnes(int nombrePersonnes) {
        return reservationactiviteDao.findByNombrePersonnes(nombrePersonnes);
    }

    public List<Reservationactivite> findByPrixTotalBetween(double prixMin, double prixMax) {
        return reservationactiviteDao.findByPrixTotalBetween(prixMin, prixMax);
    }

    @Transactional
    public Reservationactivite saveReservationActivite(Reservationactivite reservation) {
        // Verify if the activity exists in the database
        Activite existingActivite = activiteDao.findById(reservation.getActivite().getId()).orElse(null);
        if (existingActivite == null) {
            // Handle the case where the activity doesn't exist
            return null;
        }

        List<Reservationactivite> existingReservations = reservationactiviteDao.findByActiviteIdAndDateReservation(
                reservation.getActivite().getId(), reservation.getDateReservation());
        if (!existingReservations.isEmpty()) {

            return null;
        }

        return reservationactiviteDao.save(reservation);
    }

    public Reservationactivite findReservationById(Long id) {
        return reservationactiviteDao.findReservationById(id);
    }
}
