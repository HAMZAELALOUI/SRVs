package com.project.srv.service;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Ville;
import com.project.srv.dao.HotelDao;
import com.project.srv.dao.VilleDao;
import com.project.srv.exeption.InvalidDataException;
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
public class HotelServiceTest {

    @Mock
    private HotelDao hotelDao;

    @Mock
    private VilleDao villeDao;

    @InjectMocks
    private HotelService hotelService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Hotel hotel = new Hotel();
        hotel.setId(id);

        when(hotelDao.findById(id)).thenReturn(Optional.of(hotel));

        Optional<Hotel> result = hotelService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(hotel, result.get());
        verify(hotelDao, times(1)).findById(id);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;
        doNothing().when(hotelDao).deleteById(id);

        hotelService.deleteById(id);

        verify(hotelDao, times(1)).deleteById(id);
    }

    @Test
    public void testFindByNom() {
        String nom = "Hotel Example";
        Hotel hotel = new Hotel();
        hotel.setNom(nom);
        List<Hotel> hotels = Arrays.asList(hotel);

        when(hotelDao.findByNom(nom)).thenReturn(hotels);

        List<Hotel> result = hotelService.findByNom(nom);

        assertNotNull(result);
        assertEquals(hotels, result);
        verify(hotelDao, times(1)).findByNom(nom);
    }

    @Test
    public void testFindByVille() {
        Ville ville = new Ville();
        ville.setNom("Paris");
        Hotel hotel = new Hotel();
        hotel.setVille(ville);
        List<Hotel> hotels = Arrays.asList(hotel);

        when(hotelDao.findByVille(ville)).thenReturn(hotels);

        List<Hotel> result = hotelService.findByVille(ville);

        assertNotNull(result);
        assertEquals(hotels, result);
        verify(hotelDao, times(1)).findByVille(ville);
    }

    @Test
    public void testFindByNombreEtoiles() {
        int nombreEtoiles = 5;
        Hotel hotel = new Hotel();
        hotel.setNombreEtoiles(nombreEtoiles);
        List<Hotel> hotels = Arrays.asList(hotel);

        when(hotelDao.findByNombreEtoiles(nombreEtoiles)).thenReturn(hotels);

        List<Hotel> result = hotelService.findByNombreEtoiles(nombreEtoiles);

        assertNotNull(result);
        assertEquals(hotels, result);
        verify(hotelDao, times(1)).findByNombreEtoiles(nombreEtoiles);
    }

    @Test
    public void testSaveHotel() throws InvalidDataException {
        Hotel hotel = new Hotel();
        hotel.setId(null); // Ensuring the hotel is new and does not have an ID
        hotel.setNom("New Hotel");
        Ville ville = new Ville();
        ville.setId(1L);
        hotel.setVille(ville);
        hotel.setNombreEtoiles(3);
        hotel.setPrixChambres(100);

        hotelService.saveHotel(hotel);

        verify(hotelDao, times(1)).save(hotel);
    }

    @Test(expected = InvalidDataException.class)
    public void testSaveHotelInvalidData() throws InvalidDataException {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setNom(null); // Invalid data: null name
        Ville ville = new Ville();
        ville.setId(1L);
        hotel.setVille(ville);
        hotel.setNombreEtoiles(3);
        hotel.setPrixChambres(100);

        hotelService.saveHotel(hotel);
    }
}
