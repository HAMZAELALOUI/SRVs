package com.project.srv.service;

import com.project.srv.bean.Hotel;
import com.project.srv.bean.Reservation;
import com.project.srv.dao.HotelDao;
import com.project.srv.dao.ReservationDao;
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
public class ReservationServiceTest {

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private HotelDao hotelDao;

    @InjectMocks
    private ReservationService reservationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindReservationById() {
        Long id = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(reservationDao.findReservationById(id)).thenReturn(reservation);

        Reservation result = reservationService.findReservationById(id);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationDao, times(1)).findReservationById(id);
    }

    @Test
    public void testFindByHotelId() {
        Long hotelId = 1L;
        Reservation reservation = new Reservation();
        reservation.setHotelId(hotelId);

        when(reservationDao.findByHotelId(hotelId)).thenReturn(reservation);

        Reservation result = reservationService.findByHotelId(hotelId);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationDao, times(1)).findByHotelId(hotelId);
    }

    @Test
    public void testDeleteByHotelId() {
        Long hotelId = 1L;
        doNothing().when(reservationDao).deleteByHotelId(hotelId);

        reservationService.deleteByHotelId(hotelId);

        verify(reservationDao, times(1)).deleteByHotelId(hotelId);
    }

    @Test
    public void testFindByHotel() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        Reservation reservation = new Reservation();
        reservation.setHotel(hotel);

        when(reservationDao.findByHotel(hotel)).thenReturn(reservation);

        Reservation result = reservationService.findByHotel(hotel);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationDao, times(1)).findByHotel(hotel);
    }

    @Test
    public void testSaveReservation() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        Reservation reservation = new Reservation();
        reservation.setHotelId(hotelId);

        when(hotelDao.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(reservationDao.findByHotelId(hotelId)).thenReturn(null);
        when(reservationDao.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.saveReservation(reservation);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(hotelDao, times(1)).findById(hotelId);
        verify(reservationDao, times(1)).findByHotelId(hotelId);
        verify(reservationDao, times(1)).save(reservation);
    }

    @Test
    public void testSaveReservation_HotelNotFound() {
        Long hotelId = 1L;
        Reservation reservation = new Reservation();
        reservation.setHotelId(hotelId);

        when(hotelDao.findById(hotelId)).thenReturn(Optional.empty());

        Reservation result = reservationService.saveReservation(reservation);

        assertNull(result);
        verify(hotelDao, times(1)).findById(hotelId);
        verify(reservationDao, never()).findByHotelId(anyLong());
        verify(reservationDao, never()).save(reservation);
    }

    @Test
    public void testSaveReservation_ExistingReservation() {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        Reservation reservation = new Reservation();
        reservation.setHotelId(hotelId);

        when(hotelDao.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(reservationDao.findByHotelId(hotelId)).thenReturn(reservation);

        Reservation result = reservationService.saveReservation(reservation);

        assertNull(result);
        verify(hotelDao, times(1)).findById(hotelId);
        verify(reservationDao, times(1)).findByHotelId(hotelId);
        verify(reservationDao, never()).save(reservation);
    }

    @Test
    public void testUpdateReservation() {
        Long reservationId = 1L;
        Reservation existingReservation = new Reservation();
        existingReservation.setId(reservationId);
        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(reservationId);
        updatedReservation.setHotel(new Hotel());

        when(reservationDao.findReservationById(reservationId)).thenReturn(existingReservation);
        when(reservationDao.save(existingReservation)).thenReturn(existingReservation);

        Reservation result = reservationService.updateReservation(updatedReservation);

        assertNotNull(result);
        assertEquals(existingReservation, result);
        verify(reservationDao, times(1)).findReservationById(reservationId);
        verify(reservationDao, times(1)).save(existingReservation);
    }

    @Test
    public void testUpdateReservation_NotFound() {
        Long reservationId = 1L;
        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(reservationId);

        when(reservationDao.findReservationById(reservationId)).thenReturn(null);

        Reservation result = reservationService.updateReservation(updatedReservation);

        assertNull(result);
        verify(reservationDao, times(1)).findReservationById(reservationId);
        verify(reservationDao, never()).save(any(Reservation.class));
    }
}