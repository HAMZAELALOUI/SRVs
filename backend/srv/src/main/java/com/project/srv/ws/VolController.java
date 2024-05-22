package com.project.srv.ws;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import com.project.srv.exeption.InvalidDataException;
import com.project.srv.service.VilleService;
import com.project.srv.service.VolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/srv/vols")
public class VolController {

    @Autowired
    private VolDao volDao;
    @Autowired
    private VolService volService;
    @Autowired
    private VilleService villeService;

    // Get all vols
    @GetMapping("/")
    public List<Vol> getAllVols() {
        return volDao.findAll();
    }


    // Get a single vol by id
    @GetMapping("/id/{id}")
    public ResponseEntity<Vol> getVolById(@PathVariable Long id) {
        Optional<Vol> vol = volDao.findById(id);
        return vol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/origin/id/{originId}")
    public List<Vol> findByOrigin(@PathVariable Long originId) {
        Optional<Ville> origin = villeService.findById(originId);
        return volService.findByOrigin(origin.get());
    }

    @GetMapping("/origin/nom/{nom}")
    public List<Vol> findByOriginNom(@PathVariable String nom) {
        List<Ville> origins = villeService.findByNom(nom);
        if (origins.isEmpty()) {
            return volService.findByOriginName((Ville) origins);
        }
        List<Vol> vols = new ArrayList<>();
        for (Ville origin : origins) {
            vols.addAll(volService.findByOrigin(origin));
        }
        return vols;
    }

    @GetMapping("/origin/pays/{pays}")
    public List<Vol> findByOriginPays(@PathVariable String pays) {
        List<Ville> origins = villeService.findByPays(pays);
        if (origins.isEmpty()) {
            return volService.findByOriginPays((Ville) origins);
        }
        List<Vol> vols = new ArrayList<>();
        for (Ville origin : origins) {
            vols.addAll(volService.findByOrigin(origin));
        }
        return vols;
    }

    @GetMapping("/destination/{destinationId}")
    public List<Vol> findByDestination(@PathVariable Long destinationId) {
        Optional<Ville> destination = villeService.findById(destinationId);
        return volService.findByDestination(destination.get());
    }

    @GetMapping("/destination/nom/{nom}")
    public List<Vol> findByDestinationNom(@PathVariable String nom) {
        List<Ville> destinations = villeService.findByNom(nom);
        if (destinations.isEmpty()) {
            return volService.findByDestinatiomName((Ville) destinations);
        }
        List<Vol> vols = new ArrayList<>();
        for (Ville destination : destinations) {
            vols.addAll(volService.findByDestination(destination));
        }
        return vols;
    }

    @GetMapping("/destination/pays/{pays}")
    public List<Vol> findByDestinationPays(@PathVariable String pays) {
        List<Ville> destinations = villeService.findByPays(pays);
        if (destinations.isEmpty()) {
            return volService.findByDestinatiomPays((Ville) destinations);
        }
        List<Vol> vols = new ArrayList<>();
        for (Ville destination : destinations) {
            vols.addAll(volService.findByDestination(destination));
        }
        return vols;
    }


    @GetMapping("/heureDep/{heureDepart}")

    public List<Vol> findByHeureDepart(@PathVariable LocalDate heureDepart) {
        return volService.findByHeureDepart(heureDepart);
    }

    @GetMapping("/heureArr/{heureArrivee}")
    public List<Vol> findByHeureArrivee(@PathVariable LocalDate heureArrivee) {
        return volService.findByHeureArrivee(heureArrivee);
    }

    @GetMapping("/searchByDates")
    public ResponseEntity<List<Vol>> findByHeureDepartAndHeureArrivee(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arriveeDate) {
        List<Vol> vols = volService.findByHeureDepartAndHeureArrivee(departDate, arriveeDate);
        return ResponseEntity.ok(vols);
    }


    @GetMapping("/searchByAll")
    public ResponseEntity<List<Vol>> searchByAll(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arriveeDate) {

        List<Ville> origins = villeService.findByNom(origin);
        List<Ville> destinations = villeService.findByNom(destination);

        List<Vol> vols = new ArrayList<>();
        if (!origins.isEmpty() && !destinations.isEmpty()) {
            Ville originVille = origins.get(0);
            Ville destinationVille = destinations.get(0);
            vols = volService.searchByAll(originVille, destinationVille, departDate, arriveeDate);
        }

        if( !origins.isEmpty()) {
            Ville originVille = origins.get(0);
            vols = volService.findByOrigin(originVille);
        }

        // If no results found by name, fall back to date search
        if (vols.isEmpty()) {
            vols = volService.findByHeureDepartAndHeureArrivee(departDate, arriveeDate);
        }

        if (vols.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vols);
    }

    @GetMapping("/prix/{prix}")
    public List<Vol> findByPrix(@PathVariable float prix) {
        return volService.findByPrix(prix);
    }

    // Create a new vol
    @PutMapping(value = "id/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateVol(
            @PathVariable Long id,
            @RequestParam("originId") Long originId,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam("heureDepart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate heureDepart,
            @RequestParam("heureArrivee") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate heureArrivee,
            @RequestParam("prix") float prix,
            @RequestParam("placesDisponibles") int placesDisponibles,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return volDao.findById(id).map(existingVol -> {
            Ville origin = villeService.findById(originId).orElseThrow(() -> new RuntimeException("Origin not found"));
            Ville destination = villeService.findById(destinationId).orElseThrow(() -> new RuntimeException("Destination not found"));

            // Update existingVol details
            existingVol.setOrigin(origin);
            existingVol.setDestination(destination);
            existingVol.setHeureDepart(heureDepart);
            existingVol.setHeureArrivee(heureArrivee);
            existingVol.setPrix(prix);
            existingVol.setPlacesDisponibles(placesDisponibles);

            // Handle image upload
            if (image != null && !image.isEmpty()) {
                String filePath = volService.storeFile(image);
                existingVol.setImageUrl(filePath);
            }

            Vol updatedVol = volDao.save(existingVol);
            return ResponseEntity.ok(updatedVol);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ResponseEntity<?> createVol(
            @RequestParam("originId") Long originId,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam("heureDepart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate heureDepart,
            @RequestParam("heureArrivee") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate heureArrivee,
            @RequestParam("prix") float prix,
            @RequestParam("placesDisponibles") int placesDisponibles,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Ville origin = villeService.findById(originId)
                .orElseThrow(() -> new RuntimeException("Origin not found"));
        Ville destination = villeService.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Vol newVol = new Vol();
        newVol.setOrigin(origin);
        newVol.setDestination(destination);
        newVol.setHeureDepart(heureDepart);
        newVol.setHeureArrivee(heureArrivee);
        newVol.setPrix(prix);
        newVol.setPlacesDisponibles(placesDisponibles);

        // Handle image upload
        if (image != null && !image.isEmpty()) {
            String filePath = volService.storeFile(image);
            newVol.setImageUrl(filePath);
        }

        Vol savedVol = volDao.save(newVol);
        return ResponseEntity.ok(savedVol);

    }
    // Update a vol

    // Delete a vol
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteVol(@PathVariable Long id) {
        try {
            volService.deleteVolById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/origin/{origin}")
    public void deleteVolByOrigin(@PathVariable Ville origin) {
        volService.deleteVolByOrigin(origin);
    }

    @DeleteMapping("/destination/{destination}")
    public void deleteVolByDestination(@PathVariable Ville destination) {
        volService.deleteVolByDestination(destination);
    }

    @DeleteMapping("/heureDep/{heureDepart}")
    public void deleteVolByHeureDepart(@PathVariable LocalDate heureDepart) {
        volService.deleteVolByHeureDepart(heureDepart);
    }


    @DeleteMapping("/heureArr/{heureArrivee}")
    public void deleteVolByArrivee(@PathVariable LocalDate heureArrivee) {
        volService.deleteVolByHeureArrivee(heureArrivee);
    }

    @DeleteMapping("/prix/{prix}")
    public void deleteVolByPrix(@PathVariable float prix) {
        volService.deleteVolByPrix(prix);
    }

    @DeleteMapping("/")
    public void deleteAllVols() {
        volService.deleteAllVols();
    }










}
