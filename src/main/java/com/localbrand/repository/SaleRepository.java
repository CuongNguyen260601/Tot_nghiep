package com.localbrand.repository;

import com.localbrand.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {
    Page<Sale> findAll(Pageable pageable);

    Page<Sale> findAllByNameSaleLike(String nameSale, Pageable pageable);

    Page<Sale> findAllByIdStatus(Integer idStatus, Pageable pageable);

    @Query(
            "select s from Sale s " +
                    " where s.idSale in ( "+
            "select distinct s.idSale from Sale s " +
             " join ProductSale ps " +
             " on ps.idSale = s.idSale " +
             " join ProductDetail p " +
             " on p.idProductDetail = ps.idProductDetail " +
             " where s.idStatus = 2 and ps.idStatus = 2 and p.idProductDetail = :idProductDetail)"
    )
    Sale findSaleByProductDetail(Long idProductDetail);
}