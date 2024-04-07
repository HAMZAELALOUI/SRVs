package com.project.srv.ws;

import com.project.srv.bean.Ville;
import com.project.srv.dao.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/villes")
public class VilleController {

    @Autowired
    private VilleRepository villeRepository;

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



    // Create a new city
    @PostMapping
    public Ville createVille(@RequestBody Ville ville) {
        return villeRepository.save(ville);
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

}
