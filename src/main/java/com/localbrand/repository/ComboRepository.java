package com.localbrand.repository;

import com.localbrand.entity.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComboRepository extends JpaRepository<Combo, Long>, JpaSpecificationExecutor<Combo> {

    Page<Combo> findAllByNameComboLike(String name, Pageable pageable);

    Page<Combo> findAllByNameComboLikeAndAndIdStatus(String name,Integer status, Pageable pageable);

    Page<Combo> findAllByIdStatus(Integer idStatus, Pageable pageable);
}