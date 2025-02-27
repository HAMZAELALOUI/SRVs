package com.project.srv.dao;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Activite;
import com.project.srv.bean.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VilleDao extends JpaRepository<Ville, Long> {

    Optional<Ville> findById(Long Id);
    List<Ville> findByNom(String nom);
    List<Ville> findAll();
    List<Ville>  findIdByNom(String nom);
    List<Ville> findByPays(String pays);

    List<Ville> findByNomContainingIgnoreCase(String nom);

    Optional<Ville> findByNomAndPays(String nom, String pays);

    void deleteByNom(String nom);

    void deleteByPays(String pays);

    void deleteByNomContainingIgnoreCase(String nom);

    void deleteByNomAndPays(String nom, String pays);

}
