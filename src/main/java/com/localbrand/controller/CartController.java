package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @DeleteMapping(value = Interface_API.API.Cart.DELETE_ALL)
    public ResponseEntity deleteCart(@PathVariable Integer idUser) {
        ServiceResult result = this.cartService.deleteCartByUser(idUser);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

}
