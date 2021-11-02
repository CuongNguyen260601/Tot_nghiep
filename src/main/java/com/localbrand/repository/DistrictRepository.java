package com.localbrand.repository;

import com.localbrand.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DistrictRepository extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {

}