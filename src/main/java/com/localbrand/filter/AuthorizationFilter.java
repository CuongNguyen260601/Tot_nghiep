package com.localbrand.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbrand.common.Interface_API;
import com.localbrand.common.Security_Enum;
import com.localbrand.entity.Jwt;
import com.localbrand.repository.JwtRepository;
import com.localbrand.repository.RoleRepository;
import com.localbrand.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {


    private JwtRepository jwtRepository;

    public AuthorizationFilter(JwtRepository jwtRepository){
        this.jwtRepository = jwtRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Auth.LOGIN)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Auth.SIGN_UP)
        ){
            filterChain.doFilter(request, response);
        }else{
            String authorizationHeader = request.getHeader("Token");
            String refreshToken = request.getHeader("refresh_token");

            Jwt jwt = this.jwtRepository.findFirstByJwtToken(refreshToken.trim()).orElse(null);

            if(Objects.isNull(jwt)){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                Map<String, String> error = new HashMap<>();
                error.put("message", "You not login");
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }else {
                if (Objects.nonNull(authorizationHeader)) {
                    try {

                        String token = authorizationHeader.trim();
                        Algorithm algorithm = Algorithm.HMAC256(Security_Enum.SECRET.getSecret().getBytes());
                        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = jwtVerifier.verify(token);
                        String username = decodedJWT.getSubject();
                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        Arrays.stream(roles).forEach(role -> {
                            authorities.add(new SimpleGrantedAuthority(role));
                        });
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        request.setAttribute("USER_NAME", username);
                        filterChain.doFilter(request, response);

                    } catch (Exception e) {
                        log.error("Error message: {}", e.getMessage());
                        response.setHeader("error", e.getMessage());
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("error_message", e.getMessage());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                    }
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        }
    }
}