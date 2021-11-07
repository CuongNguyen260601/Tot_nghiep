package com.localbrand.repository;

import com.localbrand.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, JpaSpecificationExecutor<CartProduct> {

    Page<CartProduct> findAllByIdCart(Integer idCart, Pageable pageable);

    Optional<CartProduct> deleteByIdCartProduct(Integer idCartProduct);

    CartProduct findFirstByIdCartAndIdProductDetail(Integer idCart, Integer idProductDetail);

    Integer countAllByIdCart(Integer idCart);
}