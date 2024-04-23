package com.project.srv.ws;


import com.project.srv.bean.Utilisateur;
import com.project.srv.jwt.JwtUtil;
import com.project.srv.service.UtilisateurSevice;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/srv/utilisateur")
public class UtilisateurController {



//    @PostMapping
//    public int save(@RequestBody Utilisateur utilisateur) {
//        return utilisateurSevice.save(utilisateur);
//    }
@Autowired
private UtilisateurSevice utilisateurService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam("name") String name,
                                               @RequestParam("email") String email,
                                               @RequestParam("phone") String phone,
                                               @RequestParam("age") Integer age,
                                               @RequestParam("address") String address,
                                               @RequestParam("password") String password,
                                               @RequestParam(value = "profilePicture", required = false) MultipartFile file) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setName(name);
        utilisateur.setEmail(email);
        utilisateur.setPhone(phone);
        utilisateur.setAge(age);
        utilisateur.setAddress(address);
        utilisateur.setPassword(password);

        if (file != null && !file.isEmpty()) {
            String imageUrl = utilisateurService.storeFile(file);
            utilisateur.setProfilePicture(imageUrl);
        }

        int result = utilisateurService.save(utilisateur);
        if (result == 1) {
            return ResponseEntity.ok("User registered successfully.");
        } else if (result == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user.");
        }
    }


    @GetMapping("/email/{email}")
    public Utilisateur findByEmail(@PathVariable String email) {
        return utilisateurSevice.findByEmail(email);
    }

    @GetMapping("/name/{name}")
    public Utilisateur findByName(@PathVariable String name) {
        return utilisateurSevice.findByName(name);
    }


    @GetMapping("/address/{address}")
    public Utilisateur findByAddress(@PathVariable String address) {
        return utilisateurSevice.findByAddress(address);
    }

    @GetMapping("/Phone/{Phone}")
    public Utilisateur findByPhone(@PathVariable String Phone) {
        return utilisateurSevice.findByPhone(Phone);
    }

    @GetMapping("/age/{age}")
    public List<Utilisateur> findByAge(@PathVariable Integer age) {
        return utilisateurSevice.findByAge(age);
    }
    @DeleteMapping("/email/{email}")
    @Transactional
    public void deleteByEmail(@PathVariable String email) {
        utilisateurSevice.deleteByEmail(email);
    }

    @DeleteMapping("/name/{name}")
    @Transactional
    public void deleteByName(@PathVariable("name") String name) {
        utilisateurSevice.deleteByName(name);
    }

    @DeleteMapping("/address/{address}")
    @Transactional
    public void deleteByAddress(@PathVariable String address) {
         utilisateurSevice.deleteByAddress(address);
    }


    @DeleteMapping("/Phone/{Phone}")
    @Transactional
    public void deleteByPhone(@PathVariable String Phone) {
        utilisateurSevice.deleteByPhone(Phone);
    }

    @DeleteMapping("/age/{age}")
    @Transactional
    public void deleteByAge(@PathVariable Integer age) {
        utilisateurSevice.deleteByAge(age);
    }

    @GetMapping
    public List<Utilisateur> findAll() {
        return utilisateurSevice.findAll();
    }

    public long count() {
        return utilisateurSevice.count();
    }


    @DeleteMapping("/id/{id}")
    @Transactional
    public void deleteById(@PathVariable("id") Long aLong) {
        utilisateurSevice.deleteById(aLong);
    }




    @GetMapping("/id/{id}")
    public Optional<Utilisateur> findById(@PathVariable("id") Long aLong) {
        return utilisateurSevice.findById(aLong);
    }

//    @GetMapping("/login/email/{email}/password/{password}")
//    public int loginUser(@PathVariable String email,@PathVariable String password) {
//        return utilisateurSevice.loginUser(email, password);
//    }
    // In your UtilisateurController




    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Utilisateur user = utilisateurService.findByEmail(email);
        if (user != null && utilisateurService.getPasswordEncoder().matches(password, user.getPassword())) {
            // Generate token
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }


    // Add this class to represent the JWT response
    public class AuthenticationResponse {
        private final String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }
    }



//    @PutMapping("/updateUtilisateur")
//    public int update(@RequestBody Utilisateur utilisateur) {
//        return utilisateurSevice.update(utilisateur);
//    }

    @PutMapping("/updateUtilisateur")
    public ResponseEntity<?> updateUtilisateur(@RequestParam("id") Long id,
                                               @RequestParam("name") String name,
                                               @RequestParam("email") String email,
                                               @RequestParam("phone") String phone,
                                               @RequestParam("age") Integer age,
                                               @RequestParam("address") String address,
                                               @RequestParam("currentPassword") String currentPassword,
                                               @RequestParam(value = "newPassword", required = false) String newPassword,
                                               @RequestParam(value = "profilePicture", required = false) MultipartFile file) {
        Utilisateur existingUser = utilisateurService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Verify current password
        if (!utilisateurService.getPasswordEncoder().matches(currentPassword, existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid current password");
        }

        // Update user details
        existingUser.setName(name);
        existingUser.setEmail(email);
        existingUser.setPhone(phone);
        existingUser.setAge(age);
        existingUser.setAddress(address);

        // Update password if new password is provided and is not blank
        if (newPassword != null && !newPassword.isBlank()) {
            existingUser.setPassword(newPassword);
        }

        // Handle profile picture update
        if (file != null && !file.isEmpty()) {
            String imageUrl = utilisateurService.storeFile(file);
            existingUser.setProfilePicture(imageUrl);
        } else {
            // If no new file, keep the old image
            existingUser.setProfilePicture(existingUser.getProfilePicture());
        }

        utilisateurService.update(existingUser);
        return ResponseEntity.ok("Profile updated successfully.");
    }



    @Autowired
    private UtilisateurSevice utilisateurSevice;
}
