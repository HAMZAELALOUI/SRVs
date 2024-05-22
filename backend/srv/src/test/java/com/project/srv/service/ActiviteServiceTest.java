package com.project.srv.service;

import com.project.srv.bean.Activite;
import com.project.srv.bean.Ville;
import com.project.srv.dao.ActiviteDao;
import com.project.srv.dao.VilleDao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActiviteServiceTest {

    @Mock
    private ActiviteDao activiteDao;

    @Mock
    private VilleDao villeDao;

    @InjectMocks
    private ActiviteService activiteService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindActiviteWithVilleById() {
        Long activiteId = 1L;
        Activite activite = new Activite();
        when(activiteDao.findActiviteWithVilleById(activiteId)).thenReturn(Optional.of(activite));

        Optional<Activite> result = activiteService.findActiviteWithVilleById(activiteId);

        assertNotNull(result);
        assertEquals(activite, result.get());
        verify(activiteDao, times(1)).findActiviteWithVilleById(activiteId);
    }
    @Ignore
    @Test
    public void testRechercherParNomVilleEtHoraire() {
        String nomVille = "Paris";
        String horaire = "Morning";
        Ville ville = new Ville();
        ville.setNom(nomVille);
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);

        when(villeDao.findByNom(nomVille)).thenReturn(Arrays.asList(ville));
        when(activiteDao.findByVilleAndHoraire(ville, horaire)).thenReturn(activites);

        List<Ville> villes = villeDao.findByNom(nomVille);
        System.out.println("Villes found: " + villes);

        List<Activite> result = activiteService.rechercherParNomVilleEtHoraire(nomVille, horaire);
        System.out.println("Result: " + result);

        assertNotNull("Result should not be null", result);
        assertEquals("Result should match expected activites", activites, result);
        verify(villeDao, times(1)).findByNom(nomVille);
        verify(activiteDao, times(1)).findByVilleAndHoraire(ville, horaire);
    }

    @Test
    public void testSaveActivite() {
        Activite activite = new Activite();
        activite.setNom("Activity 1");
        activite.setLieu("Place 1");
        activite.setPrix(100);

        int result = activiteService.saveActivite(activite);

        assertEquals(1, result);
        verify(activiteDao, times(1)).save(activite);
    }

    @Test
    public void testUpdateActivite() {
        Long id = 1L;
        Activite existingActivite = new Activite();
        existingActivite.setId(id);
        when(activiteDao.findById(id)).thenReturn(Optional.of(existingActivite));

        Activite updatedActivite = new Activite();
        updatedActivite.setNom("Updated Name");
        updatedActivite.setLieu("Updated Place");

        int result = activiteService.updateActivite(id, updatedActivite);

        assertEquals(1, result);
        verify(activiteDao, times(1)).findById(id);
        verify(activiteDao, times(1)).save(existingActivite);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Activite activite = new Activite();
        when(activiteDao.findById(id)).thenReturn(Optional.of(activite));

        Optional<Activite> result = activiteService.findById(id);

        assertNotNull(result);
        assertEquals(activite, result.get());
        verify(activiteDao, times(1)).findById(id);
    }

    @Test
    public void testFindByPrixActviteBetween() {
        double prixMin = 50.0;
        double prixMax = 150.0;
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByPrixBetween(prixMin, prixMax)).thenReturn(activites);

        List<Activite> result = activiteService.findByPrixActviteBetween(prixMin, prixMax);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByPrixBetween(prixMin, prixMax);
    }

    @Test
    public void testFindByNom() {
        String nom = "Activity 1";
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByNom(nom)).thenReturn(activites);

        List<Activite> result = activiteService.findByNom(nom);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByNom(nom);
    }

    @Test
    public void testFindByLieu() {
        String lieu = "Place 1";
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByLieu(lieu)).thenReturn(activites);

        List<Activite> result = activiteService.findByLieu(lieu);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByLieu(lieu);
    }

    @Test
    public void testFindByVille() {
        Ville ville = new Ville();
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByVille(ville)).thenReturn(activites);

        List<Activite> result = activiteService.findByVille(ville);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByVille(ville);
    }

    @Test
    public void testFindByDescription() {
        String description = "Description 1";
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByDescription(description)).thenReturn(activites);

        List<Activite> result = activiteService.findByDescription(description);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByDescription(description);
    }

    @Test
    public void testFindAll() {
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findAll()).thenReturn(activites);

        List<Activite> result = activiteService.findAll();

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findAll();
    }

    @Test
    public void testFindByPrixLessThan() {
        double prixMax = 100.0;
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByPrixLessThan(prixMax)).thenReturn(activites);

        List<Activite> result = activiteService.findByPrixLessThan(prixMax);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByPrixLessThan(prixMax);
    }

    @Test
    public void testFindByHoraire() {
        String horaire = "Morning";
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByHoraire(horaire)).thenReturn(activites);

        List<Activite> result = activiteService.findByHoraire(horaire);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByHoraire(horaire);
    }

    @Test
    public void testFindByPrix() {
        double prix = 100.0;
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByPrix(prix)).thenReturn(activites);

        List<Activite> result = activiteService.findByPrix(prix);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByPrix(prix);
    }

    @Test
    public void testFindByVilleAndHoraire() {
        Ville ville = new Ville();
        String horaire = "Morning";
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByVilleAndHoraire(ville, horaire)).thenReturn(activites);

        List<Activite> result = activiteService.findByVilleAndHoraire(ville, horaire);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByVilleAndHoraire(ville, horaire);
    }

    @Test
    public void testFindByNomVille() {
        String nomVille = "Paris";
        Activite activite = new Activite();
        List<Activite> activites = Arrays.asList(activite);
        when(activiteDao.findByVilleNom(nomVille)).thenReturn(activites);

        List<Activite> result = activiteService.findByNomVille(nomVille);

        assertNotNull(result);
        assertEquals(activites, result);
        verify(activiteDao, times(1)).findByVilleNom(nomVille);
    }

    @Test
    public void testDeleteById() {
        long id = 1L;
        doNothing().when(activiteDao).deleteById(id);

        activiteService.deleteById(id);

        verify(activiteDao, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteByNom() {
        String nom = "Activity 1";
        doNothing().when(activiteDao).deleteByNom(nom);

        activiteService.deleteByNom(nom);

        verify(activiteDao, times(1)).deleteByNom(nom);
    }

    @Test
    public void testDeleteByVille() {
        Ville ville = new Ville();
        doNothing().when(activiteDao).deleteByVille(ville);

        activiteService.deleteByVille(ville);

        verify(activiteDao, times(1)).deleteByVille(ville);
    }

    @Test
    public void testDeleteByLieu() {
        String lieu = "Place 1";
        doNothing().when(activiteDao).deleteByLieu(lieu);

        activiteService.deleteByLieu(lieu);

        verify(activiteDao, times(1)).deleteByLieu(lieu);
    }

    @Test
    public void testDeleteByDescription() {
        String description = "Description 1";
        doNothing().when(activiteDao).deleteByDescription(description);

        activiteService.deleteByDescription(description);

        verify(activiteDao, times(1)).deleteByDescription(description);
    }

    @Test
    public void testDeleteByHoraire() {
        String horaire = "Morning";
        doNothing().when(activiteDao).deleteByHoraire(horaire);

        activiteService.deleteByHoraire(horaire);

        verify(activiteDao, times(1)).deleteByHoraire(horaire);
    }

    @Test
    public void testDeleteByPrix() {
        double prix = 100.0;
        doNothing().when(activiteDao).deleteByPrix(prix);

        activiteService.deleteByPrix(prix);

        verify(activiteDao, times(1)).deleteByPrix(prix);
    }
}
