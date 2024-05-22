package com.project.srv.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.srv.bean.Ville;
import com.project.srv.dao.VilleDao;
import com.project.srv.service.VilleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VilleController.class)
public class VilleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VilleDao villeDao;

    @MockBean
    private VilleService villeService;

    private Ville ville1;
    private Ville ville2;

    @BeforeEach
    public void setup() {
        ville1 = new Ville();
        ville1.setId(1L);
        ville1.setNom("Paris");
        ville1.setPays("France");

        ville2 = new Ville();
        ville2.setId(2L);
        ville2.setNom("Lyon");
        ville2.setPays("France");
    }

    @Test
    public void testGetAllVilles() throws Exception {
        Mockito.when(villeDao.findAll()).thenReturn(Arrays.asList(ville1, ville2));

        mockMvc.perform(get("/srv/villes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nom", is("Paris")))
                .andExpect(jsonPath("$[1].nom", is("Lyon")));
    }

    @Test
    public void testGetVilleById() throws Exception {
        Mockito.when(villeDao.findById(1L)).thenReturn(Optional.of(ville1));

        mockMvc.perform(get("/srv/villes/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Paris")));
    }

    @Test
    public void testGetVilleById_NotFound() throws Exception {
        Mockito.when(villeDao.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/srv/villes/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetByNom() throws Exception {
        Mockito.when(villeService.findByNom("Paris")).thenReturn(Arrays.asList(ville1));

        mockMvc.perform(get("/srv/villes/nom/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nom", is("Paris")));
    }

    @Test
    public void testSaveVille() throws Exception {
        Mockito.when(villeService.save(any(Ville.class))).thenReturn(ville1);

        mockMvc.perform(post("/srv/villes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ville1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Paris")));
    }

    @Test
    public void testUpdateVille() throws Exception {
        Mockito.when(villeDao.findById(anyLong())).thenReturn(Optional.of(ville1));
        Mockito.when(villeDao.save(any(Ville.class))).thenReturn(ville1);

        mockMvc.perform(put("/srv/villes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(ville1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Paris")));
    }

    @Test
    public void testDeleteVille() throws Exception {
        Mockito.when(villeDao.findById(anyLong())).thenReturn(Optional.of(ville1));

        mockMvc.perform(delete("/srv/villes/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVille_NotFound() throws Exception {
        Mockito.when(villeDao.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/srv/villes/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetByPays() throws Exception {
        Mockito.when(villeService.findByPays("France")).thenReturn(Arrays.asList(ville1, ville2));

        mockMvc.perform(get("/srv/villes/pays/France"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nom", is("Paris")))
                .andExpect(jsonPath("$[1].nom", is("Lyon")));
    }

    @Test
    public void testGetByNomContainingIgnoreCase() throws Exception {
        Mockito.when(villeService.findByNomContainingIgnoreCase("par")).thenReturn(Arrays.asList(ville1));

        mockMvc.perform(get("/srv/villes/nomIg/par"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nom", is("Paris")));
    }
}
