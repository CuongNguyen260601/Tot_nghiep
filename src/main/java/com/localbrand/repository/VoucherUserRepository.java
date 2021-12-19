package com.localbrand.repository;

import com.localbrand.dto.response.VoucherUserResponseDTO;
import com.localbrand.entity.VoucherUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, Long>, JpaSpecificationExecutor<VoucherUser> {

    Optional<VoucherUser> findByIdVoucherAndAndIdUser(Integer voucherId, Integer userId);

    @Query(
            "select new VoucherUserResponseDTO (vu.idVoucherUser, v.idVoucher, v.nameVoucher, v.condition, v.discount, v.descriptionVoucher, v.codeVoucher, vu.idUser, vu.quantity) from VoucherUser vu " +
                    " join Voucher v on v.idVoucher = vu.idVoucher " +
                    " where vu.idUser = :idUser and vu.quantity > 0"
    )
    Page<VoucherUserResponseDTO> findAllByIdUser(Integer idUser, Pageable pageable);
}