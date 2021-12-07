package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.dto.CartDTO;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.dto.response.CartListResponseDTO;
import com.localbrand.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(Interface_API.API.Cart.CART_SAVE)
    public ResponseEntity<ServiceResult<CartDTO>> saveCart(@Valid @RequestBody CartDTO cartDTO){
        ServiceResult<CartDTO> result = this.cartService.saveCart(cartDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Cart.CART_GET_BY_USER)
    public ResponseEntity<ServiceResult<CartDTO>> getCartByUser(@RequestParam Optional<Integer> idUser){
        ServiceResult<CartDTO> result = this.cartService.getCartByUser(idUser);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Cart.Cart_Product.CART_PRODUCT_GET_LIST)
    public ResponseEntity<ServiceResult<CartListResponseDTO>> getListCartProduct(
            @RequestParam Optional<Integer> idCart,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
            ){
        ServiceResult<CartListResponseDTO> result = this.cartService.getListCartProduct(idCart,page,limit);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Cart.Cart_Product.CART_PRODUCT_DELETE)
    public ResponseEntity<ServiceResult<CartProductDTO>> deleteCartProduct(@RequestParam Optional<Long> idCartProduct){
        ServiceResult<CartProductDTO> result = this.cartService.deleteCartProduct(idCartProduct);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PutMapping(Interface_API.API.Cart.Cart_Product.CART_PRODUCT_UPDATE_QUANTITY)
    public ResponseEntity<ServiceResult<CartProductDTO>> updateQuantityCartProduct(@RequestBody  CartProductDTO cartProductDTO){
        ServiceResult<CartProductDTO> result = this.cartService.updateQuantityProduct(cartProductDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Cart.Cart_Product.CART_PRODUCT_ADD)
    public ResponseEntity<ServiceResult<CartProductDTO>> addProductToCart(
           @RequestParam Optional<Long> idProductDetail,
           @RequestParam Optional<Long> idCart
    ){
        ServiceResult<CartProductDTO> result = this.cartService.addProductToCart(idProductDetail, idCart);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Cart.Cart_Product.CART_COUNT_PRODUCT_ADD)
    public ResponseEntity<ServiceResult<Integer>> getTotalCartProduct(@RequestParam Optional<Integer> idCart){
        ServiceResult<Integer> result = this.cartService.totalProductByIdCart(idCart);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Cart.Cart_Combo.CART_COMBO_DELETE)
    public ResponseEntity<ServiceResult<CartComboDTO>> deleteCartCombo(@RequestParam Optional<Long> idCartCombo){
        ServiceResult<CartComboDTO> result = this.cartService.deleteCartCombo(idCartCombo);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PutMapping(Interface_API.API.Cart.Cart_Combo.CART_COMBO_UPDATE_QUANTITY)
    public ResponseEntity<ServiceResult<CartComboDTO>> updateQuantityCartCombo(@RequestParam Optional<Long> idCartCombo,@RequestParam Integer quantity){
        ServiceResult<CartComboDTO> result = this.cartService.updateQuantityCombo(idCartCombo,quantity);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Cart.Cart_Combo.CART_COMBO_ADD)
    public ResponseEntity<ServiceResult<CartComboDTO>> addComboToCart(
            @RequestParam Optional<Long> idCombo,
            @RequestParam Optional<Long> idCart
    ){
        ServiceResult<CartComboDTO> result = this.cartService.addComboToCart(idCombo, idCart);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
