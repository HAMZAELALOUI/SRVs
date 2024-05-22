package com.project.srv.service;

import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Vol;
import com.project.srv.dao.ReservationVolDao;
import com.project.srv.dao.VolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationVolService {

    @Autowired
    private ReservationVolDao reservationVolDao;

    @Autowired
    private VolDao volDao;

    public ReservationVol findReservationById(Long id) {
        return reservationVolDao.findReservationById(id);
    }

    public ReservationVol findByVolId(Long volId) {
        return reservationVolDao.findByVolId(volId);
    }

    @Transactional
    public void deleteByVolId(Long volId) {
        reservationVolDao.deleteByVolId(volId);
    }

    public ReservationVol findByVol(Vol vol) {
        return reservationVolDao.findByVol(vol);
    }

    @Transactional
    public ReservationVol saveReservation(ReservationVol reservationVol) {
        // Vérifier si le vol associé à la réservation existe
        Vol existingVol = volDao.findById(reservationVol.getVolId()).orElse(null);
        if (existingVol == null) {
            return null; // Gérer le cas où le vol n'existe pas
        }

        // Vérifier si des places sont disponibles dans le vol
        if (existingVol.getPlacesDisponibles() <= 0) {
            return null; // Gérer le cas où il n'y a pas de places disponibles
        }

        // Décrémenter le nombre de places disponibles dans le vol
        existingVol.setPlacesDisponibles(existingVol.getPlacesDisponibles() - 1);

        // Enregistrer la mise à jour du vol
        volDao.save(existingVol);

        // Enregistrer la réservationVol
        return reservationVolDao.save(reservationVol);
    }

    @Transactional
    public ReservationVol updateReservation(ReservationVol reservationVol) {
        ReservationVol existingReservation = reservationVolDao.findReservationById(reservationVol.getId());
        if (existingReservation == null) {
            return null;
        }

        existingReservation.setVol(reservationVol.getVol());

        return reservationVolDao.save(existingReservation);
    }

    public ReservationVol findReservationVolById(Long id) {
        return reservationVolDao.findReservationVolById(id);
    }
}
