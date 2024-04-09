package com.project.srv.ws;

import com.project.srv.bean.ActiviteBean;
import com.project.srv.service.ActiviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gestion-Vol/Activite")
public class ActiviteWs {

    @Autowired
    private ActiviteService activiteService;
    @PostMapping("/save")
    public int saveActivite(@RequestBody ActiviteBean activite) {
       return activiteService.saveActivite(activite);
    }
    @PutMapping("/update")
    public int updateActivite(@RequestBody ActiviteBean activite) {
     return  activiteService.updateActivite(activite);
    }


    @GetMapping("/nom/{nom}")
    public List<ActiviteBean> findByNom(@PathVariable String nom) {
        return activiteService.findByNom(nom);
    }

    @GetMapping("/lieu/{lieu}")
    public List<ActiviteBean> findByLieu(@PathVariable String lieu) {
        return activiteService.findByLieu(lieu);
    }

    @GetMapping("/description/{description}")
    public List<ActiviteBean> findByDescription(@PathVariable String description) {
        return activiteService.findByDescription(description);
    }

    @GetMapping("/horaire/{horaire}")
    public List<ActiviteBean> findByHoraire(@PathVariable String horaire) {
        return activiteService.findByHoraire(horaire);
    }

    @GetMapping("/prix/{prix}")
    public List<ActiviteBean> findByPrix(@PathVariable double prix) {
        return activiteService.findByPrix(prix);
    }

    @DeleteMapping("/nom/{nom}")
    @Transactional
    public void deleteByNom(@PathVariable String nom) {
        activiteService.deleteByNom(nom);
    }

    @DeleteMapping("/lieu/{lieu}")
    @Transactional
    public void deleteByLieu(@PathVariable String lieu) {
        activiteService.deleteByLieu(lieu);
    }

    @DeleteMapping("/description/{description}")
    @Transactional
    public void deleteByDescription(@PathVariable String description) {
        activiteService.deleteByDescription(description);
    }

    @DeleteMapping("/horaire/{horaire}")
    @Transactional
    public void deleteByHoraire(@PathVariable String horaire) {
        activiteService.deleteByHoraire(horaire);
    }

    @DeleteMapping("/prix/{prix}")
    @Transactional
    public void deleteByPrix(@PathVariable double prix) {
        activiteService.deleteByPrix(prix);
    }
    @GetMapping("/prix-inferieur/{prix}")
    public List<ActiviteBean> findByPrixLessThan(@PathVariable double prix) {
        return activiteService.findByPrixLessThan(prix);
    }

    // Vous pouvez ajouter d'autres méthodes de web service personnalisées ici si nécessaire
}
