package com.project.srv.service;


import com.project.srv.bean.Utilisateur;
import com.project.srv.dao.UtilisateurDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurSevice {

//    public int save(Utilisateur utilisateur) {
//        utilisateurDao.save(utilisateur);
//        return 1;
//    }

    public int save(Utilisateur utilisateur) {
        // Check if a user with the same email already exists
        if (utilisateurDao.findByEmail(utilisateur.getEmail()) != null) {
            return -1; // User already exists
        } else {
            // Encode the password
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encryptedPwd = bCryptPasswordEncoder.encode(utilisateur.getPassword());
            utilisateur.setPassword(encryptedPwd);

            // Save the user
            utilisateurDao.save(utilisateur);
            return 1; // User created successfully
        }
    }


    public int loginUser(String email, String password) {

        Utilisateur utilisateur = findByEmail(email);
        if (utilisateur == null) {
            return -1;
        }else if (!bCryptPasswordEncoder.matches(password, utilisateur.getPassword())) {
            return -2;
        }else {
            if (utilisateur.getEmail().equals("admin@gmail.com")){
                return 1;
            }else {
                return 2;
            }
        }
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return bCryptPasswordEncoder;
    }

    public void setPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurDao.findByEmail(email);
    }

    public Utilisateur findByName(String name) {
        return utilisateurDao.findByName(name);
    }

    public Utilisateur findByAddress(String address) {
        return utilisateurDao.findByAddress(address);
    }

    public Utilisateur findByPhone(String Phone) {
        return utilisateurDao.findByPhone(Phone);
    }

    public List<Utilisateur> findByAge(Integer age) {
        return utilisateurDao.findByAge(age);
    }


    @Transactional
    public Utilisateur deleteByEmail(String email) {
        return utilisateurDao.deleteByEmail(email);
    }

    @Transactional
    public Utilisateur deleteByName(String name) {
        return utilisateurDao.deleteByName(name);
    }

    @Transactional
    public Utilisateur deleteByAddress(String address) {
        return utilisateurDao.deleteByAddress(address);
    }

    @Transactional
    public Utilisateur deleteByPhone(String Phone) {
        return utilisateurDao.deleteByPhone(Phone);
    }

    @Transactional
    public Utilisateur deleteByAge(Integer age) {
        return utilisateurDao.deleteByAge(age);
    }

    public List<Utilisateur> findAll() {
        return utilisateurDao.findAll();
    }

    public long count() {
        return utilisateurDao.count();
    }

    @Transactional
    public void deleteById(Long aLong) {
        utilisateurDao.deleteById(aLong);
    }

    public List<Utilisateur> findAll(Sort sort) {
        return utilisateurDao.findAll(sort);
    }

    //todo
    public void inscrir() {
    }

    public void seConnecter() {
    }

    public void modifierProfile() {
    }
    //end todo

    public Optional<Utilisateur> findById(Long aLong) {
        return utilisateurDao.findById(aLong);
    }


    public int update(Utilisateur utilisateur) {
        Optional<Utilisateur> userOpt = utilisateurDao.findById(utilisateur.getId());
        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            user.setName(utilisateur.getName());
            user.setEmail(utilisateur.getEmail());
            user.setPhone(utilisateur.getPhone());
            user.setProfilePicture(utilisateur.getProfilePicture());
            user.setPassword(utilisateur.getPassword());
            utilisateurDao.save(user);
            return 1; // User updated successfully
        } else {
            utilisateurDao.save(utilisateur);
            return 2; // New user created
        }
    }


    @Autowired
    UtilisateurDao utilisateurDao;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

}
