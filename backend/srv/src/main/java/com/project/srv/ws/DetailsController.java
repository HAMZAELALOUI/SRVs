package com.project.srv.ws;

import com.project.srv.bean.Details;
import com.project.srv.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Details")
public class DetailsController {

    @Autowired
    private DetailsService detailsService;

    @GetMapping("/participants/{nombreParticipants}")
    public List<Details> findByNombreParticipants(@PathVariable int nombreParticipants) {
        return detailsService.findByNombreParticipants(nombreParticipants);
    }

    @GetMapping("/prix/{prixTotalActivite}")
    public List<Details> findByPrixTotalActivite(@PathVariable double prixTotalActivite) {
        return detailsService.findByPrixTotalActivite(prixTotalActivite);
    }

    @DeleteMapping("/participants/{nombreParticipants}")
    @Transactional
    public void deleteByNombreParticipants(@PathVariable int nombreParticipants) {
        detailsService.deleteByNombreParticipants(nombreParticipants);
    }

    @DeleteMapping("/prix/{prixTotalActivite}")
    @Transactional
    public void deleteByPrixTotalActivite(@PathVariable double prixTotalActivite) {
        detailsService.deleteByPrixTotalActivite(prixTotalActivite);
    }

    // Vous pouvez ajouter d'autres méthodes de web service personnalisées ici si nécessaire
}
