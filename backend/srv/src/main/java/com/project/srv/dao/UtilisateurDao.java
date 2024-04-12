package com.project.srv.dao;

import com.project.srv.bean.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);

    Utilisateur findByName(String name);

    Utilisateur findByAddress(String address);

    Utilisateur findByPhone(String Phone);

    List<Utilisateur> findByAge(Integer age);

    void deleteByEmail(String email);

    void deleteByName(String name);

    void deleteByAddress(String address);

    void deleteByPhone(String Phone);

    void deleteByAge(Integer age);


}
