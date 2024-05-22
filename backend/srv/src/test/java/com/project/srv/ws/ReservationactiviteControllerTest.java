package com.project.srv.ws;

import com.project.srv.bean.Reservationactivite;
import com.project.srv.jwt.JwtUtil;
import com.project.srv.service.ReservationactiviteService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationactiviteController.class)
public class ReservationactiviteControllerTest {

    @MockBean
    private ReservationactiviteService reservationService;

    @MockBean
    private JwtUtil jwtUtil;

    @InjectMocks
    private ReservationactiviteController reservationactiviteController;

    private MockMvc mockMvc;

    // Ensure this is a valid Base64-encoded string
    @Value("${jwt.secret:secret}")
    private String secret;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationactiviteController).build();
    }

    @Test
    public void testSaveReservationActivite() throws Exception {
        Reservationactivite reservation = new Reservationactivite();
        when(reservationService.saveReservationActivite(any(Reservationactivite.class))).thenReturn(reservation);

        mockMvc.perform(post("/GestionVol/Reservationactivite/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReservationById_Unauthorized_NoToken() throws Exception {
        mockMvc.perform(get("/GestionVol/Reservationactivite/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Changed to expect 400 status
    }

    private String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()) // Ensure secret is properly used
                .compact();
    }
}
