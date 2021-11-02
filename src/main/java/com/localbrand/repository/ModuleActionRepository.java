package com.localbrand.repository;

import com.localbrand.entity.ModuleAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModuleActionRepository extends JpaRepository<ModuleAction, Long>, JpaSpecificationExecutor<ModuleAction> {

}