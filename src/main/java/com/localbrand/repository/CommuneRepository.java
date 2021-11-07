package com.localbrand.repository;

import com.localbrand.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Integer>, JpaSpecificationExecutor<Commune> {

    List<Commune> findAllByIdProvinceAndAndIdDistrict(Integer idProvince, Integer idDistrict);
}