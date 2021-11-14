package com.localbrand.repository;

import com.localbrand.entity.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, Long>, JpaSpecificationExecutor<VoucherUser> {

    Optional<VoucherUser> findByIdVoucherAndAndIdUser(Integer voucherId, Integer userId);
}