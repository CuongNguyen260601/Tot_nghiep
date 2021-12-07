package com.localbrand.repository;

import com.localbrand.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher> {
    Page<Voucher> findAll(Pageable pageable);

    Page<Voucher> findAllByNameVoucherLike(String nameVoucher, Pageable pageable);

    Page<Voucher> findAllByIdStatus(Integer idStatus, Pageable pageable);

    Optional<Voucher> findByCodeVoucher(String codeVoucher);

    @Query(
            value = "select top 1 * from _Voucher " +
                    " where idStatus = :idStatus " +
                    " and condition <= :condition " +
                    " order by condition asc",
            nativeQuery = true
    )
    Optional<Voucher> findFirstByCondition(Integer idStatus, Float condition);

    @Query(
            value = "select top 1 * from _Voucher " +
                    " where idStatus = :idStatus " +
                    " and condition > :condition " +
                    " order by condition asc",
            nativeQuery = true
    )
    Optional<Voucher> findFirstByDonate(Integer idStatus, Float condition);
}