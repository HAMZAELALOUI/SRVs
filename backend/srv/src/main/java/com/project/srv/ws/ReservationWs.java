package com.project.srv.ws;

import com.project.srv.bean.Reservation;
import com.project.srv.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Reservation")
public class ReservationWs {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/hotel/{hotelId}")
    public List<Reservation> findByHotelId(@PathVariable Long hotelId) {
        return reservationService.findByHotelId(hotelId);
    }

    @DeleteMapping("/hotel/{hotelId}")
    @Transactional
    public void deleteByHotelId(@PathVariable Long hotelId) {
        reservationService.deleteByHotelId(hotelId);
    }

    // Vous pouvez ajouter d'autres méthodes de web service personnalisées ici si nécessaire
}
