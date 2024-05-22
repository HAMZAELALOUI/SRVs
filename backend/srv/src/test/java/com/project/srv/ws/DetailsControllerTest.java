package com.project.srv.ws;

import com.project.srv.bean.Details;
import com.project.srv.service.DetailsService;
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

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DetailsController.class)
public class DetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetailsService detailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByNombreParticipants() throws Exception {
        List<Details> detailsList = new ArrayList<>();
        detailsList.add(new Details());

        when(detailsService.findByNombreParticipants(anyInt())).thenReturn(detailsList);

        mockMvc.perform(get("/Gestion-Vol/Details/participants/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByPrixTotalActivite() throws Exception {
        List<Details> detailsList = new ArrayList<>();
        detailsList.add(new Details());

        when(detailsService.findByPrixTotalActivite(anyDouble())).thenReturn(detailsList);

        mockMvc.perform(get("/Gestion-Vol/Details/prix/100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByNombreParticipants() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Details/participants/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteByPrixTotalActivite() throws Exception {
        mockMvc.perform(delete("/Gestion-Vol/Details/prix/100.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
