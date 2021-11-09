package com.localbrand.repository;

import com.localbrand.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, JpaSpecificationExecutor<CartProduct> {

    Page<CartProduct> findAllByIdCart(Integer idCart, Pageable pageable);

    Optional<CartProduct> deleteByIdCartProduct(Long idCartProduct);

    CartProduct findFirstByIdCartAndIdProductDetail(Integer idCart, Integer idProductDetail);

    Integer countAllByIdCart(Integer idCart);

    @Query(
            " select cp from CartProduct cp " +
                    " join Cart c on c.idCart = cp.idCart " +
                    " where c.idUser = :idUser " +
                    " and cp.idProductDetail = :idProductDetail"
    )
    CartProduct findFirstByIdProductDetailAndIdUser(Integer idProductDetail, Integer idUser);
}