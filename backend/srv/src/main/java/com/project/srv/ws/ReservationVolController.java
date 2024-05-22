package com.project.srv.ws;

import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Vol;
import com.project.srv.service.ReservationVolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Gestion-Vol/ReservationVol")
public class ReservationVolController {

    @Autowired
    private ReservationVolService reservationVolService;

    @GetMapping("/vol/{volId}")
    public ReservationVol findByVolId(@PathVariable Long volId) {
        return reservationVolService.findByVolId(volId);
    }

    @GetMapping("/{id}")
    public ReservationVol getReservationVolById(@PathVariable Long id) {
        return reservationVolService.findReservationVolById(id);
    }

    @DeleteMapping("/vol/{volId}")
    @Transactional
    public void deleteByVolId(@PathVariable Long volId) {
        reservationVolService.deleteByVolId(volId);
    }

    @GetMapping("/vol")
    public ReservationVol findByVol(@RequestBody Vol vol) {
        return reservationVolService.findByVol(vol);
    }

    @PutMapping("/update")
    public ReservationVol updateReservationVol(@RequestBody ReservationVol reservationVol) {
        return reservationVolService.updateReservation(reservationVol);
    }

    @PostMapping("/save")
    public ReservationVol saveReservationVol(@RequestBody ReservationVol reservationVol) {
        return reservationVolService.saveReservation(reservationVol);
    }
}
