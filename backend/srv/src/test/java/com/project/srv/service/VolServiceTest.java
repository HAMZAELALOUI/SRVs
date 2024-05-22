package com.project.srv.service;

import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import com.project.srv.exeption.InvalidDataException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VolServiceTest {

    @Mock
    private VolDao volDao;

    @Mock
    private EntityManager entityManager;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private VolService volService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByOrigin() {
        Ville origin = new Ville();
        Vol vol = new Vol();
        vol.setOrigin(origin);
        List<Vol> vols = Arrays.asList(vol);
        when(volDao.findByOrigin(origin)).thenReturn(vols);

        List<Vol> result = volService.findByOrigin(origin);

        assertNotNull(result);
        assertEquals(vols, result);
        verify(volDao, times(1)).findByOrigin(origin);
    }

    @Test
    public void testFindByDestination() {
        Ville destination = new Ville();
        Vol vol = new Vol();
        vol.setDestination(destination);
        List<Vol> vols = Arrays.asList(vol);
        when(volDao.findByDestination(destination)).thenReturn(vols);

        List<Vol> result = volService.findByDestination(destination);

        assertNotNull(result);
        assertEquals(vols, result);
        verify(volDao, times(1)).findByDestination(destination);
    }

    @Test
    public void testFindByHeureDepart() {
        LocalDate heureDepart = LocalDate.now();
        Vol vol = new Vol();
        vol.setHeureDepart(heureDepart);
        List<Vol> vols = Arrays.asList(vol);
        when(volDao.findByHeureDepart(heureDepart)).thenReturn(vols);

        List<Vol> result = volService.findByHeureDepart(heureDepart);

        assertNotNull(result);
        assertEquals(vols, result);
        verify(volDao, times(1)).findByHeureDepart(heureDepart);
    }

    @Test
    public void testFindByHeureArrivee() {
        LocalDate heureArrivee = LocalDate.now();
        Vol vol = new Vol();
        vol.setHeureArrivee(heureArrivee);
        List<Vol> vols = Arrays.asList(vol);
        when(volDao.findByHeureArrivee(heureArrivee)).thenReturn(vols);

        List<Vol> result = volService.findByHeureArrivee(heureArrivee);

        assertNotNull(result);
        assertEquals(vols, result);
        verify(volDao, times(1)).findByHeureArrivee(heureArrivee);
    }

    @Test
    public void testFindByPrix() {
        float prix = 100.0f;
        Vol vol = new Vol();
        vol.setPrix(prix);
        List<Vol> vols = Arrays.asList(vol);
        when(volDao.findByPrix(prix)).thenReturn(vols);

        List<Vol> result = volService.findByPrix(prix);

        assertNotNull(result);
        assertEquals(vols, result);
        verify(volDao, times(1)).findByPrix(prix);
    }

    @Test
    public void testSave() throws InvalidDataException {
        Vol vol = new Vol();
        Ville origin = new Ville();
        origin.setId(1L);
        Ville destination = new Ville();
        destination.setId(2L);

        vol.setOrigin(origin);
        vol.setDestination(destination);
        vol.setHeureDepart(LocalDate.now().plusDays(1));
        vol.setHeureArrivee(LocalDate.now().plusDays(2));
        vol.setPrix(200.0f);
        vol.setPlacesDisponibles(100);

        when(entityManager.merge(origin)).thenReturn(origin);
        when(entityManager.merge(destination)).thenReturn(destination);
        when(volDao.save(vol)).thenReturn(vol);

        Vol result = volService.save(vol);

        assertNotNull(result);
        assertEquals(vol, result);
        verify(entityManager, times(1)).merge(origin);
        verify(entityManager, times(1)).merge(destination);
        verify(volDao, times(1)).save(vol);
    }

    @Test(expected = InvalidDataException.class)
    public void testSave_InvalidHeureDepart() throws InvalidDataException {
        Vol vol = new Vol();
        vol.setHeureDepart(LocalDate.now().minusDays(1));
        volService.save(vol);
    }

    @Test(expected = InvalidDataException.class)
    public void testSave_InvalidHeureArrivee() throws InvalidDataException {
        Vol vol = new Vol();
        vol.setHeureArrivee(LocalDate.now().minusDays(1));
        volService.save(vol);
    }

    @Test(expected = InvalidDataException.class)
    public void testSave_InvalidPrix() throws InvalidDataException {
        Vol vol = new Vol();
        vol.setPrix(-100.0f);
        volService.save(vol);
    }

    @Test(expected = InvalidDataException.class)
    public void testSave_InvalidPlacesDisponibles() throws InvalidDataException {
        Vol vol = new Vol();
        vol.setPlacesDisponibles(0);
        volService.save(vol);
    }

    @Test
    public void testStoreFile() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.png");

        doNothing().when(file).transferTo(any(Path.class));

        String result = volService.storeFile(file);

        assertNotNull(result);
        assertEquals("/images/test.png", result);
    }

    @Test(expected = RuntimeException.class)
    public void testStoreFile_EmptyFile() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        volService.storeFile(file);
    }

    @Test(expected = RuntimeException.class)
    public void testStoreFile_InvalidPath() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("../test.png");
        volService.storeFile(file);
    }
}
