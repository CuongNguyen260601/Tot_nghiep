package com.localbrand.repository;

import com.localbrand.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {

}