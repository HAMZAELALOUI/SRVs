package com.project.srv.ws;

import com.project.srv.bean.Vol;
import com.project.srv.dao.VolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vols")
public class VolController {

    @Autowired
    private VolRepository volRepository;

    // Get all vols
    @GetMapping
    public List<Vol> getAllVols() {
        return volRepository.findAll();
    }

    // Get a single vol by id
    @GetMapping("/{id}")
    public ResponseEntity<Vol> getVolById(@PathVariable Long id) {
        Optional<Vol> vol = volRepository.findById(id);
        return vol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new vol
    @PostMapping
    public Vol createVol(@RequestBody Vol vol) {
        return volRepository.save(vol);
    }

    // Update a vol
    @PutMapping("/{id}")
    public ResponseEntity<Vol> updateVol(@PathVariable Long id, @RequestBody Vol volDetails) {
        return volRepository.findById(id).map(existingVol -> {
            existingVol.setDestination(volDetails.getDestination());
            existingVol.setPrix(volDetails.getPrix());
            existingVol.setHeureDepart(volDetails.getHeureDepart());
            existingVol.setHeureArrivee(volDetails.getHeureArrivee());
            existingVol.setPlacesDisponibles(volDetails.getPlacesDisponibles());
            Vol updatedVol = volRepository.save(existingVol);
            return ResponseEntity.ok(updatedVol);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a vol
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVol(@PathVariable Long id) {
        return volRepository.findById(id).map(vol -> {
            volRepository.delete(vol);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
