package com.project.srv.ws;

import com.project.srv.bean.Activite;
import com.project.srv.bean.Ville;
import com.project.srv.service.ActiviteService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ActiviteController.class)
public class ActiviteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActiviteService activiteService;

    private Activite activite1, activite2;
    private Ville ville;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ville = new Ville();
        ville.setId(1L);
        ville.setNom("TestVille");
        ville.setPays("TestPays");

        activite1 = new Activite();
        activite1.setId(1L);
        activite1.setNom("Test Activite");
        activite1.setLieu("Test Lieu");
        activite1.setDescription("Test Description");
        activite1.setHoraire("Test Horaire");
        activite1.setPrix(100.0);
        activite1.setVille(ville);

        activite2 = new Activite();
        activite2.setId(2L);
        activite2.setNom("Another Activite");
        activite2.setLieu("Another Lieu");
        activite2.setDescription("Another Description");
        activite2.setHoraire("Another Horaire");
        activite2.setPrix(200.0);
        activite2.setVille(ville);
    }

    @Test
    public void testFindActiviteWithVilleById() throws Exception {
        when(activiteService.findActiviteWithVilleById(1L)).thenReturn(Optional.of(activite1));

        mockMvc.perform(get("/GestionVol/Activite/activity/ActiviteDetails/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testSaveActivite() throws Exception {
        when(activiteService.saveActivite(any(Activite.class))).thenReturn(1);

        mockMvc.perform(post("/GestionVol/Activite/save")
                        .contentType("application/json")
                        .content("{\"nom\":\"Test Activite\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testUpdateActivite() throws Exception {
        when(activiteService.updateActivite(eq(1L), any(Activite.class))).thenReturn(1);

        mockMvc.perform(put("/GestionVol/Activite/update/1")
                        .contentType("application/json")
                        .content("{\"nom\":\"Updated Activite\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void testFindByNomVille() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByNomVille("TestVille")).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/ville/TestVille"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findAll()).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindByPrixActiviteBetween() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByPrixActviteBetween(100.0, 200.0)).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/prixactivite/between/100/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindById() throws Exception {
        when(activiteService.findById(1L)).thenReturn(Optional.of(activite1));

        mockMvc.perform(get("/GestionVol/Activite/Id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testFindByNom() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByNom("TestNom")).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/nom/TestNom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindByLieu() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByLieu("TestLieu")).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/lieu/TestLieu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindByDescription() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByDescription("TestDescription")).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/description/TestDescription"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindByHoraire() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByHoraire("TestHoraire")).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/horaire/TestHoraire"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testFindByPrix() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByPrix(100.0)).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/prix/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(activiteService).deleteById(1L);

        mockMvc.perform(delete("/GestionVol/Activite/Id/1"))
                .andExpect(status().isOk());

        verify(activiteService, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteByNom() throws Exception {
        doNothing().when(activiteService).deleteByNom("TestNom");

        mockMvc.perform(delete("/GestionVol/Activite/nom/TestNom"))
                .andExpect(status().isOk());

        verify(activiteService, times(1)).deleteByNom("TestNom");
    }


    @Test
    public void testDeleteByLieu() throws Exception {
        doNothing().when(activiteService).deleteByLieu("TestLieu");

        mockMvc.perform(delete("/GestionVol/Activite/lieu/TestLieu"))
                .andExpect(status().isOk());

        verify(activiteService, times(1)).deleteByLieu("TestLieu");
    }

    @Test
    public void testDeleteByDescription() throws Exception {
        doNothing().when(activiteService).deleteByDescription("TestDescription");

        mockMvc.perform(delete("/GestionVol/Activite/description/TestDescription"))
                .andExpect(status().isOk());

        verify(activiteService, times(1)).deleteByDescription("TestDescription");
    }



    @Test
    public void testDeleteByHoraire() throws Exception {
        doNothing().when(activiteService).deleteByHoraire("TestHoraire");

        mockMvc.perform(delete("/GestionVol/Activite/horaire/TestHoraire"))
                .andExpect(status().isOk());

        verify(activiteService, times(1)).deleteByHoraire("TestHoraire");
    }

    @Test
    public void testDeleteByPrix() throws Exception {
        doNothing().when(activiteService).deleteByPrix(100.0);

        mockMvc.perform(delete("/GestionVol/Activite/prix/100"))
                .andExpect(status().isOk());

        verify(activiteService, times(1)).deleteByPrix(100.0);
    }

    @Test
    public void testFindByPrixLessThan() throws Exception {
        List<Activite> activites = Arrays.asList(activite1, activite2);
        when(activiteService.findByPrixLessThan(100.0)).thenReturn(activites);

        mockMvc.perform(get("/GestionVol/Activite/prix-inferieur/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].id").exists());
    }
}
