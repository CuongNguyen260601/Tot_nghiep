package com.localbrand.repository;

import com.localbrand.entity.ProductSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Long>, JpaSpecificationExecutor<ProductSale> {

    Optional<ProductSale> findFirstByIdProductDetailAndIdStatus(Integer idProductDetail, Integer idStatus);

    @Query(
            "select ps from ProductSale ps " +
                    " where ps.idStatus = :status " +
                    " and ps.idProductDetail in :listIdProductDetail"
    )
    List<ProductSale> findAllByDirection(Integer status, List<Integer> listIdProductDetail);

    @Query(
            "select ps from ProductSale ps " +
                    " where ps.idProductSale in :listProductSaleId"
    )
    List<ProductSale> findAllByListProductSaleId(List<Integer> listProductSaleId);

    @Transactional
    @Modifying
    @Query(
            "Update ProductSale ps set ps.idStatus = :nextStatus" +
                    " where ps.idStatus = :prevStatus " +
                    " and ps.idProductDetail in :idProductDetail"
    )
    void UpdateSaleByIdProductDetail(Integer prevStatus, Integer nextStatus, List<Integer> idProductDetail);

    Page<ProductSale> findAllByIdStatus(Integer idStatus, Pageable pageable);
}