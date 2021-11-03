package com.localbrand.controller;


import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartProductDTO;
import com.localbrand.service.CartProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@RestController
//@CrossOrigin(origins = Interface_API.Cors.CORS)
//@RequestMapping(Interface_API.MAIN)
public class CartProductController {
//
//    private final CartProductService cartProductService;
//
//    public CartProductController(CartProductService cartProductService) {
//        this.cartProductService = cartProductService;
//    }
//
//    @PostMapping(value = Interface_API.API.Cart.Cart_Product.FIND_BY_NAME)
//    public ResponseEntity findBName(@RequestParam(defaultValue = "1") Integer page, @RequestBody CartProductDTO cartProductDTO) {
//        ServiceResult result = this.cartProductService.findByName(page, cartProductDTO);
//        return ResponseEntity.status(result.getStatus().value()).body(result);
//    }
//
//    @PostMapping(value = Interface_API.API.Cart.Cart_Product.SAVE)
//    public ResponseEntity save(@Valid @RequestBody CartProductDTO cartProductDTO) {
//        ServiceResult saved = this.cartProductService.save(cartProductDTO);
//        return ResponseEntity.status(saved.getStatus().value()).body(saved);
//    }
//
//    @DeleteMapping(value = Interface_API.API.Cart.Cart_Product.DELETE)
//    public ResponseEntity delete(@PathVariable Long idCartProduct) {
//        ServiceResult deleted = this.cartProductService.delete(idCartProduct);
//        return ResponseEntity.status(deleted.getStatus().value()).body(deleted);
//    }

}
