package com.project.srv.dao;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolDao extends JpaRepository<Vol, Long> {

    List<Vol> findByOrigin(Ville origin);
    List<Vol> findByDestination(Ville destination);

    List<Vol> findByHeureDepart(LocalDate heureDepart);

    List<Vol> findByHeureArrivee(LocalDate heureArrivee);

    List<Vol> findByPrix(float prix);

    List<Vol> findVolByOriginAndDestination(Ville origin, Ville destination);
    List<Vol> findByHeureDepartAndHeureArrivee(LocalDate heureDepart, LocalDate heureArrivee);

    @Query("SELECT v FROM Vol v WHERE v.origin = :origin AND v.destination = :destination AND v.heureDepart >= :departDate AND v.heureArrivee <= :arriveeDate")
    List<Vol> searchByAll(
            @Param("origin") Ville origin,
            @Param("destination") Ville destination,
            @Param("departDate") LocalDate departDate,
            @Param("arriveeDate") LocalDate arriveeDate);


    void deleteByOrigin(Ville origin);
    void deleteByDestination(Ville destination);

    void deleteByHeureDepart(LocalDate heureDepart);

    void deleteByHeureArrivee(LocalDate heureArrivee);

    void deleteByPrix(float prix);


}
