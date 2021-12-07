package com.localbrand.repository;

import com.localbrand.entity.BillProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BillProductRepository extends JpaRepository<BillProduct, Long>, JpaSpecificationExecutor<BillProduct> {

    BillProduct findFirstByIdBillAndAndIdProductDetail(Integer idBill, Integer idProductDetail);

    Page<BillProduct> findAllByIdBillAndIdStatus(Integer idBill, Integer idStatus, Pageable pageable);

    List<BillProduct> findAllByIdBillAndIdStatus(Integer idBill, Integer idStatus);

    List<BillProduct> findAllByIdBill(Integer idBill);

    Page<BillProduct> findAllByIdBill(Integer idBill, Pageable pageable);
}