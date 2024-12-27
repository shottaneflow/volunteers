package com.volonteers.restcontroller;

import com.volonteers.dto.AuthRequest;
import com.volonteers.exceptions.AppError;
import com.volonteers.model.Volunteer;
import com.volonteers.service.UserService;
import com.volonteers.utils.JwtTokenUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth-api")

public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtils = new JwtTokenUtils();
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
            if(this.userService.findByUsername(volunteer.getUsername()) != null)
                return new ResponseEntity<>("Неправильный пароль", HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails=userService.loadUserByUsername(volunteer.getUsername());
        String token=jwtTokenUtils.generateToken(userDetails);
        AuthRequest authRequest=new AuthRequest();
        authRequest.setToken(token);
        authRequest.setRoles(userDetails.getAuthorities());

        return ResponseEntity.ok(authRequest);

    }

}
