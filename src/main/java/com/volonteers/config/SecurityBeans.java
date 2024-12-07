package com.volonteers.config;

import com.volonteers.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.*;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityBeans {

    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityBeans(JwtRequestFilter jwtRequestFilter,
                         UserService userService) {
        this.userService=userService;
        this.jwtRequestFilter=jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/auth-api/**").permitAll()
                        .requestMatchers("/registration-api/**").permitAll()
                        .requestMatchers("/welcome-page-api/**").permitAll()
                        .anyRequest().authenticated()


                )

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling->exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class) // Добавляем CORS фильтр
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        ;;


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public Filter corsFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

                HttpServletResponse httpResponse = (HttpServletResponse) response;
                HttpServletRequest httpRequest = (HttpServletRequest) request;

                // Добавляем необходимые заголовки
                httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
                httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
                httpResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

                // Обрабатываем preflight-запросы
                if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
                    httpResponse.setStatus(HttpStatus.OK.value());
                    return;
                }

                chain.doFilter(request, response);
            }
        };
    }

}
