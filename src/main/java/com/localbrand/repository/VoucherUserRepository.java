package com.localbrand.repository;

import com.localbrand.entity.VoucherUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VoucherUserRepository extends JpaRepository<VoucherUser, Long>, JpaSpecificationExecutor<VoucherUser> {

}