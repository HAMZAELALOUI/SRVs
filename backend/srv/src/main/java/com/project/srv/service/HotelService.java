package com.project.srv.service;


import com.project.srv.bean.Hotel;
import com.project.srv.dao.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> findByEmplacement(String emplacement) {
        return hotelRepository.findByEmplacement(emplacement);
    }

    public List<Hotel> findByNombreEtoiles(int nombreEtoiles) {
        return hotelRepository.findByNombreEtoiles(nombreEtoiles);
    }

    public List<Hotel> findHotelByReservationId(Long reservationId) {
        return hotelRepository.findHotelByReservationsId(reservationId);
    }

    public List<Hotel> findByNom(String nom) {
        return hotelRepository.findByNom(nom);
    }

    @Transactional
    public void deleteByEmplacement(String emplacement) {
        hotelRepository.deleteByEmplacement(emplacement);
    }

    @Transactional
    public void deleteByNombreEtoiles(int nombreEtoiles) {
        hotelRepository.deleteByNombreEtoiles(nombreEtoiles);
    }

    @Transactional
    public void deleteByNom(String nom) {
        hotelRepository.deleteByNom(nom);
    }


    public int saveHotel(Hotel hotel) {
        if (hotel.getId() != null && hotelRepository.existsById(hotel.getId())) {
            return -1;
        }
        if (hotel.getNom() == null || hotel.getNom().isEmpty()) {
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
        hotelRepository.save(hotel);
        return 1;
    }

    public int updateHotel(Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(hotel.getId()).orElse(null);
        if (existingHotel != null) {
            existingHotel.setNom(hotel.getNom());
            existingHotel.setEmplacement(hotel.getEmplacement());
            existingHotel.setDescription(hotel.getDescription());
            existingHotel.setNombreEtoiles(hotel.getNombreEtoiles());
            existingHotel.setPrixChambres(hotel.getPrixChambres());

            hotelRepository.save(existingHotel);
            return 1;
        } else {
            hotelRepository.save(hotel);
            return 2;
        }
    }
    public List<Hotel> findByPrixChambresLessThan(double prixMax) {
        return hotelRepository.findByPrixChambresLessThan(prixMax);
    }
    public List<Hotel> findByPrixChambresBetween(double prixMin, double prixMax) {
        return hotelRepository.findByPrixChambresBetween(prixMin, prixMax);
    }


}
