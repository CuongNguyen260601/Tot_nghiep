package com.localbrand.repository;

import com.localbrand.entity.NewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NewDetailRepository extends JpaRepository<NewDetail, Long>, JpaSpecificationExecutor<NewDetail> {

}