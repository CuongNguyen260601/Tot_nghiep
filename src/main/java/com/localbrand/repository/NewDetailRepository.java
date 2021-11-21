package com.localbrand.repository;

import com.localbrand.entity.NewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NewDetailRepository extends JpaRepository<NewDetail, Long>, JpaSpecificationExecutor<NewDetail> {

    List<NewDetail> findByIdNew(Integer idNew);


}