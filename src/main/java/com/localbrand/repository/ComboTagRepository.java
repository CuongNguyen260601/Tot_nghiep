package com.localbrand.repository;

import com.localbrand.entity.ComboTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ComboTagRepository extends JpaRepository<ComboTag, Long>, JpaSpecificationExecutor<ComboTag> {

    List<ComboTag> findAllByIdCombo(Integer idCombo);
}