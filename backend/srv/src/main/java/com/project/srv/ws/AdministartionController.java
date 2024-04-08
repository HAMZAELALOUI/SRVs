package com.project.srv.ws;

import com.project.srv.bean.Administration;
import com.project.srv.service.AdministartionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/srv/admin")
public class AdministartionController {

@GetMapping("/name/{name}")
    public Administration findByName(@PathVariable String name) {
        return administartionService.findByName(name);
    }

   @GetMapping("/id/{id}")
    public Optional<Administration> findById(@PathVariable("id") Long aLong) {
        return administartionService.findById(aLong);
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long aLong) {
        administartionService.deleteById(aLong);
    }
    public void seConnecter() {
        administartionService.seConnecter();
    }

    public void gererUtilsateurs() {
        administartionService.gererUtilsateurs();
    }

    public void gererVoyages() {
        administartionService.gererVoyages();
    }

    public void gererReservations() {
        administartionService.gererReservations();
    }

    public void gererPaiement() {
        administartionService.gererPaiement();
    }

    public void gererActivities() {
        administartionService.gererActivities();
    }

    public void gererHotels() {
        administartionService.gererHotels();
    }

    public void gererVols() {
        administartionService.gererVols();
    }



    @Autowired
    private AdministartionService administartionService;
}
