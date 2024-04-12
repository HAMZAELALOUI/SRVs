package com.project.srv.ws;

import com.project.srv.bean.Ville;
import com.project.srv.dao.VilleRepository;
import com.project.srv.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/srv/villes")
public class VilleController {

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private VilleService villeService;

    // Get all cities
    @GetMapping
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }

    // Get a single city by id
    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleById(@PathVariable Long id) {
        Optional<Ville> ville = villeRepository.findById(id);
        return ville.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{nom}")
    public List<Ville> getByNom(@PathVariable String nom) {
        return villeService.findByNom(nom);
    }

    @GetMapping("/{pays}")
    public List<Ville> findByPays(@PathVariable String pays) {
        return villeService.findByPays(pays);
    }

    @GetMapping("/nomIg/{nom}")
    public List<Ville> findByNomContainingIgnoreCase(@PathVariable String nom) {
        return villeService.findByNomContainingIgnoreCase(nom);
    }


    // Create a new city
    @PostMapping
    public Ville save(@RequestBody Ville ville) {
        return villeService.save(ville);
    }

    // Update a city
    @PutMapping("/{id}")
    public ResponseEntity<Ville> updateVille(@PathVariable Long id, @RequestBody Ville villeDetails) {
        return villeRepository.findById(id).map(existingVille -> {
            existingVille.setNom(villeDetails.getNom());
            existingVille.setPays(villeDetails.getPays());
            Ville updatedVille = villeRepository.save(existingVille);
            return ResponseEntity.ok(updatedVille);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a city
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVille(@PathVariable Long id) {
        return villeRepository.findById(id).map(ville -> {
            villeRepository.delete(ville);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{nom}")
    public void deleteVilleByNom(@PathVariable String nom) {
        villeService.deleteVilleByNom(nom);
    }

    @DeleteMapping("/{pays}")
    public void deleteVilleByPays(@PathVariable String pays) {
        villeService.deleteVilleByPays(pays);
    }

    @DeleteMapping("/")
    public void deleteAllVilles() {
        villeService.deleteAllVilles();
    }

}
