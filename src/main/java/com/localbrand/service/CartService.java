package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.dto.CartDTO;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.dto.response.CartProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CartService {

    ServiceResult<CartDTO> saveCart(CartDTO cartDTO);

    ServiceResult<CartDTO> getCartByUser(Optional<Integer> idUser);

    ServiceResult<List<CartComboDTO>> getListCartCombo(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<List<CartProductResponseDTO>> getListCartProduct(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<CartProductDTO> deleteCartProduct(Optional<Long> idCartProduct);

    ServiceResult<CartComboDTO> deleteCartCombo(Optional<Long> idCartCombo);

    ServiceResult<CartProductDTO> updateQuantityProduct(CartProductDTO cartProductDTO);

    ServiceResult<CartComboDTO> updateQuantityCombo(CartComboDTO cartComboDTO);

    ServiceResult<CartProductDTO> addProductToCart(Optional<Long> idProductDetail, Optional<Long> idCart);

    ServiceResult<CartComboDTO> addComboToCart(Optional<Long> idCombo);

    ServiceResult<Integer> totalProductByIdCart(Optional<Integer> idCart);
}
