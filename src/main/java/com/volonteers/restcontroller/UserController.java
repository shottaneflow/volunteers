package com.volonteers.restcontroller;


import com.volonteers.model.Volunteer;
import com.volonteers.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/role")
    public Set<String> getUserRoles(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        return userDetails.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());
    }
    @GetMapping("/profile")
    public ResponseEntity<Volunteer> getUserProfile(Principal principal) {
        Volunteer volunteer = this.userService.findByUsername(principal.getName());
        return ResponseEntity.ok(volunteer);
    }
    @PostMapping("/profile/edit")
    public ResponseEntity<?> editUserProfile(Principal principal, @Valid @RequestBody Volunteer volunteer) {
        Volunteer volunter = this.userService.findByUsername(principal.getName());
        volunter.setFio(volunteer.getFio());
        volunter.setGender(volunteer.getGender());
        volunter.setDateOfBirth(volunteer.getDateOfBirth());
        volunter.setLanguages(volunteer.getLanguages());
        this.userService.save(volunter);
        return ResponseEntity.ok(volunter);
    }
}
