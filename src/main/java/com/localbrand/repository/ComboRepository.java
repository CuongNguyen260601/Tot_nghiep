package com.localbrand.repository;

import com.localbrand.entity.Combo;
import com.localbrand.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComboRepository extends JpaRepository<Combo, Long>, JpaSpecificationExecutor<Combo> {

    Page<Combo> findAllByNameComboLike(String name, Pageable pageable);

    Page<Combo> findAllByNameComboLikeAndAndIdStatus(String name,Integer status, Pageable pageable);

    Page<Combo> findAllByIdStatus(Integer idStatus, Pageable pageable);

    @Query(
            "select c from Combo c " +
                    " where c.quantity = 0"
    )
    List<Combo> findAllByQuantity();


    @Query(
            value = "select * from _Combo c "+
            " where c.idCombo in ( "+
            " select distinct bc.idCombo from _Bill_Combo bc "+
            " join _Bill b on b.idBill = bc.idBill "+
            " where bc.idStatus = :idStatus "+
            " and month(b.dateSuccess) = month(current_timestamp) "+
            " group by bc.idCombo "+
            " having count(bc.idCombo) >= :a) ",
            nativeQuery = true
    )
    List<Combo> findAllComboHot(Integer idStatus, Integer a);
}