package com.project.srv.dao;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VolRepository extends JpaRepository<Vol, Long> {

    List<Vol> findByDestination(String destination);

    List<Vol> findByHeureDepart(LocalDate heureDepart);

    List<Vol> findByHeureArrivee(LocalDate heureArrivee);

    List<Vol> findByPrix(float prix);

    List<Vol> findByVille(Ville ville);


    void deleteByDestination(String destination);

    void deleteByHeureDepart(LocalDate heureDepart);

    void deleteByHeureArrivee(LocalDate heureArrivee);

    void deleteByPrix(float prix);

    void deleteByVille(Ville ville);

}
