package com.localbrand.repository;

import com.localbrand.entity.BillCombo;
import com.localbrand.entity.BillProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BillComboRepository extends JpaRepository<BillCombo, Long>, JpaSpecificationExecutor<BillCombo> {

    BillCombo findFirstByIdBillAndAndIdCombo(Integer idBill, Integer idCombo);

    Page<BillCombo> findAllByIdBillAndIdStatus(Integer idBill, Integer idStatus, Pageable pageable);

    List<BillCombo> findAllByIdBillAndIdStatus(Integer idBill, Integer idStatus);

    List<BillCombo> findAllByIdBill(Integer idBill);

    Page<BillCombo> findAllByIdBill(Integer idBill, Pageable pageable);
}