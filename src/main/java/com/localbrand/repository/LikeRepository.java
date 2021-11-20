package com.localbrand.repository;

import com.localbrand.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>, JpaSpecificationExecutor<Like> {

    Integer countLikeByIdProduct(Integer idProduct);

    @Query(
            "select l from Like l " +
                    " where (:idProduct = -1 or l.idProduct = :idProduct) " +
                    " and (:idCombo = -1 or l.idCombo = :idCombo) " +
                    " and (:idUser = -1 or l.idUser = :idUser)"
    )
    Optional<Like> findByProductOrComboAndUser(Integer idProduct, Integer idCombo, Integer idUser);

    @Query(
            "select count(l) from Like l " +
                    " where (:idProduct = -1 or l.idProduct = :idProduct) " +
                    " and (:idCombo = -1 or l.idCombo = :idCombo) "
    )
    Optional<Integer> totalLikeByProductOrCombo(Integer idProduct, Integer idCombo);

    Optional<Like> findFirstByIdUserAndIdProduct(Integer idUser, Integer idProduct);
}