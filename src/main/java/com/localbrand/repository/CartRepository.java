package com.localbrand.repository;

import com.localbrand.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    Cart findFirstByIdUserAndIdStatus(Integer idUser, Integer idStatus);
}