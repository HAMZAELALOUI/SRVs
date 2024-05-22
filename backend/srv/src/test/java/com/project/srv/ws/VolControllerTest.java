package com.project.srv.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.srv.bean.Ville;
import com.project.srv.bean.Vol;
import com.project.srv.dao.VolDao;
import com.project.srv.service.VilleService;
import com.project.srv.service.VolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VolController.class)
public class VolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolDao volDao;

    @MockBean
    private VolService volService;

    @MockBean
    private VilleService villeService;

    private Vol vol1;
    private Vol vol2;
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

        vol1 = new Vol();
        vol1.setIdVol(1);
        vol1.setOrigin(ville1);
        vol1.setDestination(ville2);
        vol1.setHeureDepart(LocalDate.of(2023, 5, 1));
        vol1.setHeureArrivee(LocalDate.of(2023, 5, 2));
        vol1.setPrix(100.0f);
        vol1.setPlacesDisponibles(150);

        vol2 = new Vol();
        vol2.setIdVol(2);
        vol2.setOrigin(ville2);
        vol2.setDestination(ville1);
        vol2.setHeureDepart(LocalDate.of(2023, 6, 1));
        vol2.setHeureArrivee(LocalDate.of(2023, 6, 2));
        vol2.setPrix(150.0f);
        vol2.setPlacesDisponibles(100);
    }

    @Test
    public void testGetAllVols() throws Exception {
        Mockito.when(volDao.findAll()).thenReturn(Arrays.asList(vol1, vol2));

        mockMvc.perform(get("/srv/vols/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].origin.nom", is("Paris")))
                .andExpect(jsonPath("$[1].origin.nom", is("Lyon")));
    }

    @Test
    public void testGetVolById() throws Exception {
        Mockito.when(volDao.findById(1L)).thenReturn(Optional.of(vol1));

        mockMvc.perform(get("/srv/vols/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin.nom", is("Paris")));
    }

    @Test
    public void testGetVolById_NotFound() throws Exception {
        Mockito.when(volDao.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/srv/vols/id/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByOrigin() throws Exception {
        Mockito.when(villeService.findById(1L)).thenReturn(Optional.of(ville1));
        Mockito.when(volService.findByOrigin(ville1)).thenReturn(Arrays.asList(vol1));

        mockMvc.perform(get("/srv/vols/origin/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].origin.nom", is("Paris")));
    }

   
    @Test
    public void testFindByHeureDepart() throws Exception {
        Mockito.when(volService.findByHeureDepart(LocalDate.of(2023, 5, 1))).thenReturn(Arrays.asList(vol1));

        mockMvc.perform(get("/srv/vols/heureDep/2023-05-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].heureDepart", is("2023-05-01")));
    }

    @Test
    public void testFindByHeureArrivee() throws Exception {
        Mockito.when(volService.findByHeureArrivee(LocalDate.of(2023, 5, 2))).thenReturn(Arrays.asList(vol1));

        mockMvc.perform(get("/srv/vols/heureArr/2023-05-02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].heureArrivee", is("2023-05-02")));
    }




    @Test
    public void testDeleteVol() throws Exception {
        Mockito.doNothing().when(volService).deleteVolById(anyLong());

        mockMvc.perform(delete("/srv/vols/1"))
                .andExpect(status().isOk());
    }
}
