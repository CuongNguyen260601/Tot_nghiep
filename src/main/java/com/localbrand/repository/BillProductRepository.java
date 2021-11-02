package com.localbrand.repository;

import com.localbrand.entity.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillProductRepository extends JpaRepository<BillProduct, Long>, JpaSpecificationExecutor<BillProduct> {

}