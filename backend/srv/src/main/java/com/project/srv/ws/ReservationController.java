package com.project.srv.ws;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Reservation;
import com.project.srv.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/hotel/{hotelId}")
    public Reservation findByHotelId(@PathVariable Long hotelId) {
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
    @GetMapping("/hotel")
    public Reservation findByHotel(@RequestBody Hotel hotel) {
        return reservationService.findByHotel(hotel);
    }

    @PutMapping("/update")
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }
    @PostMapping("/save")
    public Reservation saveReservation(@RequestBody Reservation reservation) {
        return reservationService.saveReservation(reservation);
    }

}
