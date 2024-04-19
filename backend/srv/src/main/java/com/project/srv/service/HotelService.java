package com.project.srv.service;

import com.project.srv.bean.Hotel;
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


    public int saveHotel(Hotel hotel) {
        if (hotel.getId() != null && hotelDao.existsById(hotel.getId())) {
            return -1;
        }
        if (hotel.getNom() == null || hotel.getNom().isEmpty()) {
            // Gérer les cas où le nom de l'hôtel est vide ou nul
        }

        if (hotel.getEmplacement() == null || hotel.getEmplacement().isEmpty()) {
            return -3;
        }
        int nombreEtoiles = hotel.getNombreEtoiles();
        if (nombreEtoiles < 1 || nombreEtoiles > 5) {
            return -4;
        }
        if (hotel.getPrixChambres() <= 0) {
            return -5;
        }
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
