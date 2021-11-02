package com.localbrand.repository;

import com.localbrand.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JwtRepository extends JpaRepository<Jwt, Long>, JpaSpecificationExecutor<Jwt> {

}