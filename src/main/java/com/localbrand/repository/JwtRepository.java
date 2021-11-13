package com.localbrand.repository;

import com.localbrand.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt, Long>, JpaSpecificationExecutor<Jwt> {

    @Query(
            "select t from Jwt t " +
                    " where t.endTime >  current_timestamp " +
                    " and t.isActive = true " +
                    " and t.jwtToken = :token"
    )
    Optional<Jwt> findFirstByJwtToken(String token);

    Optional<Jwt> findByIdUser(Integer userId);
}