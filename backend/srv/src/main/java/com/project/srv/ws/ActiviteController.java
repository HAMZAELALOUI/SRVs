package com.project.srv.ws;

import com.project.srv.bean.Activite;
import com.project.srv.bean.Ville;
import com.project.srv.service.ActiviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/GestionVol/Activite")
public class ActiviteController {

    @Autowired
    private ActiviteService activiteService;

    @GetMapping("/activity/ActiviteDetails/{activiteId}")
    public Optional<Activite> findActiviteWithVilleById(@PathVariable Long activiteId) {
        return activiteService.findActiviteWithVilleById(activiteId);
    }
    @PostMapping("/save")
    public int saveActivite(@RequestBody Activite activite) {
       return activiteService.saveActivite(activite);
    }
    @PutMapping("/update/{id}")
    public int updateActivite(@PathVariable Long id,@RequestBody Activite activite) {
     return  activiteService.updateActivite(id, activite);
    }

    @GetMapping("/ville/{nomVille}")
    public List<Activite> findByNomVille(@PathVariable String nomVille) {
        return activiteService.findByNomVille(nomVille);
    }
    @GetMapping("/")
    public List<Activite> findAll() {
        return activiteService.findAll();
    }
    @GetMapping("/prixactivite/between/{prixMin}/{prixMax}")
    public List<Activite> findByPrixActiviteBetween(@PathVariable double prixMin, @PathVariable double prixMax) {
        return activiteService.findByPrixActviteBetween(prixMin,prixMax);
}
    @GetMapping("/Id/{id}")
    public Optional<Activite> findById(@PathVariable long id) {
        return activiteService.findById(id);
    }

    @GetMapping("/nom/{nom}")
    public List<Activite> findByNom(@PathVariable String nom) {
        return activiteService.findByNom(nom);
    }

    @GetMapping("/lieu/{lieu}")
    public List<Activite> findByLieu(@PathVariable String lieu) {
        return activiteService.findByLieu(lieu);
    }

    @GetMapping("/description/{description}")
    public List<Activite> findByDescription(@PathVariable String description) {
        return activiteService.findByDescription(description);
    }


    @GetMapping("/horaire/{horaire}")
    public List<Activite> findByHoraire(@PathVariable String horaire) {
        return activiteService.findByHoraire(horaire);
    }

    @GetMapping("/prix/{prix}")
    public List<Activite> findByPrix(@PathVariable double prix) {
        return activiteService.findByPrix(prix);
    }

    @DeleteMapping("/Id/{id}")
    @Transactional
    public void deleteById(@PathVariable long id) {
        activiteService.deleteById(id);
    }
    @DeleteMapping("/nom/{nom}")
    @Transactional
    public void deleteByNom(@PathVariable String nom) {
        activiteService.deleteByNom(nom);
    }
    @GetMapping("/jj/{ville}")
    public List<Activite> findByVille(@PathVariable Ville ville) {
        return activiteService.findByVille(ville);
    }
    @GetMapping("/villeString/{nomVille}/horaire/{horaire}")
    public List<Activite> findByVilleAndHoraireString(@PathVariable String nomVille, @PathVariable String horaire) {
        return activiteService.rechercherParNomVilleEtHoraire(nomVille, horaire);
    }


    @GetMapping("/ville/{nomVille}/horaire/{horaire}")
    public List<Activite> findByVilleAndHoraire(@PathVariable String nomVille, @PathVariable String horaire) {
        return activiteService.rechercherParNomVilleEtHoraire(nomVille, horaire);
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
    @DeleteMapping("/ville/{ville}")
    @Transactional
    public void deleteByVille(@PathVariable Ville ville) {
        activiteService.deleteByVille(ville);
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
    public List<Activite> findByPrixLessThan(@PathVariable double prix) {
        return activiteService.findByPrixLessThan(prix);
    }

    // Vous pouvez ajouter d'autres méthodes de web service personnalisées ici si nécessaire
}
