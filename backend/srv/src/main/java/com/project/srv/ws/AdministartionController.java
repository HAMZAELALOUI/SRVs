package com.project.srv.ws;

import com.project.srv.bean.Administration;
import com.project.srv.service.AdministartionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password) {
        boolean isAuthenticated = administartionService.authenticate(name, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @Autowired
    private AdministartionService administartionService;
}
