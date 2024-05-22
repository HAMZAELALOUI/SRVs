package com.project.srv.ws;

import com.project.srv.bean.Paiement;
import com.project.srv.bean.Reservation;
import com.project.srv.bean.ReservationVol;
import com.project.srv.bean.Reservationactivite;
import com.project.srv.service.PaiementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaiementController.class)
public class PaiementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaiementService paiementService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByRef() throws Exception {
        Paiement paiement = new Paiement();
        when(paiementService.findByRef(anyString())).thenReturn(paiement);

        mockMvc.perform(get("/Gestion-Vol/Paiement/Ref/testRef")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByModePaiement() throws Exception {
        List<Paiement> paiementList = new ArrayList<>();
        paiementList.add(new Paiement());

        when(paiementService.findByModePaiement(anyString())).thenReturn(paiementList);

        mockMvc.perform(get("/Gestion-Vol/Paiement/mode/testMode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByMontant() throws Exception {
        List<Paiement> paiementList = new ArrayList<>();
        paiementList.add(new Paiement());

        when(paiementService.findByMontant(anyDouble())).thenReturn(paiementList);

        mockMvc.perform(get("/Gestion-Vol/Paiement/montant/100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByNumeroCarte() throws Exception {
        List<Paiement> paiementList = new ArrayList<>();
        paiementList.add(new Paiement());

        when(paiementService.findByNumeroCarte(anyString())).thenReturn(paiementList);

        mockMvc.perform(get("/Gestion-Vol/Paiement/numero/testNumero")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByDateExpiration() throws Exception {
        List<Paiement> paiementList = new ArrayList<>();
        paiementList.add(new Paiement());

        when(paiementService.findByDateExpiration(anyString())).thenReturn(paiementList);

        mockMvc.perform(get("/Gestion-Vol/Paiement/date/testDate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByTitulaireCarte() throws Exception {
        List<Paiement> paiementList = new ArrayList<>();
        paiementList.add(new Paiement());

        when(paiementService.findByTitulaireCarte(anyString())).thenReturn(paiementList);

        mockMvc.perform(get("/Gestion-Vol/Paiement/titulaire/testTitulaire")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByReservation() throws Exception {
        Paiement paiement = new Paiement();
        when(paiementService.findByReservation(any(Reservation.class))).thenReturn(paiement);

        mockMvc.perform(get("/Gestion-Vol/Paiement/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByModePaiement() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Paiement/mode/testMode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByMontant() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Paiement/montant/100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByNumeroCarte() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Paiement/numero/testNumero")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByDateExpiration() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Paiement/date/testDate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByTitulaireCarte() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Paiement/titulaire/testTitulaire")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSavePaiement() throws Exception {
        mockMvc.perform(post("/Gestion-Vol/Paiement/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ref\": \"testRef\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByReservationVol() throws Exception {
        Paiement paiement = new Paiement();
        when(paiementService.findByReservationVol(any(ReservationVol.class))).thenReturn(paiement);

        mockMvc.perform(get("/Gestion-Vol/Paiement/reservationVol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSavePaiementVol() throws Exception {
        mockMvc.perform(post("/Gestion-Vol/Paiement/savevol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ref\": \"testRef\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSavePaiementActivite() throws Exception {
        mockMvc.perform(post("/Gestion-Vol/Paiement/saveActivite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ref\": \"testRef\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByReservationActivite() throws Exception {
        Paiement paiement = new Paiement();
        when(paiementService.findByReservationactivite(any(Reservationactivite.class))).thenReturn(paiement);

        mockMvc.perform(get("/Gestion-Vol/Paiement/reservationActivite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk());
    }
}
