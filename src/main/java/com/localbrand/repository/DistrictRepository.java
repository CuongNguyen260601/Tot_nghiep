package com.localbrand.repository;

import com.localbrand.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {

    List<District> findAllByIdProvince(Integer idProvince);
}