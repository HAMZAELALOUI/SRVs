package com.project.srv.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.srv.bean.Utilisateur;
import com.project.srv.jwt.JwtUtil;
import com.project.srv.service.UtilisateurSevice;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UtilisateurController.class)
public class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilisateurSevice utilisateurSevice;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private Utilisateur utilisateur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setName("Test User");
        utilisateur.setEmail("test@example.com");
        utilisateur.setPhone("123456789");
        utilisateur.setAge(30);
        utilisateur.setAddress("Test Address");
        utilisateur.setPassword(passwordEncoder.encode("password"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        when(utilisateurSevice.save(any(Utilisateur.class))).thenReturn(1);

        mockMvc.perform(multipart("/srv/utilisateur/register")
                        .param("name", "Test User")
                        .param("email", "test@example.com")
                        .param("phone", "123456789")
                        .param("age", "30")
                        .param("address", "Test Address")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."));
    }

    @Test
    public void testFindByEmail() throws Exception {
        when(utilisateurSevice.findByEmail("test@example.com")).thenReturn(utilisateur);

        mockMvc.perform(get("/srv/utilisateur/email/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }




}
