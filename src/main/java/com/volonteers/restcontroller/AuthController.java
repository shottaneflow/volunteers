package com.volonteers.restcontroller;

import com.volonteers.exceptions.AppError;
import com.volonteers.model.Volunteer;
import com.volonteers.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth-api")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody Volunteer volunteer) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(volunteer.getUsername(),
                    volunteer.getPassword()));
            Volunteer user=this.userService.findByUsername(volunteer.getUsername());
            //Проверяю, если юзер подтвердит почту, то activationCode скинется в null,
            //если нет, то он не даст зайти, потому что бзер добавится в бд, но activationCode будет висеть UUID
            if(!StringUtils.isEmpty(user.getActivationCode())){
                return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(),"Подтвердите email"), HttpStatus.CONFLICT);
            }
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"Неправильный  логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();

    }
}
