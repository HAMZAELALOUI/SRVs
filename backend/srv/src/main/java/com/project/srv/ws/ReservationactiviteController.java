package com.project.srv.ws;

import com.project.srv.bean.Reservationactivite;
import com.project.srv.jwt.JwtUtil;
import com.project.srv.service.ReservationactiviteService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/GestionVol/Reservationactivite")
public class ReservationactiviteController {
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private ReservationactiviteService reservationService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/save")
    public Reservationactivite saveReservationActivite(@RequestBody Reservationactivite reservation) {
        return reservationService.saveReservationActivite(reservation);
    }




    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id, @RequestHeader("Authorization") String tokenHeader) {
        // Vérifier si le token JWT est présent dans l'en-tête de la requête
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        // Extrait le token JWT de l'en-tête de la requête
        String jwtToken = tokenHeader.substring(7);

        // Vérifiez si le token JWT est valide
        if (!jwtUtil.validateToken(jwtToken, getUsernameFromToken(jwtToken))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        // Récupérer la réservation par ID
        Reservationactivite reservation = reservationService.findReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reservation);
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

}
