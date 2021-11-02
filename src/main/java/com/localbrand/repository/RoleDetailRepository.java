package com.localbrand.repository;

import com.localbrand.entity.RoleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDetailRepository extends JpaRepository<RoleDetail, Long>, JpaSpecificationExecutor<RoleDetail> {

}