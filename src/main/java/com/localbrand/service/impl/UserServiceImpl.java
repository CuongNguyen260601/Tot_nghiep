package com.localbrand.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.localbrand.common.JWT_Enum;
import com.localbrand.common.Security_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.request.UserRequestDTO;
import com.localbrand.dto.request.UserUpdateRequestDTO;
import com.localbrand.dto.response.RefreshTokenDTO;
import com.localbrand.dto.response.UserResponseDTO;
import com.localbrand.dto.response.UserResponseSignupDTO;
import com.localbrand.entity.*;
import com.localbrand.model_mapping.Impl.AddressMapping;
import com.localbrand.model_mapping.Impl.UserMapping;
import com.localbrand.repository.*;
import com.localbrand.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapping userMapping;
    private final JwtRepository jwtRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final AddressMapping addressMapping;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmailEqualsIgnoreCase(username).orElse(null);

        if(Objects.isNull(user)){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User is "+ user);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Role role = roleRepository.findById(user.getIdRole().longValue()).orElse(null);
        if(Objects.nonNull(role)) {
            authorities.add(new SimpleGrantedAuthority(role.getNameRole()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordUser(), authorities);
    }

    @Override
    public ServiceResult<UserResponseSignupDTO> singUp(HttpServletRequest request, UserRequestDTO userRequestDTO) {
        User user = this.userMapping.toEntitySignUp(userRequestDTO);
        user = this.userRepository.save(user);

        Algorithm algorithm = Algorithm.HMAC256(Security_Enum.SECRET.getSecret().getBytes());

        UserDetails userDetails = this.loadUserByUsername(user.getEmail());

        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_Enum.ACCESS_MINUTE.getValue()*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_Enum.REFRESH_MINUTE.getValue()*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Jwt jwt = Jwt
                .builder()
                .idUser(user.getIdUser().intValue())
                .jwtToken(refresh_token)
                .endTime(Timestamp.from(Instant.now().plusMillis(JWT_Enum.REFRESH_MINUTE.getValue()*60*1000)))
                .isActive(true)
                .build();

        jwtRepository.save(jwt);

        Cart cart = Cart.builder()
                .idCart(null)
                .idUser(user.getIdUser().intValue())
                .idStatus(Status_Enum.EXISTS.getCode())
                .build();

        this.cartRepository.save(cart);

        return new ServiceResult<>(HttpStatus.OK, "Signup is success", this.userMapping.toDtoSignup(user, access_token, refresh_token));
    }

    @Override
    public ServiceResult<UserResponseDTO> updateProfile(HttpServletRequest request, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = this.userRepository.findById(userUpdateRequestDTO.getIdUser()).orElse(null);

        if(Objects.isNull(user)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "User is not found", null);
        }


        user.setFirstName(userUpdateRequestDTO.getFirstName());
        user.setLastName(userUpdateRequestDTO.getLastName());
        user.setDateOfBirth(userUpdateRequestDTO.getDateOfBirth());
        user.setPhoneNumber(userUpdateRequestDTO.getPhoneNumber());
        user.setPasswordUser(passwordEncoder.encode(userUpdateRequestDTO.getPasswordUser()));
        user.setIdGender(userUpdateRequestDTO.getIdGender());
        user.setImageUser(userUpdateRequestDTO.getImageUser());

        if(Objects.isNull(userUpdateRequestDTO.getIdUser())){
            Address address = this.addressMapping.toEntitySave(userUpdateRequestDTO.getAddressRequestDTO());
            address = this.addressRepository.save(address);
            user.setIdAddress(address.getIdAddress().intValue());
        }else{
            user.setIdAddress(userUpdateRequestDTO.getIdAddress());
        }

        user = this.userRepository.save(user);

        return new ServiceResult<>(HttpStatus.OK, "Update is success", this.userMapping.toDto(user));
    }

    @Override
    public ServiceResult<RefreshTokenDTO> refreshToken(HttpServletRequest request, String refreshToken) {


        Jwt jwt = this.jwtRepository.findFirstByJwtToken(refreshToken.trim()).orElse(null);

        if(Objects.isNull(jwt)){
            return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You not login", null);
        }else{
            try {
                Algorithm algorithm = Algorithm.HMAC256(Security_Enum.SECRET.getSecret().getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();

                UserDetails userDetails = this.loadUserByUsername(username);

                String access_token = JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_Enum.ACCESS_MINUTE.getValue()*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                String refresh_token = JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWT_Enum.REFRESH_MINUTE.getValue()*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                jwt.setEndTime(Timestamp.from(Instant.now().plusMillis(JWT_Enum.REFRESH_MINUTE.getValue()*60*1000)));
                jwt.setJwtToken(refresh_token);
                jwt.setIsActive(true);

                jwtRepository.save(jwt);

                return new ServiceResult<>(HttpStatus.OK, "Refresh token is success", new RefreshTokenDTO(access_token,refresh_token));
            }catch (Exception e){
                return new ServiceResult<>(HttpStatus.UNAUTHORIZED, "You not login", null);
            }

        }
    }

    @Transactional()
    @Override
    public ServiceResult<?> logout(HttpServletRequest request) {
        String username = request.getAttribute("USER_NAME").toString();

        User user = this.userRepository.findFirstByEmailEqualsIgnoreCase(username).orElse(null);

        if(Objects.isNull(user)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, "User not found");
        }

        Jwt jwt = this.jwtRepository.findByIdUser(user.getIdUser().intValue()).orElse(null);

        if(Objects.nonNull(jwt)){
            this.jwtRepository.delete(jwt);
        }

        return new ServiceResult<>(HttpStatus.OK, "Logout is success");
    }

}
