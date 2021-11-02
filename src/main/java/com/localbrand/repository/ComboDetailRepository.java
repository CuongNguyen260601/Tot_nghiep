package com.localbrand.repository;

import com.localbrand.entity.ComboDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComboDetailRepository extends JpaRepository<ComboDetail, Long>, JpaSpecificationExecutor<ComboDetail> {

}