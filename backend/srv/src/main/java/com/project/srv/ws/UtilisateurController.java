package com.project.srv.ws;


import com.project.srv.bean.Utilisateur;
import com.project.srv.service.UtilisateurSevice;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/srv/utilisateur")
public class UtilisateurController {

    @PostMapping
    public int save(@RequestBody Utilisateur utilisateur) {
        return utilisateurSevice.save(utilisateur);
    }

    @GetMapping("/email/{email}")
    public Utilisateur findByEmail(@PathVariable String email) {
        return utilisateurSevice.findByEmail(email);
    }

    @GetMapping("/name/{name}")
    public Utilisateur findByName(@PathVariable String name) {
        return utilisateurSevice.findByName(name);
    }


    @GetMapping("/address/{address}")
    public Utilisateur findByAddress(@PathVariable String address) {
        return utilisateurSevice.findByAddress(address);
    }

    @GetMapping("/Phone/{Phone}")
    public Utilisateur findByPhone(@PathVariable String Phone) {
        return utilisateurSevice.findByPhone(Phone);
    }

    @GetMapping("/age/{age}")
    public List<Utilisateur> findByAge(@PathVariable Integer age) {
        return utilisateurSevice.findByAge(age);
    }

    @Transactional
    @DeleteMapping("/email/{email}")
    public Utilisateur deleteByEmail(@PathVariable String email) {
        return utilisateurSevice.deleteByEmail(email);
    }

    @Transactional
    @DeleteMapping("/name/{name}")
    public Utilisateur deleteByName(@PathVariable String name) {
        return utilisateurSevice.deleteByName(name);
    }

    @Transactional
    @DeleteMapping("/address/{address}")
    public Utilisateur deleteByAddress(@PathVariable String address) {
        return utilisateurSevice.deleteByAddress(address);
    }

    @Transactional
    @DeleteMapping("/Phone/{Phone}")
    public Utilisateur deleteByPhone(String Phone) {
        return utilisateurSevice.deleteByPhone(Phone);
    }

    @Transactional
    @DeleteMapping("/age/{age}")
    public Utilisateur deleteByAge(Integer age) {
        return utilisateurSevice.deleteByAge(age);
    }

    @GetMapping
    public List<Utilisateur> findAll() {
        return utilisateurSevice.findAll();
    }

    public long count() {
        return utilisateurSevice.count();
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable("id") Long aLong) {
        utilisateurSevice.deleteById(aLong);
    }



    public void inscrir() {
        utilisateurSevice.inscrir();
    }

    public void seConnecter() {
        utilisateurSevice.seConnecter();
    }

    public void modifierProfile() {
        utilisateurSevice.modifierProfile();
    }

    @GetMapping("/id/{id}")
    public Optional<Utilisateur> findById(Long aLong) {
        return utilisateurSevice.findById(aLong);
    }

    @Autowired
    private UtilisateurSevice utilisateurSevice;
}
