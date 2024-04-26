package com.project.srv.service;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Ville;
import com.project.srv.dao.HotelDao;
import com.project.srv.dao.VilleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {


    @Autowired
    private HotelDao hotelDao;
    @Autowired
    private VilleDao villeDao;

    public String getImagePath() {
        // Implémentez cette méthode pour récupérer le chemin de l'image depuis la base de données
        // Par exemple, utilisez hotelRepository.findBy...() pour récupérer le chemin
        return "src/images/image1.png"; // Retourne le chemin de l'image
    }

    public Optional<Hotel> findActiviteWithVilleById(Long hotelId) {
        return hotelDao.findActiviteWithVilleById(hotelId);
    }

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

    public List<Hotel> findByVille(Ville ville) {
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

    public List<Hotel> findByNomVille(String nomVille) {
        return hotelDao.findByVilleNom(nomVille);
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


    public List<Hotel> rechercherParNomVilleEtDateAetDateD(String nomVille, String dateA, String dateD) {
        Ville ville = trouverVilleParNom(nomVille);
        if (ville != null) {
            return hotelDao.findByVilleAndDateAAndDateD( ville, dateA, dateD);
        } else {
            return null;
        }
    }
    public List<Hotel> findByDateAAndDateD( String dateA, String dateD) {
        return hotelDao.findByDateAAndDateD(dateA, dateD);
    }


    private Ville trouverVilleParNom(String nomVille) {
        List<Ville> villes = villeDao.findByNom(nomVille);
        if (!villes.isEmpty()) {
            return villes.get(0);
        } else {
            // Gérer le cas où la ville n'est pas trouvée
            return null;
        }
    }

}
