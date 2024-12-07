package com.volonteers.config;

import com.volonteers.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.IOException;
import java.security.SignatureException;
import java.util.stream.Collectors;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    public JwtRequestFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String username=null;
        String jwt=null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            jwt=authHeader.substring(7);
            try {
                username=jwtTokenUtils.getUsername(jwt);
            }
            catch(ExpiredJwtException e){
                //добавить ошибку время жизни токена вышло
            }

        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);

        }
        filterChain.doFilter(request, response);

    }
}