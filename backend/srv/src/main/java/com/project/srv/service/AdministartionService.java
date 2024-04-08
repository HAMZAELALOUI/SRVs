package com.project.srv.service;


import com.project.srv.bean.Administration;
import com.project.srv.dao.AdministartionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdministartionService {


    //todo
    public void seConnecter() {
    }

    public void gererUtilsateurs() {
    }

    public void gererVoyages() {
    }

    public void gererReservations() {
    }

    public void gererPaiement() {
    }

    public void gererActivities() {
    }

    public void gererHotels() {
    }

    public void gererVols() {
    }
    //end todo


    public Administration findByName(String name) {
        return administartionDao.findByName(name);
    }

    public Optional<Administration> findById(Long aLong) {
        return administartionDao.findById(aLong);
    }

    @Transactional
    public void deleteById(Long aLong) {
        administartionDao.deleteById(aLong);
    }

    @Autowired
    AdministartionDao administartionDao;
}
