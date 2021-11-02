package com.localbrand.repository;

import com.localbrand.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModuleRepository extends JpaRepository<Module, Long>, JpaSpecificationExecutor<Module> {

}