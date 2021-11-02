package com.localbrand.repository;

import com.localbrand.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommuneRepository extends JpaRepository<Commune, Integer>, JpaSpecificationExecutor<Commune> {

}