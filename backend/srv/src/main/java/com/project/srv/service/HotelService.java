package com.project.srv.service;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Ville;
import com.project.srv.dao.HotelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelDao hotelDao;

    public List<Hotel> findByEmplacement(String emplacement) {
        return hotelDao.findByEmplacement(emplacement);
    }

    public List<Hotel> findByNombreEtoiles(int nombreEtoiles) {
        return hotelDao.findByNombreEtoiles(nombreEtoiles);
    }

    public List<Hotel> findHotelByReservationId(Long reservationId) {
        return hotelDao.findHotelByReservationsId(reservationId);
    }

    public List<Hotel> findByNom(String nom) {
        return hotelDao.findByNom(nom);
    }
    public List<Hotel> findAll() {
        return hotelDao.findAll();
    }

    public List<Hotel> findByVille(String ville) {
        return hotelDao.findByVille(ville);
    }

    @Transactional
    public void deleteByEmplacement(String emplacement) {
        hotelDao.deleteByEmplacement(emplacement);
    }

    @Transactional
    public void deleteByNombreEtoiles(int nombreEtoiles) {
        hotelDao.deleteByNombreEtoiles(nombreEtoiles);
    }

    @Transactional
    public void deleteByNom(String nom) {
        hotelDao.deleteByNom(nom);
    }


    @Transactional
    public int saveHotel(Hotel hotel) {
        if (hotel.getId() != null && hotelDao.existsById(hotel.getId())) {
            return -1;
        }
        if (hotel.getNom() == null || hotel.getNom().isEmpty()) {
            // Handle cases where the hotel name is empty or null
            return -2;
        }

        // Ensure the Ville object is set for the hotel
        Ville ville = hotel.getVille();
        if (ville == null || ville.getId() == null) {
            // Handle cases where the Ville is not set or not valid
            return -3;
        }

        int nombreEtoiles = hotel.getNombreEtoiles();
        if (nombreEtoiles < 1 || nombreEtoiles > 5) {
            return -4;
        }
        if (hotel.getPrixChambres() <= 0) {
            return -5;
        }

        // Save the hotel along with its associated Ville
        hotelDao.save(hotel);

        return 1;
    }



    public int updateHotel(Hotel hotel) {
        Hotel existingHotel = hotelDao.findById(hotel.getId()).orElse(null);
        if (existingHotel != null) {
            existingHotel.setNom(hotel.getNom());
            existingHotel.setEmplacement(hotel.getEmplacement());
            existingHotel.setDescription(hotel.getDescription());
            existingHotel.setNombreEtoiles(hotel.getNombreEtoiles());
            existingHotel.setPrixChambres(hotel.getPrixChambres());

            hotelDao.save(existingHotel);
            return 1;
        } else {
            hotelDao.save(hotel);
            return 2;
        }
    }

    public List<Hotel> findByPrixChambresLessThan(double prixMax) {
        return hotelDao.findByPrixChambresLessThan(prixMax);
    }

    public List<Hotel> findByPrixChambresBetween(double prixMin, double prixMax) {
        return hotelDao.findByPrixChambresBetween(prixMin, prixMax);
    }

    public Optional<Hotel> findById(Long id) {
        return hotelDao.findById(id);
    }
}
