package com.localbrand.repository;

import com.localbrand.entity.ComboDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ComboDetailRepository extends JpaRepository<ComboDetail, Long>, JpaSpecificationExecutor<ComboDetail> {

    Page<ComboDetail> findAllByIdCombo(Integer idCombo, Pageable pageable);
    List<ComboDetail> findAllByIdCombo(Integer idCombo);
}