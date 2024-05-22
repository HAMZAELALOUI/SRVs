package com.project.srv.service;

import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Vol;
import com.project.srv.dao.ReservationVolDao;
import com.project.srv.dao.VolDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationVolServiceTest {

    @Mock
    private ReservationVolDao reservationVolDao;

    @Mock
    private VolDao volDao;

    @InjectMocks
    private ReservationVolService reservationVolService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindReservationById() {
        Long id = 1L;
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setId(id);

        when(reservationVolDao.findReservationById(id)).thenReturn(reservationVol);

        ReservationVol result = reservationVolService.findReservationById(id);

        assertNotNull(result);
        assertEquals(reservationVol, result);
        verify(reservationVolDao, times(1)).findReservationById(id);
    }

    @Test
    public void testFindByVolId() {
        Long volId = 1L;
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setVolId(volId);

        when(reservationVolDao.findByVolId(volId)).thenReturn(reservationVol);

        ReservationVol result = reservationVolService.findByVolId(volId);

        assertNotNull(result);
        assertEquals(reservationVol, result);
        verify(reservationVolDao, times(1)).findByVolId(volId);
    }

    @Test
    public void testDeleteByVolId() {
        Long volId = 1L;
        doNothing().when(reservationVolDao).deleteByVolId(volId);

        reservationVolService.deleteByVolId(volId);

        verify(reservationVolDao, times(1)).deleteByVolId(volId);
    }

    @Test
    public void testFindByVol() {
        int volId = 1;
        Vol vol = new Vol();
        vol.setIdVol(volId);
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setVol(vol);

        when(reservationVolDao.findByVol(vol)).thenReturn(reservationVol);

        ReservationVol result = reservationVolService.findByVol(vol);

        assertNotNull(result);
        assertEquals(reservationVol, result);
        verify(reservationVolDao, times(1)).findByVol(vol);
    }

    @Test
    public void testSaveReservation() {
        Long volId = 1L;
        Vol vol = new Vol();
        vol.setIdVol(Math.toIntExact(volId));
        vol.setPlacesDisponibles(5);
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setVolId(volId);

        when(volDao.findById(volId)).thenReturn(Optional.of(vol));
        when(reservationVolDao.save(reservationVol)).thenReturn(reservationVol);

        ReservationVol result = reservationVolService.saveReservation(reservationVol);

        assertNotNull(result);
        assertEquals(reservationVol, result);
        assertEquals(4, vol.getPlacesDisponibles());
        verify(volDao, times(1)).findById(volId);
        verify(volDao, times(1)).save(vol);
        verify(reservationVolDao, times(1)).save(reservationVol);
    }

    @Test
    public void testSaveReservation_VolNotFound() {
        Long volId = 1L;
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setVolId(volId);

        when(volDao.findById(volId)).thenReturn(Optional.empty());

        ReservationVol result = reservationVolService.saveReservation(reservationVol);

        assertNull(result);
        verify(volDao, times(1)).findById(volId);
        verify(reservationVolDao, never()).save(reservationVol);
    }

    @Test
    public void testSaveReservation_NoAvailableSeats() {
        Long volId = 1L;
        Vol vol = new Vol();
        vol.setIdVol(Math.toIntExact(volId));
        vol.setPlacesDisponibles(0);
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setVolId(volId);

        when(volDao.findById(volId)).thenReturn(Optional.of(vol));

        ReservationVol result = reservationVolService.saveReservation(reservationVol);

        assertNull(result);
        verify(volDao, times(1)).findById(volId);
        verify(volDao, never()).save(vol);
        verify(reservationVolDao, never()).save(reservationVol);
    }

    @Test
    public void testUpdateReservation() {
        Long reservationId = 1L;
        ReservationVol existingReservation = new ReservationVol();
        existingReservation.setId(reservationId);
        ReservationVol updatedReservation = new ReservationVol();
        updatedReservation.setId(reservationId);
        updatedReservation.setVol(new Vol());

        when(reservationVolDao.findReservationById(reservationId)).thenReturn(existingReservation);
        when(reservationVolDao.save(existingReservation)).thenReturn(existingReservation);

        ReservationVol result = reservationVolService.updateReservation(updatedReservation);

        assertNotNull(result);
        assertEquals(existingReservation, result);
        verify(reservationVolDao, times(1)).findReservationById(reservationId);
        verify(reservationVolDao, times(1)).save(existingReservation);
    }

    @Test
    public void testUpdateReservation_NotFound() {
        Long reservationId = 1L;
        ReservationVol updatedReservation = new ReservationVol();
        updatedReservation.setId(reservationId);

        when(reservationVolDao.findReservationById(reservationId)).thenReturn(null);

        ReservationVol result = reservationVolService.updateReservation(updatedReservation);

        assertNull(result);
        verify(reservationVolDao, times(1)).findReservationById(reservationId);
        verify(reservationVolDao, never()).save(any(ReservationVol.class));
    }
}
