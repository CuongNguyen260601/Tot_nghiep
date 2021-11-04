package com.localbrand.repository;

import com.localbrand.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, JpaSpecificationExecutor<CartProduct> {

    Page<CartProduct> findAllByIdCart(Integer idCart, Pageable pageable);

    CartProduct deleteCartProductByIdCartProduct(Long idCartProduct);

    CartProduct findFirstByIdCartAndIdProductDetail(Integer idCart, Integer idProductDetail);
}