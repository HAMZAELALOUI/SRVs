package com.project.srv.service;

import com.project.srv.bean.Reservation;
import com.project.srv.dao.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;




    public  Reservation findReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }
    public List<Reservation> findByHotelId(Long hotelId) {
        return reservationRepository.findByHotelId(hotelId);
    }

    @Transactional
    public void deleteByHotelId(Long hotelId) {
        reservationRepository.deleteByHotelId(hotelId);
    }

    // Vous pouvez ajouter d'autres méthodes de service personnalisées ici si nécessaire
}