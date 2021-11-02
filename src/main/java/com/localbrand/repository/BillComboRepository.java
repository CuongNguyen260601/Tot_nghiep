package com.localbrand.repository;

import com.localbrand.entity.BillCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillComboRepository extends JpaRepository<BillCombo, Long>, JpaSpecificationExecutor<BillCombo> {

}