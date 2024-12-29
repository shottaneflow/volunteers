package com.volonteers.service;

import ch.qos.logback.core.util.StringUtil;
import com.volonteers.exceptions.AppError;
import com.volonteers.model.Volunteer;
import com.volonteers.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.volonteers.model.Authority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.format;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SmtpMailSender smtpMailSender;

    public DefaultUserService(UserRepository userRepository,
                              SmtpMailSender smtpMailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.smtpMailSender=smtpMailSender;
    }


    @Override
    public Volunteer findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь "+username+" не найден"));
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        Volunteer user=this.userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Пользователь "+username+" не найден"));
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList());
    }
    @Override
    public void addUser(Volunteer user) {
        Volunteer volunteer=this.userRepository.findByUsername(user.getUsername()).orElse(null);
        if(volunteer != null) {
            throw new BadCredentialsException("Пользователь уже существует");
        }

            user.setActive(true);
            Authority auth = new Authority();
            auth.setId(2);
            auth.setAuthority("ROLE_USER");
            user.addAuthorities(auth);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());
            this.userRepository.save(user);

            if (!StringUtils.isEmpty(user.getEmail())) {
                String message = String.format(
                        "Hi, %s! \n" +
                                "Welcome to VolunteerApp. Please, visit next link to confirm your email : " +
                                "http://localhost:8081/registration-api/activate/%s",
                        user.getUsername(),
                        user.getActivationCode()
                );
                smtpMailSender.send(user.getEmail(), "Activation Code", message);
            }


    }

    @Override
    public void activateUser(String code){
        Volunteer user=this.userRepository.findByActivationCode(code);

        if(user==null){
            throw new BadCredentialsException("Не найден код активации");
        }
        user.setActivationCode(null);
        userRepository.save(user);
    }
    @Override
    public void save(Volunteer volunteer){
        this.userRepository.save(volunteer);
    }
    @Override
    public Volunteer findUserById(int id){
        return this.userRepository.findById(id).orElse(null);
    }

}
