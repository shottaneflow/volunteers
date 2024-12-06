package com.volonteers.restcontroller;


import com.volonteers.model.Volunteer;
import com.volonteers.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration-api")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody Volunteer user) {
            this.userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);


    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activateUser(@PathVariable String code) {
        this.userService.activateUser(code);
        return new ResponseEntity<>("Activated", HttpStatus.OK);
    }


    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ProblemDetail> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,e.getLocalizedMessage()));
    }


}
