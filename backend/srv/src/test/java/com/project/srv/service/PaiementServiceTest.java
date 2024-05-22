package com.project.srv.service;

import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Reservationactivite;
import com.project.srv.dao.PaiementDao;
import com.project.srv.exeption.InvalidDataException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaiementServiceTest {

    @Mock
    private PaiementDao paiementDao;

    @Mock
    private ReservationService reservationService;

    @Mock
    private ReservationVolService reservationVolService;

    @Mock
    private ReservationactiviteService reservationactiviteService;

    @InjectMocks
    private PaiementService paiementService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByRef() {
        String ref = "12345";
        Paiement paiement = new Paiement();
        paiement.setRef(ref);

        when(paiementDao.findByRef(ref)).thenReturn(paiement);

        Paiement result = paiementService.findByRef(ref);

        assertNotNull(result);
        assertEquals(paiement, result);
        verify(paiementDao, times(1)).findByRef(ref);
    }

    @Test
    public void testFindByModePaiement() {
        String modePaiement = "Credit Card";
        Paiement paiement = new Paiement();
        paiement.setModePaiement(modePaiement);
        List<Paiement> paiements = Arrays.asList(paiement);

        when(paiementDao.findByModePaiement(modePaiement)).thenReturn(paiements);

        List<Paiement> result = paiementService.findByModePaiement(modePaiement);

        assertNotNull(result);
        assertEquals(paiements, result);
        verify(paiementDao, times(1)).findByModePaiement(modePaiement);
    }

    @Test
    public void testFindByMontant() {
        double montant = 100.0;
        Paiement paiement = new Paiement();
        paiement.setMontant(montant);
        List<Paiement> paiements = Arrays.asList(paiement);

        when(paiementDao.findByMontant(montant)).thenReturn(paiements);

        List<Paiement> result = paiementService.findByMontant(montant);

        assertNotNull(result);
        assertEquals(paiements, result);
        verify(paiementDao, times(1)).findByMontant(montant);
    }

    @Test
    public void testFindByNumeroCarte() {
        String numeroCarte = "1234567890";
        Paiement paiement = new Paiement();
        paiement.setNumeroCarte(numeroCarte);
        List<Paiement> paiements = Arrays.asList(paiement);

        when(paiementDao.findByNumeroCarte(numeroCarte)).thenReturn(paiements);

        List<Paiement> result = paiementService.findByNumeroCarte(numeroCarte);

        assertNotNull(result);
        assertEquals(paiements, result);
        verify(paiementDao, times(1)).findByNumeroCarte(numeroCarte);
    }

    @Test
    public void testDeleteByModePaiement() {
        String modePaiement = "Credit Card";
        doNothing().when(paiementDao).deleteByModePaiement(modePaiement);

        paiementService.deleteByModePaiement(modePaiement);

        verify(paiementDao, times(1)).deleteByModePaiement(modePaiement);
    }

    @Test
    public void testSavePaiement() {
        Paiement paiement = new Paiement();
        paiement.setMontant(100.0);
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        paiement.setReservation(reservation);

        when(reservationService.findReservationById(reservation.getId())).thenReturn(reservation);
        when(paiementDao.findByReservation(reservation)).thenReturn(null);

        int result = paiementService.savePaiement(paiement);

        assertEquals(1, result);
        verify(paiementDao, times(2)).save(paiement);
    }

    @Ignore
    @Test
    public void testSavePaiementInvalidData_NegativeMontant() {
        Paiement paiement = new Paiement();
        paiement.setMontant(-100.0); // Invalid montant
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        paiement.setReservation(reservation);

        int result = paiementService.savePaiement(paiement);

        assertEquals(-3, result); // Ensure it returns -3 for invalid montant
        verify(paiementDao, never()).save(paiement);
    }

    @Test
    public void testSavePaiementInvalidData_NoReservation() {
        Paiement paiement = new Paiement();
        paiement.setMontant(100.0);
        paiement.setReservation(null); // No reservation

        int result = paiementService.savePaiement(paiement);

        assertEquals(-2, result); // Ensure it returns -2 for no reservation
        verify(paiementDao, never()).save(paiement);
    }

    @Test
    public void testSavePaiementInvalidData_ExistingPaiement() {
        Paiement paiement = new Paiement();
        paiement.setMontant(100.0);
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        paiement.setReservation(reservation);

        when(reservationService.findReservationById(reservation.getId())).thenReturn(reservation);
        when(paiementDao.findByReservation(reservation)).thenReturn(new Paiement());

        int result = paiementService.savePaiement(paiement);

        assertEquals(-1, result); // Ensure it returns -1 for existing paiement
        verify(paiementDao, never()).save(paiement);
    }

    @Test
    public void testSavePaiementVol() {
        Paiement paiement = new Paiement();
        paiement.setMontant(100.0);
        ReservationVol reservationVol = new ReservationVol();
        reservationVol.setId(1L);
        paiement.setReservationVol(reservationVol);

        when(reservationVolService.findReservationVolById(reservationVol.getId())).thenReturn(reservationVol);
        when(paiementDao.findByReservationVol(reservationVol)).thenReturn(null);

        int result = paiementService.savePaiementVol(paiement);

        assertEquals(1, result);
        verify(paiementDao, times(2)).save(paiement);
    }

    @Test
    public void testSavePaiementActivite() {
        Paiement paiement = new Paiement();
        paiement.setMontant(100.0);
        Reservationactivite reservationactivite = new Reservationactivite();
        reservationactivite.setId(1L);
        paiement.setReservationactivite(reservationactivite);

        when(reservationactiviteService.findReservationById(reservationactivite.getId())).thenReturn(reservationactivite);
        when(paiementDao.findByReservationactivite(reservationactivite)).thenReturn(null);

        int result = paiementService.savePaiementActivite(paiement);

        assertEquals(1, result);
        verify(paiementDao, times(2)).save(paiement);
    }
}
