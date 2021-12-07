package com.localbrand.repository;

import com.localbrand.entity.CartCombo;
import com.localbrand.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.Optional;

public interface CartComboRepository extends JpaRepository<CartCombo, Long>, JpaSpecificationExecutor<CartCombo> {

    @Query(
            nativeQuery = true,
            value = "SELECT combo.*, cartCombo.idCartCombo FROM _Cart_Combo cartCombo" +
                    " JOIN (SELECT * FROM _Cart where idUser = ?1) cart" +
                    " ON cart.idCart = cartCombo.idCart" +
                    " JOIN _Combo combo ON combo.idCombo = cartCombo.idCombo" +
                    " WHERE combo.nameCombo LIKE %?2%"
    )
    Page<Map<String, Object>> findListComboByName(Integer userId, String comboName, Pageable pageable);

    CartCombo findFirstByIdCartAndIdCombo(Integer idCart, Integer idCombo);

    @Query(
            nativeQuery = true,
            value = "DELETE FROM _Cart_Combo WHERE idCart = (SELECT idCart FROM _Cart WHERE idUser = ?1)"
    )
    void deleteAll(Integer idUser);

    Page<CartCombo> findAllByIdCart(Integer idCart, Pageable pageable);

    Optional<CartCombo> deleteByIdCartCombo(Long idCartCombo);

    @Query(
            " select cc from CartCombo cc " +
                    " join Cart c on c.idCart = cc.idCart " +
                    " where c.idUser = :idUser " +
                    " and cc.idCombo = :idCombo"
    )
    CartCombo findFirstByIdComboAndIdUser(Integer idCombo, Integer idUser);

}