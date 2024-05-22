package com.project.srv.service;

import com.project.srv.bean.Activite;
import com.project.srv.bean.Reservationactivite;
import com.project.srv.dao.ActiviteDao;
import com.project.srv.dao.ReservationactiviteDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationactiviteServiceTest {

    @Mock
    private ReservationactiviteDao reservationactiviteDao;

    @Mock
    private ActiviteDao activiteDao;

    @InjectMocks
    private ReservationactiviteService reservationactiviteService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByActiviteId() {
        Long activiteId = 1L;
        Reservationactivite reservation = new Reservationactivite();
        reservation.setId(1L);
        List<Reservationactivite> reservations = Arrays.asList(reservation);

        when(reservationactiviteDao.findByActiviteId(activiteId)).thenReturn(reservations);

        List<Reservationactivite> result = reservationactiviteService.findByActiviteId(activiteId);

        assertNotNull(result);
        assertEquals(reservations, result);
        verify(reservationactiviteDao, times(1)).findByActiviteId(activiteId);
    }

    @Test
    public void testDeleteByActiviteId() {
        Long activiteId = 1L;
        doNothing().when(reservationactiviteDao).deleteByActiviteId(activiteId);

        reservationactiviteService.deleteByActiviteId(activiteId);

        verify(reservationactiviteDao, times(1)).deleteByActiviteId(activiteId);
    }

    @Test
    public void testFindByDateReservation() {
        Date dateReservation = new Date();
        Reservationactivite reservation = new Reservationactivite();
        reservation.setDateReservation(dateReservation);
        List<Reservationactivite> reservations = Arrays.asList(reservation);

        when(reservationactiviteDao.findByDateReservation(dateReservation)).thenReturn(reservations);

        List<Reservationactivite> result = reservationactiviteService.findByDateReservation(dateReservation);

        assertNotNull(result);
        assertEquals(reservations, result);
        verify(reservationactiviteDao, times(1)).findByDateReservation(dateReservation);
    }

    @Test
    public void testFindByNombrePersonnes() {
        int nombrePersonnes = 5;
        Reservationactivite reservation = new Reservationactivite();
        reservation.setNombrePersonnes(nombrePersonnes);
        List<Reservationactivite> reservations = Arrays.asList(reservation);

        when(reservationactiviteDao.findByNombrePersonnes(nombrePersonnes)).thenReturn(reservations);

        List<Reservationactivite> result = reservationactiviteService.findByNombrePersonnes(nombrePersonnes);

        assertNotNull(result);
        assertEquals(reservations, result);
        verify(reservationactiviteDao, times(1)).findByNombrePersonnes(nombrePersonnes);
    }

    @Test
    public void testFindByPrixTotalBetween() {
        double prixMin = 50.0;
        double prixMax = 150.0;
        Reservationactivite reservation = new Reservationactivite();
        reservation.setPrixTotal(100.0);
        List<Reservationactivite> reservations = Arrays.asList(reservation);

        when(reservationactiviteDao.findByPrixTotalBetween(prixMin, prixMax)).thenReturn(reservations);

        List<Reservationactivite> result = reservationactiviteService.findByPrixTotalBetween(prixMin, prixMax);

        assertNotNull(result);
        assertEquals(reservations, result);
        verify(reservationactiviteDao, times(1)).findByPrixTotalBetween(prixMin, prixMax);
    }

    @Test
    public void testSaveReservationActivite() {
        Long activiteId = 1L;
        Activite activite = new Activite();
        activite.setId(activiteId);

        Reservationactivite reservation = new Reservationactivite();
        reservation.setActivite(activite);
        reservation.setDateReservation(new Date());

        when(activiteDao.findById(activiteId)).thenReturn(Optional.of(activite));
        when(reservationactiviteDao.findByActiviteIdAndDateReservation(activiteId, reservation.getDateReservation())).thenReturn(Arrays.asList());
        when(reservationactiviteDao.save(reservation)).thenReturn(reservation);

        Reservationactivite result = reservationactiviteService.saveReservationActivite(reservation);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(activiteDao, times(1)).findById(activiteId);
        verify(reservationactiviteDao, times(1)).findByActiviteIdAndDateReservation(activiteId, reservation.getDateReservation());
        verify(reservationactiviteDao, times(1)).save(reservation);
    }

    @Test
    public void testSaveReservationActivite_ActiviteNotFound() {
        Long activiteId = 1L;
        Activite activite = new Activite();
        activite.setId(activiteId);

        Reservationactivite reservation = new Reservationactivite();
        reservation.setActivite(activite);
        reservation.setDateReservation(new Date());

        when(activiteDao.findById(activiteId)).thenReturn(Optional.empty());

        Reservationactivite result = reservationactiviteService.saveReservationActivite(reservation);

        assertNull(result);
        verify(activiteDao, times(1)).findById(activiteId);
        verify(reservationactiviteDao, never()).findByActiviteIdAndDateReservation(anyLong(), any(Date.class));
        verify(reservationactiviteDao, never()).save(reservation);
    }

    @Test
    public void testSaveReservationActivite_ExistingReservation() {
        Long activiteId = 1L;
        Activite activite = new Activite();
        activite.setId(activiteId);

        Reservationactivite reservation = new Reservationactivite();
        reservation.setActivite(activite);
        reservation.setDateReservation(new Date());

        when(activiteDao.findById(activiteId)).thenReturn(Optional.of(activite));
        when(reservationactiviteDao.findByActiviteIdAndDateReservation(activiteId, reservation.getDateReservation())).thenReturn(Arrays.asList(reservation));

        Reservationactivite result = reservationactiviteService.saveReservationActivite(reservation);

        assertNull(result);
        verify(activiteDao, times(1)).findById(activiteId);
        verify(reservationactiviteDao, times(1)).findByActiviteIdAndDateReservation(activiteId, reservation.getDateReservation());
        verify(reservationactiviteDao, never()).save(reservation);
    }

    @Test
    public void testFindReservationById() {
        Long id = 1L;
        Reservationactivite reservation = new Reservationactivite();
        reservation.setId(id);

        when(reservationactiviteDao.findReservationById(id)).thenReturn(reservation);

        Reservationactivite result = reservationactiviteService.findReservationById(id);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationactiviteDao, times(1)).findReservationById(id);
    }
}
