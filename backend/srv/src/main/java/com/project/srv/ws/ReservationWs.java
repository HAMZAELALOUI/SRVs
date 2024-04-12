package com.project.srv.ws;


import com.project.srv.bean.ReservationBean;

import com.project.srv.bean.Reservation;

import com.project.srv.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Reservation")
public class ReservationWs {

    @Autowired
    private ReservationService reservationService;


    @GetMapping("/date/{dateReservation}")
    public List<ReservationBean> findByDateReservation(@PathVariable Date dateReservation) {
        return reservationService.findByDateReservation(dateReservation);
    }

    @GetMapping("/duree/{duree}")
    public List<ReservationBean> findByDuree(@PathVariable int duree) {
        return reservationService.findByDuree(duree);
    }

    @GetMapping("/personnes/{nombrePersonnes}")
    public List<ReservationBean> findByNombrePersonnes(@PathVariable int nombrePersonnes) {
        return reservationService.findByNombrePersonnes(nombrePersonnes);
    }

    @GetMapping("/prix/{prixTotal}")
    public List<ReservationBean> findByPrixTotal(@PathVariable double prixTotal) {
        return reservationService.findByPrixTotal(prixTotal);
    }

    @DeleteMapping("/date/{dateReservation}")
    @Transactional
    public void deleteByDateReservation(@PathVariable Date dateReservation) {
        reservationService.deleteByDateReservation(dateReservation);
    }

    @DeleteMapping("/duree/{duree}")
    @Transactional
    public void deleteByDuree(@PathVariable int duree) {
        reservationService.deleteByDuree(duree);
    }

    @DeleteMapping("/personnes/{nombrePersonnes}")
    @Transactional
    public void deleteByNombrePersonnes(@PathVariable int nombrePersonnes) {
        reservationService.deleteByNombrePersonnes(nombrePersonnes);
    }

    @DeleteMapping("/prix/{prixTotal}")
    @Transactional
    public void deleteByPrixTotal(@PathVariable double prixTotal) {
        reservationService.deleteByPrixTotal(prixTotal);

    @GetMapping("/hotel/{hotelId}")
    public List<Reservation> findByHotelId(@PathVariable Long hotelId) {
        return reservationService.findByHotelId(hotelId);
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.findReservationById(id);
    }
    @DeleteMapping("/hotel/{hotelId}")
    @Transactional
    public void deleteByHotelId(@PathVariable Long hotelId) {
        reservationService.deleteByHotelId(hotelId);
    }

    // Vous pouvez ajouter d'autres méthodes de web service personnalisées ici si nécessaire
}
