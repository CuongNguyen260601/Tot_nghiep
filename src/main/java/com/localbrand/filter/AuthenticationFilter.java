package com.localbrand.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbrand.common.JWT_Enum;
import com.localbrand.common.Role_Enum;
import com.localbrand.common.Security_Enum;
import com.localbrand.dto.response.UserResponseSignupDTO;
import com.localbrand.entity.Jwt;
import com.localbrand.model_mapping.Impl.UserMapping;
import com.localbrand.repository.JwtRepository;
import com.localbrand.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtRepository jwtRepository;
    private final UserRepository userRepository;
    private final UserMapping userMapping;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtRepository jwtRepository, UserRepository userRepository, UserMapping userMapping){
        this.authenticationManager = authenticationManager;
        this.jwtRepository = jwtRepository;
        this.userRepository = userRepository;
        this.userMapping = userMapping;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is {} and Password is {}", username, password);
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Transactional()
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();

        if(request.getRequestURL().toString().contains("api/webtpf/login")){
            user.getAuthorities().forEach(grantedAuthority -> {
                if(grantedAuthority.getAuthority().equals(Role_Enum.ROLE_USER.getRole())) {
                    try {
                        response.setStatus(500);
                        new ObjectMapper().writeValue(response.getOutputStream(), "you not admin");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            });
        }else{

            Algorithm algorithm = Algorithm.HMAC256(Security_Enum.SECRET.getSecret().getBytes());
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_Enum.ACCESS_MINUTE.getValue()*60*1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
            String refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_Enum.REFRESH_MINUTE.getValue()*60*1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
            com.localbrand.entity.User userEntity = userRepository.findFirstByEmailEqualsIgnoreCase(user.getUsername()).orElse(null);
            Jwt jwtFind  = this.jwtRepository.findByIdUser(userEntity.getIdUser().intValue()).orElse(null);
            if(Objects.nonNull(jwtFind)){
                this.jwtRepository.delete(jwtFind);
            }
            Jwt jwt = Jwt
                    .builder()
                    .idUser(userEntity.getIdUser().intValue())
                    .jwtToken(refresh_token)
                    .endTime(Timestamp.from(Instant.now().plusMillis(JWT_Enum.REFRESH_MINUTE.getValue()*60*1000)))
                    .isActive(true)
                    .build();
            this.jwtRepository.save(jwt);
            UserResponseSignupDTO userResponseSignupDTO = this.userMapping.toDtoSignup(userEntity, access_token, refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), userResponseSignupDTO);
        }
    }
}
