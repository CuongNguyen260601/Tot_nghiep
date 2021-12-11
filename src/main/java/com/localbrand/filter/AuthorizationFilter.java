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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {


    private final JwtRepository jwtRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Auth.LOGIN)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Auth.SIGN_UP)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Auth.REFRESH_TOKEN)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_GET_ALL_USER)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SEARCH_USER)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_USER)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_USER_BY_COLOR)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_USER_BY_COLOR_AND_SIZE)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_ALL)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_NEW)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_HOT)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_RELATED)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Address.ADDRESS_FIND_ALL_COMMUNE)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Address.ADDRESS_FIND_ALL_DISTRICT)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Address.ADDRESS_FIND_ALL_PROVINCE)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Size.SIZE_FIND_EXISTS)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Color.COLOR_FIND_EXISTS)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Category.CATEGORY_TO_SIZE)
            || request.getServletPath().contains("/Image")
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_PARENT_ID)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.UPLOAD_IMAGE)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Auth.GET_NEW_PASSWORD)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Combo.COMBO_FIND_ALL_USER)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Combo.COMBO_FIND_BY_ID_USER)
            || request.getServletPath().equals(Interface_API.MAIN+Interface_API.API.Combo.COMBO_SEARCH_USER)

        ){
            filterChain.doFilter(request, response);
        }else{
            String authorizationHeader = request.getHeader("Token");
            String refreshToken = request.getHeader("refresh_token");

            if(Objects.isNull(authorizationHeader) || Objects.isNull(refreshToken)){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                Map<String, String> error = new HashMap<>();
                error.put("message", "You not login");
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

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
                        Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        request.setAttribute("USER_NAME", username);

                    } catch (Exception e) {
                        log.error("Error message: {}", e.getMessage());
                        response.setHeader("error", e.getMessage());
                        response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("error_message", e.getMessage());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                    }
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        }
    }
}
