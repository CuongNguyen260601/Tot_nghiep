package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.dto.CartDTO;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.dto.response.CartComboResponseDTO;
import com.localbrand.dto.response.CartListResponseDTO;
import com.localbrand.dto.response.CartProductResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.CartComboMapping;
import com.localbrand.model_mapping.Impl.CartMapping;
import com.localbrand.model_mapping.Impl.CartProductMapping;
import com.localbrand.repository.*;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    private final CartProductRepository cartProductRepository;
    private final CartComboRepository cartComboRepository;
    private final CartMapping cartMapping;
    private final CartRepository cartRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ComboRepository comboRepository;
    private final CartProductMapping cartProductMapping;
    private final CartComboMapping cartComboMapping;

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
    public ServiceResult<CartListResponseDTO> getListCartProduct(Optional<Integer> idCart, Optional<Integer> page, Optional<Integer> limit) {
        log.info("Get list cart product");

        if(Objects.isNull(idCart) || idCart.isEmpty() || idCart.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart.VALIDATE_ID_CART, null);
        }

        if(Objects.isNull(page) || page.isEmpty() || page.get() < 0 ||
        Objects.isNull(limit) || limit.isEmpty() || limit.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);
        }

        Pageable pageable = PageRequest.of(page.orElse(0),limit.get() );

        List<CartProduct> listCartProduct = this.cartProductRepository.findAllByIdCart(idCart.get(), pageable).toList();
        List<CartCombo> listCartCombo = this.cartComboRepository.findAllByIdCart(idCart.get(), pageable).toList();

        AtomicReference<Float> totalMoney = new AtomicReference<>(0F);
        List<CartProductResponseDTO> listCartProductResult = listCartProduct.stream().map(
                cartProduct -> {
                    ProductDetail productDetail = this.productDetailRepository.findById(cartProduct.getIdProductDetail().longValue()).orElse(null);
                    if(Objects.nonNull(productDetail)) {
                        totalMoney.set(totalMoney.get()+ cartProduct.getQuantity()*productDetail.getPrice());
                        return this.cartProductMapping.toCartUserDTO(cartProduct, productDetail);
                    }else{
                        return null;
                    }
                }
        ).collect(Collectors.toList());

        List<CartComboResponseDTO> listCartComboResult = listCartCombo.stream().map(
                cartCombo -> {
                    Combo combo = this.comboRepository.findById(cartCombo.getIdCombo().longValue()).orElse(null);
                    if(Objects.nonNull(combo)) {
                        totalMoney.set(totalMoney.get()+ cartCombo.getQuantity()*combo.getPrice());
                        return this.cartComboMapping.toCartUserDTO(cartCombo, combo);
                    }else{
                        return null;
                    }
                }
        ).collect(Collectors.toList());

        Boolean isBoolean = this.cartProductRepository.countAllByIdCart(idCart.get()) - pageable.getPageSize() > 0;

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.GET_LIST_CART_BY_USER_SUCCESS,CartListResponseDTO.builder().cartProducts(listCartProductResult).cartCombos(listCartComboResult).isNextPage(isBoolean).totalMoney(totalMoney.get()).build());
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

    @Transactional
    @Override
    public ServiceResult<CartComboDTO> deleteCartCombo(Optional<Long> idCartCombo) {

        log.info("Delete cart combo");

        if(idCartCombo.isEmpty()|| idCartCombo.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Combo.VALIDATE_ID_CART_COMBO, null);
        }

        this.cartComboRepository.deleteByIdCartCombo(idCartCombo.get()).orElse(null);

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.DELETE_CART_COMBO_SUCCESS, null);
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

    @Transactional
    @Override
    public ServiceResult<CartComboDTO> updateQuantityCombo(Optional<Long> idCartCombo,Integer quantity) {
        log.info("Update quantity cart combo: "+ idCartCombo);

        CartCombo cartCombo= cartComboRepository.findById(idCartCombo.orElse(null)).orElse(null);

        if(Objects.isNull(cartCombo)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Combo.VALIDATE_COMBO, null);
        }

        Combo combo = this.comboRepository.findById(cartCombo.getIdCombo().longValue()).orElse(null);

        if(quantity > combo.getQuantity()){
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.UPDATE_CART_COMBO_NOT_ENOUGH, null);
        }

        cartCombo.setQuantity(quantity);
        this.cartComboRepository.save(cartCombo);

        return new ServiceResult<>(HttpStatus.OK, Notification.Cart.UPDATE_CART_COMBO_SUCCESS, null);
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
    public ServiceResult<CartComboDTO> addComboToCart(Optional<Long> idCombo, Optional<Long> idCart ) {
        log.info("Add combo to cart: "+idCombo);

        if(idCart.isEmpty() || idCart.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart.VALIDATE_ID_CART, null);
        }

        if(idCombo.isEmpty() || idCombo.get() < 1){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Combo.VALIDATE_COMBO, null);
        }

        Combo combo = this.comboRepository.findById(idCombo.get()).orElse(null);

        if(Objects.isNull(combo)){
            return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.Cart.Validate_Cart_Combo.VALIDATE_COMBO, null);
        }

        CartCombo cartCombo = this.cartComboRepository.findFirstByIdCartAndIdCombo(idCart.get().intValue(), combo.getIdCombo().intValue());

        if(Objects.nonNull(cartCombo)){
            if(combo.getQuantity() <= 0 || combo.getQuantity() <= cartCombo.getQuantity()){
                return new ServiceResult<>(HttpStatus.OK,"Số lượng Combo này đã đạt giới hạn! Không thể thêm Combo vào giỏ hàng.", null);
            }else {
                cartCombo.setQuantity(cartCombo.getQuantity()+1);
                cartCombo = this.cartComboRepository.save(cartCombo);
                return new ServiceResult<>(HttpStatus.OK, Notification.Cart.ADD_CART_COMBO_SUCCESS, this.cartComboMapping.toDtoCartCombo(cartCombo,combo));
            }
        }else{
            CartCombo cartCombo1  = CartCombo.builder()
                    .idCart(idCart.get().intValue())
                    .idCombo(idCombo.get().intValue())
                    .quantity(1)
                    .build();
            cartCombo1 = this.cartComboRepository.save(cartCombo1);
            return new ServiceResult<>(HttpStatus.OK, Notification.Cart.ADD_CART_COMBO_SUCCESS, this.cartComboMapping.toDtoCartCombo(cartCombo1,combo));
        }
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
