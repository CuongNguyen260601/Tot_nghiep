package com.localbrand.repository;

import com.localbrand.entity.CartCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartComboRepository extends JpaRepository<CartCombo, Long>, JpaSpecificationExecutor<CartCombo> {

}