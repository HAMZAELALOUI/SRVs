package com.project.srv.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.srv.bean.Hotel;
import com.project.srv.bean.Reservation;
import com.project.srv.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private Reservation reservation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        reservation = new Reservation();
        reservation.setId(1L);  // Ensure the ID is set for the response
        reservation.setHotel(new Hotel());
    }

    @Test
    public void testFindByHotelId() throws Exception {
        when(reservationService.findByHotelId(anyLong())).thenReturn(reservation);

        mockMvc.perform(get("/Gestion-Vol/Reservation/hotel/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetReservationById() throws Exception {
        when(reservationService.findReservationById(anyLong())).thenReturn(reservation);

        mockMvc.perform(get("/Gestion-Vol/Reservation/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteByHotelId() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Reservation/hotel/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void testUpdateReservation() throws Exception {
        when(reservationService.updateReservation(any(Reservation.class))).thenReturn(reservation);

        ObjectMapper mapper = new ObjectMapper();
        String reservationJson = mapper.writeValueAsString(reservation);

        mockMvc.perform(put("/Gestion-Vol/Reservation/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testSaveReservation() throws Exception {
        when(reservationService.saveReservation(any(Reservation.class))).thenReturn(reservation);

        ObjectMapper mapper = new ObjectMapper();
        String reservationJson = mapper.writeValueAsString(reservation);

        mockMvc.perform(post("/Gestion-Vol/Reservation/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
