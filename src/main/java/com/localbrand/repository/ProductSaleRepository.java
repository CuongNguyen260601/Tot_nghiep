package com.localbrand.repository;

import com.localbrand.entity.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Long>, JpaSpecificationExecutor<ProductSale> {

    Optional<ProductSale> findFirstByIdProductDetailAndIdStatus(Integer idProductDetail, Integer idStatus);
}