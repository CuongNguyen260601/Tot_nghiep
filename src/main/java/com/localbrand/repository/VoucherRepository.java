package com.localbrand.repository;

import com.localbrand.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher> {
    Page<Voucher> findAll(Pageable pageable);

    Page<Voucher> findAllByNameVoucherLike(String nameVoucher, Pageable pageable);

    Page<Voucher> findAllByIdStatus(Integer idStatus, Pageable pageable);
}