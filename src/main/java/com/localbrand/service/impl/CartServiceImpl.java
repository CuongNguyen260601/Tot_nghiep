package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.dto.CartDTO;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.dto.ProductDetailDTO;
import com.localbrand.dto.response.CartProductResponseDTO;
import com.localbrand.entity.Cart;
import com.localbrand.entity.CartProduct;
import com.localbrand.entity.ProductDetail;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.CartMapping;
import com.localbrand.model_mapping.Impl.CartProductMapping;
import com.localbrand.repository.CartComboRepository;
import com.localbrand.repository.CartProductRepository;
import com.localbrand.repository.CartRepository;
import com.localbrand.repository.ProductDetailRepository;
import com.localbrand.service.CartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    private final CartProductRepository cartProductRepository;
    private final CartMapping cartMapping;
    private final CartRepository cartRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CartProductMapping cartProductMapping;

    @Transactional
    @Override
    public ServiceResult<CartDTO> saveCart(CartDTO cartDTO) {
        log.info("Save cart : " + cartDTO);
        try {
            Cart cart = this.cartMapping.toEntity(cartDTO);
            cart = this.cartRepository.save(cart);
            CartDTO cartDTOSave = this.cartMapping.toDto(cart);
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.SAVE_CART_SUCCESS, cartDTOSave);
        }catch (Exception ex){
            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.Cart.SAVE_CART_FALSE, null);
        }
    }

    @Override
    public ServiceResult<CartDTO> getCartByUser(Optional<Integer> idUser) {
        log.info("Get cart by user: "+idUser);

        if(Objects.isNull(idUser) || !idUser.isEmpty() || idUser.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart.VALIDATE_ID_USER, null);
        }

        Cart cart = this.cartRepository.findFirstByIdUserAndIdStatus(idUser.get().intValue(), Status_Enum.EXISTS.getCode());

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.GET_CART_BY_USER_SUCCESS, this.cartMapping.toDto(cart));
    }

    @Override
    public ServiceResult<List<CartProductResponseDTO>> getListCartProduct(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit) {
        log.info("Get list cart product");

        if(Objects.isNull(idCart) || idCart.isEmpty() || idCart.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart.VALIDATE_ID_CART, null);
        }

        if(Objects.isNull(page) || page.isEmpty() || page.get() < 0 ||
        Objects.isNull(limit) || limit.isEmpty() || limit.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0),limit.get() );

        List<CartProduct> listCart = this.cartProductRepository.findAllByIdCart(idCart.get(), pageable).toList();

        List<CartProductResponseDTO> listCartProduct = listCart.stream().map(
                cartProduct -> {
                    ProductDetail productDetail = this.productDetailRepository.findById(cartProduct.getIdProductDetail().longValue()).orElse(null);
                    if(Objects.nonNull(productDetail)) {
                        return this.cartProductMapping.toCartUserDTO(cartProduct, productDetail);
                    }else{
                        return null;
                    }
                }
        ).collect(Collectors.toList());
        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.GET_LIST_CART_PRODUCT_BY_USER_SUCCESS,listCartProduct);
    }

    @Override
    public ServiceResult<List<CartComboDTO>> getListCartCombo(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit) {
        return null;
    }

    @Transactional
    @Override
    public ServiceResult<CartProductDTO> deleteCartProduct(Optional<Long> idCartProduct) {

        log.info("Delete cart product");

        if(idCartProduct.isEmpty()|| idCartProduct.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Product.VALIDATE_ID_CART_PRODUCT, null);
        }

        this.cartProductRepository.deleteByIdCartProduct(idCartProduct.get()).orElse(null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.DELETE_CART_PRODUCT_SUCCESS, null);
    }

    @Override
    public ServiceResult<CartComboDTO> deleteCartCombo(Optional<Long> idCartCombo) {
        return null;
    }

    @Transactional
    @Override
    public ServiceResult<CartProductDTO> updateQuantityProduct(CartProductDTO cartProductDTO) {
        log.info("Update quantity cart product: "+cartProductDTO);

        ProductDetail productDetail = this.productDetailRepository.findById(cartProductDTO.getProductDetailDTO().getIdProductDetail()).orElse(null);

        if(Objects.isNull(productDetail)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Product.VALIDATE_PRODUCT_DETAIL, null);
        }

        if(cartProductDTO.getQuantity() > productDetail.getQuantity()){
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.UPDATE_CART_PRODUCT_NOT_ENOUGH, cartProductDTO);
        }

        CartProduct cartProduct = this.cartProductMapping.toEntity(cartProductDTO);

        cartProduct = this.cartProductRepository.save(cartProduct);

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.UPDATE_CART_PRODUCT_SUCCESS, this.cartProductMapping.toDtoCartProduct(cartProduct,productDetail));
    }

    @Override
    public ServiceResult<CartComboDTO> updateQuantityCombo(CartComboDTO cartComboDTO) {
        return null;
    }

    @Override
    public ServiceResult<CartProductDTO> addProductToCart(Optional<Long> idProductDetail, Optional<Long> idCart) {
        log.info("Add product to cart: "+idProductDetail);

        if(idCart.isEmpty() || idCart.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart.VALIDATE_ID_CART, null);
        }

        if(idProductDetail.isEmpty() || idProductDetail.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Product.VALIDATE_PRODUCT_DETAIL, null);
        }

        ProductDetail productDetail = this.productDetailRepository.findById(idProductDetail.get()).orElse(null);

        if(Objects.isNull(productDetail)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Product.VALIDATE_PRODUCT_DETAIL, null);
        }

        if(productDetail.getQuantity() == 0){
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.UPDATE_CART_PRODUCT_NOT_ENOUGH, null);
        }

        CartProduct cartProduct = this.cartProductRepository.findFirstByIdCartAndIdProductDetail(idCart.get().intValue(), idProductDetail.get().intValue());

        if(Objects.nonNull(cartProduct)){
            cartProduct.setQuantity(cartProduct.getQuantity()+1);
            cartProduct = this.cartProductRepository.save(cartProduct);
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.ADD_CART_PRODUCT_SUCCESS, this.cartProductMapping.toDtoCartProduct(cartProduct,productDetail));
        }else{
            CartProduct cartProduct1 = CartProduct.builder()
                    .idCart(idCart.get().intValue())
                    .idProductDetail(idProductDetail.get().intValue())
                    .quantity(1)
                    .build();
            cartProduct1 = this.cartProductRepository.save(cartProduct1);
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.ADD_CART_PRODUCT_SUCCESS, this.cartProductMapping.toDtoCartProduct(cartProduct1,productDetail));
        }

    }

    @Override
    public ServiceResult<CartComboDTO> addComboToCart(Optional<Long> idCombo) {
        return null;
    }

    @Override
    public ServiceResult<Integer> totalProductByIdCart(Optional<Integer> idCart) {
        if(idCart.isEmpty() || idCart.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart.VALIDATE_ID_CART, null);
        }

        Integer totalProduct = this.cartProductRepository.countAllByIdCart(idCart.get());

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.COUNT_TOTAL_CART_PRODUCT, totalProduct);
    }
}
