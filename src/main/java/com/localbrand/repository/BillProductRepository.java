package com.localbrand.repository;

import com.localbrand.entity.BillProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillProductRepository extends JpaRepository<BillProduct, Long>, JpaSpecificationExecutor<BillProduct> {

    BillProduct findFirstByIdBillAndAndIdProductDetail(Integer idBill, Integer idProductDetail);

    Page<BillProduct> findAllByIdBillAndIdStatus(Integer idBill, Integer idStatus, Pageable pageable);

    Page<BillProduct> findAllByIdBill(Integer idBill, Pageable pageable);
}