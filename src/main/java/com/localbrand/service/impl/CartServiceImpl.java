package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.repository.CartComboRepository;
import com.localbrand.repository.CartProductRepository;
import com.localbrand.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    private final CartComboRepository cartComboRepository;
    private final CartProductRepository cartProductRepository;

    public CartServiceImpl(CartComboRepository cartComboRepository, CartProductRepository cartProductRepository) {
        this.cartComboRepository = cartComboRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional
    @Override
    public ServiceResult deleteCartByUser(Integer idUser) {
        this.log.debug("request to delete all cart of user: {}", idUser);
        try {
            this.cartComboRepository.deleteAll(idUser);
            this.cartProductRepository.deleteAll(idUser);
            this.log.debug("delete all cart successfully");
            return new ServiceResult(HttpStatus.OK, "");
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
