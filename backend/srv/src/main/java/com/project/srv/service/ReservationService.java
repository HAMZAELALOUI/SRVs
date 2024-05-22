package com.project.srv.service;

import com.project.srv.bean.Reservation;
import com.project.srv.dao.HotelDao;
import com.project.srv.dao.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.srv.bean.Hotel;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private HotelDao hotelDao;


    public  Reservation findReservationById(Long id) {
        return reservationDao.findReservationById(id);
    }
    public Reservation findByHotelId(Long hotelId) {
        return reservationDao.findByHotelId(hotelId);
    }

    @Transactional
    public void deleteByHotelId(Long hotelId) {
        reservationDao.deleteByHotelId(hotelId);
    }



    public Reservation findByHotel(Hotel hotel) {
        return reservationDao.findByHotel(hotel);
    }

    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        // Vérifier si l'hôtel existe déjà dans la base de données
        Hotel existingHotel = hotelDao.findById(reservation.getHotelId()).orElse(null);
        if (existingHotel == null) {
            // Gérer le cas où l'hôtel n'existe pas
            return null;
        }

        // Vérifier s'il existe déjà une réservation pour cet hôtel
        Reservation existingReservation = reservationDao.findByHotelId(reservation.getHotelId());
        if (existingReservation != null) {
            // Gérer le cas où une réservation existe déjà pour cet hôtel
            return null;
        }

        // Enregistrer la réservation
        return reservationDao.save(reservation);
    }


    @Transactional
    public Reservation updateReservation(Reservation reservation) {
        // Vérifier si la réservation existe dans la base de données
        Reservation existingReservation = reservationDao.findReservationById(reservation.getId());
        if (existingReservation == null) {
            return null; // Gérer le cas où la réservation n'existe pas
        }

        // Mettre à jour les informations de la réservation
        existingReservation.setHotel(reservation.getHotel());
        // Ajoutez d'autres attributs à mettre à jour selon les besoins

        // Enregistrer les modifications dans la base de données
        return reservationDao.save(existingReservation);
    }

}
