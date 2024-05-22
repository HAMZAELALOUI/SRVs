package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.dao.VilleDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VilleServiceTest {

    @Mock
    private VilleDao villeDao;

    @InjectMocks
    private VilleService villeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Ville ville = new Ville();
        ville.setId(1L);
        when(villeDao.findById(1L)).thenReturn(Optional.of(ville));

        Optional<Ville> result = villeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(ville, result.get());
        verify(villeDao, times(1)).findById(1L);
    }

    @Test
    public void testFindByNom() {
        Ville ville = new Ville();
        ville.setNom("Paris");
        List<Ville> villes = Arrays.asList(ville);
        when(villeDao.findByNom("Paris")).thenReturn(villes);

        List<Ville> result = villeService.findByNom("Paris");

        assertNotNull(result);
        assertEquals(villes, result);
        verify(villeDao, times(1)).findByNom("Paris");
    }

    @Test
    public void testFindAll() {
        Ville ville = new Ville();
        List<Ville> villes = Arrays.asList(ville);
        when(villeDao.findAll()).thenReturn(villes);

        List<Ville> result = villeService.findAll();

        assertNotNull(result);
        assertEquals(villes, result);
        verify(villeDao, times(1)).findAll();
    }

    @Test
    public void testFindByPays() {
        Ville ville = new Ville();
        ville.setPays("France");
        List<Ville> villes = Arrays.asList(ville);
        when(villeDao.findByPays("France")).thenReturn(villes);

        List<Ville> result = villeService.findByPays("France");

        assertNotNull(result);
        assertEquals(villes, result);
        verify(villeDao, times(1)).findByPays("France");
    }

    @Test
    public void testFindByNomContainingIgnoreCase() {
        Ville ville = new Ville();
        ville.setNom("paris");
        List<Ville> villes = Arrays.asList(ville);
        when(villeDao.findByNomContainingIgnoreCase("paris")).thenReturn(villes);

        List<Ville> result = villeService.findByNomContainingIgnoreCase("paris");

        assertNotNull(result);
        assertEquals(villes, result);
        verify(villeDao, times(1)).findByNomContainingIgnoreCase("paris");
    }

    @Test
    public void testDeleteVilleByNom() {
        String nom = "Paris";
        doNothing().when(villeDao).deleteByNom(nom);

        villeService.deleteVilleByNom(nom);

        verify(villeDao, times(1)).deleteByNom(nom);
    }

    @Test
    public void testDeleteVilleByPays() {
        String pays = "France";
        doNothing().when(villeDao).deleteByPays(pays);

        villeService.deleteVilleByPays(pays);

        verify(villeDao, times(1)).deleteByPays(pays);
    }

    @Test
    public void testDeleteAllVilles() {
        doNothing().when(villeDao).deleteAll();

        villeService.deleteAllVilles();

        verify(villeDao, times(1)).deleteAll();
    }

    @Test
    public void testSave() {
        Ville ville = new Ville();
        ville.setNom("Paris");
        ville.setPays("France");

        when(villeDao.findByNomAndPays(ville.getNom(), ville.getPays())).thenReturn(Optional.empty());
        when(villeDao.save(ville)).thenReturn(ville);

        Ville result = villeService.save(ville);

        assertNotNull(result);
        assertEquals(ville, result);
        verify(villeDao, times(1)).findByNomAndPays(ville.getNom(), ville.getPays());
        verify(villeDao, times(1)).save(ville);
    }

    @Test
    public void testSave_ExistingVille() {
        Ville ville = new Ville();
        ville.setNom("Paris");
        ville.setPays("France");

        when(villeDao.findByNomAndPays(ville.getNom(), ville.getPays())).thenReturn(Optional.of(ville));

        Ville result = villeService.save(ville);

        assertNotNull(result);
        assertEquals(ville, result);
        verify(villeDao, times(1)).findByNomAndPays(ville.getNom(), ville.getPays());
        verify(villeDao, never()).save(ville);
    }

    @Test
    public void testGetAllVilleNames() {
        Ville ville = new Ville();
        ville.setNom("Paris");
        List<Ville> villes = Arrays.asList(ville);
        when(villeDao.findAll()).thenReturn(villes);

        List<String> result = villeService.getAllVilleNames();

        assertNotNull(result);
        assertEquals(Arrays.asList("Paris"), result);
        verify(villeDao, times(1)).findAll();
    }

    @Test
    public void testFindIdByNom() {
        Ville ville = new Ville();
        ville.setNom("Paris");
        List<Ville> villes = Arrays.asList(ville);
        when(villeDao.findIdByNom("Paris")).thenReturn(villes);

        List<Ville> result = villeService.findIdByNom("Paris");

        assertNotNull(result);
        assertEquals(villes, result);
        verify(villeDao, times(1)).findIdByNom("Paris");
    }
}
