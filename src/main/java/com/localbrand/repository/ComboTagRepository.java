package com.localbrand.repository;

import com.localbrand.entity.ComboTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComboTagRepository extends JpaRepository<ComboTag, Long>, JpaSpecificationExecutor<ComboTag> {

}