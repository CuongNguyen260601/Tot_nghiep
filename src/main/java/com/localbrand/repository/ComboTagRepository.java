package com.localbrand.repository;

import com.localbrand.entity.ComboTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComboTagRepository extends JpaRepository<ComboTag, Long>, JpaSpecificationExecutor<ComboTag> {

    List<ComboTag> findAllByIdCombo(Integer idCombo);

    @Query(
            "select ct from ComboTag ct " +
                    " where ct.idTag = :idTag " +
                    " and ct.idCombo in :comboIds"
    )
    List<ComboTag> findAllByIdTagCombo(List<Integer> comboIds, Integer idTag);

    List<ComboTag> findAllByIdTag(Integer idTag);
}