package com.project.srv.ws;

import com.project.srv.bean.Administration;
import com.project.srv.service.AdministartionService;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdministartionController.class)
public class AdministartionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdministartionService administartionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByName() throws Exception {
        Administration administration = new Administration();
        administration.setName("testName");

        when(administartionService.findByName("testName")).thenReturn(administration);

        mockMvc.perform(get("/srv/admin/name/testName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"));
    }

    @Test
    public void testFindById() throws Exception {
        Administration administration = new Administration();
        administration.setId(1L);

        when(administartionService.findById(1L)).thenReturn(Optional.of(administration));

        mockMvc.perform(get("/srv/admin/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/srv/admin/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_Success() throws Exception {
        when(administartionService.authenticate("testName", "testPassword")).thenReturn(true);

        mockMvc.perform(post("/srv/admin/login")
                        .param("name", "testName")
                        .param("password", "testPassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    public void testLogin_Failure() throws Exception {
        when(administartionService.authenticate("testName", "testPassword")).thenReturn(false);

        mockMvc.perform(post("/srv/admin/login")
                        .param("name", "testName")
                        .param("password", "testPassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }
}
