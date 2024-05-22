package com.project.srv.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Vol;
import com.project.srv.service.ReservationVolService;
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

@WebMvcTest(ReservationVolController.class)
public class ReservationVolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationVolService reservationVolService;

    @InjectMocks
    private ReservationVolController reservationVolController;

    private ReservationVol reservationVol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationVolController).build();

        reservationVol = new ReservationVol();
        reservationVol.setId(1L); // Ensure the ID is set for the response
        reservationVol.setVol(new Vol());
    }

    @Test
    public void testFindByVolId() throws Exception {
        when(reservationVolService.findByVolId(anyLong())).thenReturn(reservationVol);

        mockMvc.perform(get("/Gestion-Vol/ReservationVol/vol/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetReservationVolById() throws Exception {
        when(reservationVolService.findReservationVolById(anyLong())).thenReturn(reservationVol);

        mockMvc.perform(get("/Gestion-Vol/ReservationVol/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteByVolId() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/ReservationVol/vol/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void testUpdateReservationVol() throws Exception {
        when(reservationVolService.updateReservation(any(ReservationVol.class))).thenReturn(reservationVol);

        ObjectMapper mapper = new ObjectMapper();
        String reservationVolJson = mapper.writeValueAsString(reservationVol);

        mockMvc.perform(put("/Gestion-Vol/ReservationVol/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationVolJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testSaveReservationVol() throws Exception {
        when(reservationVolService.saveReservation(any(ReservationVol.class))).thenReturn(reservationVol);

        ObjectMapper mapper = new ObjectMapper();
        String reservationVolJson = mapper.writeValueAsString(reservationVol);

        mockMvc.perform(post("/Gestion-Vol/ReservationVol/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationVolJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
