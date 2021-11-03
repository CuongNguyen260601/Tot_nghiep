package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.dto.CartDTO;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.dto.ProductDetailDTO;
import com.localbrand.entity.Cart;
import com.localbrand.model_mapping.Impl.CartMapping;
import com.localbrand.repository.CartComboRepository;
import com.localbrand.repository.CartProductRepository;
import com.localbrand.repository.CartRepository;
import com.localbrand.service.CartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

//    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
//    private final CartComboRepository cartComboRepository;
//    private final CartProductRepository cartProductRepository;
//    private final CartMapping cartMapping;
//    private final CartRepository cartRepository;
//
//    @Transactional
//    @Override
//    public ServiceResult<CartDTO> saveCart(CartDTO cartDTO) {
//        log.info("Save cart :" + cartDTO);
//        try {
//            Cart cart = this.cartMapping.toEntity(cartDTO);
//            cart = this.cartRepository.save(cart);
//            CartDTO cartDTOSave = this.cartMapping.toDto(cart);
//            return new ServiceResult<>(HttpStatus.OK,"", cartDTOSave);
//        }catch (Exception ex){
//            return null;
//        }
//    }
//
//    @Override
//    public ServiceResult<CartDTO> getCartByUser(Optional<Integer> idUser) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<List<CartProductDTO>> getListCartProduct(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<List<CartComboDTO>> getListCartCombo(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<CartDTO> deleteCartProduct(Optional<Integer> idCartProduct) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<CartDTO> deleteCartCombo(Optional<Integer> idCartCombo) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<CartProductDTO> updateQuantityProduct(CartProductDTO cartProductDTO) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<CartComboDTO> updateQuantityCombo(CartComboDTO cartComboDTO) {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<CartDTO> addProductToCart(ProductDetailDTO productDetailDTO) {
//        return null;
//    }
}
