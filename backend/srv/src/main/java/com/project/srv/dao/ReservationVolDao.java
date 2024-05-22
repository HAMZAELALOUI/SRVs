package com.project.srv.dao;

import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationVolDao extends JpaRepository<ReservationVol, Long> {
    ReservationVol findByVolId(Long volId);

    void deleteByVolId(Long volId);

    ReservationVol findReservationVolById(Long id); // Correction ici

    ReservationVol findByVol(Vol vol);

    ReservationVol findReservationById(Long id); // Pas de changement ici
}
