package com.project.srv.ws;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import com.project.srv.service.VolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/srv/vols")
public class VolController {

    @Autowired
    private VolDao volDao;
    @Autowired
    private VolService volService;

    // Get all vols
    @GetMapping
    public List<Vol> getAllVols() {
        return volDao.findAll();
    }

    // Get a single vol by id
    @GetMapping("/id/{id}")
    public ResponseEntity<Vol> getVolById(@PathVariable Long id) {
        Optional<Vol> vol = volDao.findById(id);
        return vol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/destination/{destination}")
    public List<Vol> findByDestination(@PathVariable String destination) {
        return volService.findByDestination(destination);
    }




    @GetMapping("/heureDep/{heureDepart}")

    public List<Vol> findByHeureDepart(@PathVariable LocalDate heureDepart) {
        return volService.findByHeureDepart(heureDepart);
    }

    @GetMapping("/heureArr/{heureArrivee}")
    public List<Vol> findByHeureArrivee(@PathVariable LocalDate heureArrivee) {
        return volService.findByHeureArrivee(heureArrivee);
    }

    @GetMapping("/prix/{prix}")
    public List<Vol> findByPrix(@PathVariable float prix) {
        return volService.findByPrix(prix);
    }

    @GetMapping("/ville/{ville}")
    public List<Vol> findByVille(@PathVariable Ville ville) {
        return volService.findByVille(ville);
    }

    // Create a new vol
    @PostMapping
    public int save(@RequestBody Vol vol) {
        return volService.save(vol);
    }

    // Update a vol
    @PutMapping("id/{id}")
    public ResponseEntity<Vol> updateVol(@PathVariable Long id, @RequestBody Vol volDetails) {
        return volDao.findById(id).map(existingVol -> {
            existingVol.setDestination(volDetails.getDestination());
            existingVol.setPrix(volDetails.getPrix());
            existingVol.setHeureDepart(volDetails.getHeureDepart());
            existingVol.setHeureArrivee(volDetails.getHeureArrivee());
            existingVol.setPlacesDisponibles(volDetails.getPlacesDisponibles());
            Vol updatedVol = volDao.save(existingVol);
            return ResponseEntity.ok(updatedVol);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a vol
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteVol(@PathVariable Long id) {
        return volDao.findById(id).map(vol -> {
            volDao.delete(vol);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{destination}")
    public void deleteVolByDestination(@PathVariable String destination) {
        volService.deleteVolByDestination(destination);
    }

    @DeleteMapping("/{heureDepart}")
    public void deleteVolByHeureDepart(@PathVariable LocalDate heureDepart) {
        volService.deleteVolByHeureDepart(heureDepart);
    }


    @DeleteMapping("/{heureArrivee}")
    public void deleteVolByArrivee(@PathVariable LocalDate heureArrivee) {
        volService.deleteVolByHeureArrivee(heureArrivee);
    }

    @DeleteMapping("/{prix}")
    public void deleteVolByPrix(@PathVariable float prix) {
        volService.deleteVolByPrix(prix);
    }

    @DeleteMapping("/")
    public void deleteAllVols() {
        volService.deleteAllVols();
    }










}
