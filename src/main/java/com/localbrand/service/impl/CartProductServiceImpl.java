package com.localbrand.service.impl;

import com.localbrand.common.Config_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.CartProductMapping;
import com.localbrand.repository.*;
import com.localbrand.service.CartProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class CartProductServiceImpl implements CartProductService {
    private final Logger log = LoggerFactory.getLogger(CartProductServiceImpl.class);

    private final CartProductMapping cartProductMapping;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    public CartProductServiceImpl(CartProductMapping cartProductMapping, CartProductRepository cartProductRepository, UserRepository userRepository, CartRepository cartRepository, ProductDetailRepository productDetailRepository, ProductRepository productRepository) {
        this.cartProductMapping = cartProductMapping;
        this.cartProductRepository = cartProductRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productDetailRepository = productDetailRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public ServiceResult save(CartProductDTO cartProductDTO) {
        this.log.debug("request to save cart product: {}", cartProductDTO);

        try {

            Optional<User> userOpt = this.userRepository.findById(cartProductDTO.getIdUser());
            if (!userOpt.isPresent()) {
                throw new RuntimeException(Notification.User.Validate_User.NOT_FOUND_ID);
            }

            Optional<Cart> cartOpt = this.cartRepository.findById(cartProductDTO.getIdCart().longValue());
            if (!cartOpt.isPresent()) {
                throw new RuntimeException(Notification.Cart.Validate_Cart.NOT_FOUND_ID);
            }

            Optional<ProductDetail> productDetailOpt = this.productDetailRepository.findById(cartProductDTO.getIdProductDetail().longValue());
            if (!productDetailOpt.isPresent()) {
                throw new RuntimeException(Notification.Product.Validate_Product_Request.VALIDATE_ID_PRODUCT);
            }

            Optional<Product> productOpt = this.productRepository.findById(productDetailOpt.get().getIdProduct().longValue());
            if (productOpt.get().getTotalProduct() < cartProductDTO.getQuantity()) {
                throw new RuntimeException(Notification.Cart.Cart_Product.Validate.QUANTITY_OVERFLOW);
            }

            CartProduct cartProduct = this.cartProductMapping.toEntity(cartProductDTO);
            this.cartProductRepository.save(cartProduct);

            return new ServiceResult(HttpStatus.OK, Notification.Cart.Cart_Product.SAVE_SUCCESS, cartProduct);

        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Transactional
    @Override
    public ServiceResult delete(Long id) {
        this.log.debug("request to delete cart product: {}", id);
        try {
            this.cartProductRepository.deleteById(id);
            return new ServiceResult(HttpStatus.OK, Notification.Cart.Cart_Product.DELETE_SUCCESS);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public ServiceResult findByName(Integer page, CartProductDTO cartProductDTO) {
        this.log.debug("request to search by name: {}", cartProductDTO.getProductName());
        Pageable pageable = PageRequest.of(page - 1, Config_Enum.SIZE_PAGE.getCode());
        Page<Map<String, Object>> result = this.cartProductRepository.findByName(cartProductDTO.getProductName(), cartProductDTO.getIdUser(), pageable);
        return new ServiceResult(HttpStatus.OK, "", result);
    }
}
