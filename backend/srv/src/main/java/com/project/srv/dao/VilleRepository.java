package com.project.srv.dao;

import com.project.srv.bean.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {

    List<Ville> findByNom(String nom);

    List<Ville> findByPays(String pays);

    List<Ville> findByNomContainingIgnoreCase(String nom);

    List<Ville> findByNomAndPays(String nom, String pays);

}
