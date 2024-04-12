package com.project.srv.ws;

import com.project.srv.bean.Hotel;
import com.project.srv.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/emplacement/{emplacement}")
    public List<Hotel> findByEmplacement(@PathVariable String emplacement) {
        return hotelService.findByEmplacement(emplacement);
    }

    @GetMapping("/etoiles/{nombreEtoiles}")
    public List<Hotel> findByNombreEtoiles(@PathVariable int nombreEtoiles) {
        return hotelService.findByNombreEtoiles(nombreEtoiles);
    }

    @GetMapping("/reservation/{reservationId}")
    public List<Hotel> findHotelByReservationId(@PathVariable Long reservationId) {
        return hotelService.findHotelByReservationId(reservationId);
    }

    @GetMapping("/nom/{nom}")
    public List<Hotel> findByNom(@PathVariable String nom) {
        return hotelService.findByNom(nom);
    }

    @DeleteMapping("/emplacement/{emplacement}")
    @Transactional
    public void deleteByEmplacement(@PathVariable String emplacement) {
        hotelService.deleteByEmplacement(emplacement);
    }

    @DeleteMapping("/etoiles/{nombreEtoiles}")
    @Transactional
    public void deleteByNombreEtoiles(@PathVariable int nombreEtoiles) {
        hotelService.deleteByNombreEtoiles(nombreEtoiles);
    }

    @DeleteMapping("/nom/{nom}")
    @Transactional
    public void deleteByNom(@PathVariable String nom) {
        hotelService.deleteByNom(nom);
    }

    @PostMapping("/save")
    public int saveHotel(@RequestBody Hotel hotel) {
        return hotelService.saveHotel(hotel);
    }

    @PutMapping("/update")
    public int updateHotel(@RequestBody Hotel hotel) {
        return hotelService.updateHotel(hotel);
    }

    @GetMapping("/prixChambres/lessThan/{prixMax}")
    public List<Hotel> findByPrixChambresLessThan(@PathVariable double prixMax) {
        return hotelService.findByPrixChambresLessThan(prixMax);
    }

    @GetMapping("/prixChambres/between/{prixMin}/{prixMax}")
    public List<Hotel> findByPrixChambresBetween(@PathVariable double prixMin, @PathVariable double prixMax) {
        return hotelService.findByPrixChambresBetween(prixMin, prixMax);
    }

    // Endpoint pour rechercher un hôtel par son identifiant
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelService.findById(id).orElse(null);
    }
}
