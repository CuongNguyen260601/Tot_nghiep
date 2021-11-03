package com.localbrand.controller;


import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartComboDTO;
import com.localbrand.service.CartComboService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
public class CartComboController {

    private final CartComboService cartComboService;

    public CartComboController(CartComboService cartComboService) {
        this.cartComboService = cartComboService;
    }

    @PostMapping(value = Interface_API.API.Cart.CartCombo.SEARCH)
    public ResponseEntity searchName(@RequestParam Integer page, @RequestBody CartComboDTO cartComboDTO) {
        ServiceResult result = this.cartComboService.findByName(cartComboDTO, page);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(value = Interface_API.API.Cart.CartCombo.SAVE)
    public ResponseEntity save(@RequestBody CartComboDTO cartComboDTO) {
        ServiceResult save = this.cartComboService.save(cartComboDTO);
        return ResponseEntity.status(save.getStatus()).body(save);
    }

    @DeleteMapping(value = Interface_API.API.Cart.CartCombo.DELETE)
    public ResponseEntity delete(@RequestBody CartComboDTO cartComboDTO) {
        ServiceResult delete = this.cartComboService.delete(cartComboDTO);
        return ResponseEntity.status(delete.getStatus()).body(delete);
    }

}
