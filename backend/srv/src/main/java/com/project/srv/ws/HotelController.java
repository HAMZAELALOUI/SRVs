package com.project.srv.ws;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Ville;
import com.project.srv.service.HotelService;
import com.project.srv.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private VilleService villeService;

    @GetMapping("/emplacement/{emplacement}")
    public List<Hotel> findByEmplacement(@PathVariable String emplacement) {
        return hotelService.findByEmplacement(emplacement);
    }

    @GetMapping("/etoiles/{nombreEtoiles}")
    public List<Hotel> findByNombreEtoiles(@PathVariable int nombreEtoiles) {
        return hotelService.findByNombreEtoiles(nombreEtoiles);
    }
    @GetMapping("/ville/{ville}")
    public List<Hotel> findByVille(@PathVariable Ville ville) {
        return hotelService.findByVille(ville);
    }
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
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
    @GetMapping("/horaire/{horaire}")
    public List<Hotel> findByHoraire(@PathVariable Date horaire) {
        return hotelService.findByHoraire(horaire);
    }

    @GetMapping("/horaireAndVille/{horaire}/{villeId}")
    public List<Hotel> findByHoraireAndVille(@PathVariable Date horaire, @PathVariable Long villeId) {
        Ville ville = villeService.findById(villeId).orElse(null);
        if (ville == null) {
            // Gérer le cas où la ville n'est pas trouvée
            return null;
        }
        return hotelService.findByHoraireAndVille(horaire, ville);
    }

}
