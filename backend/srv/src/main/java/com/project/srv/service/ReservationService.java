package com.project.srv.service;

import com.project.srv.bean.ReservationBean;
import com.project.srv.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    ////reservation activité
    @Autowired
    private ReservationDao reservationDao;

    public List<ReservationBean> findByDateReservation(Date dateReservation) {
        return reservationDao.findByDateReservation(dateReservation);
    }

    public List<ReservationBean> findByDuree(int duree) {
        return reservationDao.findByDuree(duree);
    }

    public List<ReservationBean> findByNombrePersonnes(int nombrePersonnes) {
        return reservationDao.findByNombrePersonnes(nombrePersonnes);
    }

    public List<ReservationBean> findByPrixTotal(double prixTotal) {
        return reservationDao.findByPrixTotal(prixTotal);
    }

    @Transactional
    public void deleteByDateReservation(Date dateReservation) {
        reservationDao.deleteByDateReservation(dateReservation);
    }

    @Transactional
    public void deleteByDuree(int duree) {
        reservationDao.deleteByDuree(duree);
    }

    @Transactional
    public void deleteByNombrePersonnes(int nombrePersonnes) {
        reservationDao.deleteByNombrePersonnes(nombrePersonnes);
    }

    @Transactional
    public void deleteByPrixTotal(double prixTotal) {
        reservationDao.deleteByPrixTotal(prixTotal);
    }

    // Vous pouvez ajouter d'autres méthodes de service personnalisées ici si nécessaire
}
