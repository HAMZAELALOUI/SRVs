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
    @DeleteMapping("/email/{email}")
    @Transactional
    public void deleteByEmail(@PathVariable String email) {
        utilisateurSevice.deleteByEmail(email);
    }

    @DeleteMapping("/name/{name}")
    @Transactional
    public void deleteByName(@PathVariable("name") String name) {
        utilisateurSevice.deleteByName(name);
    }

    @DeleteMapping("/address/{address}")
    @Transactional
    public void deleteByAddress(@PathVariable String address) {
         utilisateurSevice.deleteByAddress(address);
    }


    @DeleteMapping("/Phone/{Phone}")
    @Transactional
    public void deleteByPhone(@PathVariable String Phone) {
        utilisateurSevice.deleteByPhone(Phone);
    }

    @DeleteMapping("/age/{age}")
    @Transactional
    public void deleteByAge(@PathVariable Integer age) {
        utilisateurSevice.deleteByAge(age);
    }

    @GetMapping
    public List<Utilisateur> findAll() {
        return utilisateurSevice.findAll();
    }

    public long count() {
        return utilisateurSevice.count();
    }


    @DeleteMapping("/id/{id}")
    @Transactional
    public void deleteById(@PathVariable("id") Long aLong) {
        utilisateurSevice.deleteById(aLong);
    }




    @GetMapping("/id/{id}")
    public Optional<Utilisateur> findById(@PathVariable("id") Long aLong) {
        return utilisateurSevice.findById(aLong);
    }

    @GetMapping("/login/email/{email}/password/{password}")
    public int loginUser(@PathVariable String email,@PathVariable String password) {
        return utilisateurSevice.loginUser(email, password);
    }


    @PutMapping("/updateUtilisateur")
    public int update(@RequestBody Utilisateur utilisateur) {
        return utilisateurSevice.update(utilisateur);
    }

    @Autowired
    private UtilisateurSevice utilisateurSevice;
}
