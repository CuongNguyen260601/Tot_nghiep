package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.CancelSaleRequestDTO;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;
import com.localbrand.dto.response.ProductShowUserResponseDTO;
import com.localbrand.service.ProductSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class ProductSaleController {

    private final ProductSaleService productSaleService;

    @PostMapping(Interface_API.API.ProductSale.PRODUCT_SALE_ADD)
    public ResponseEntity<ServiceResult<ProductSaleResponseDTO>> addSaleToProduct(@Valid @RequestBody ProductSaleRequestDTO productSaleRequestDTO){
        ServiceResult<ProductSaleResponseDTO> result = this.productSaleService.addSaleToProductDetail(productSaleRequestDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @PostMapping(Interface_API.API.ProductSale.PRODUCT_SALE_CANCEL)
    public ResponseEntity<ServiceResult<ProductSaleResponseDTO>> cancelSaleToProduct(@RequestBody CancelSaleRequestDTO cancelSaleRequestDTO){
        ServiceResult<ProductSaleResponseDTO> result = this.productSaleService.cancelSaleToProductDetail(cancelSaleRequestDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.ProductSale.PRODUCT_SALE_GET_LIST)
    public ResponseEntity<ServiceResult<ProductSaleResponseDTO>> getListSaleProductAdmin(
            @RequestParam Optional<Integer> idSale,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
            ){
        ServiceResult<ProductSaleResponseDTO> result = this.productSaleService.getListProductSale(idSale,page,limit);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.ProductSale.PRODUCT_SALE_GET_LIST_USER)
    public ResponseEntity<ServiceResult<List<ProductShowUserResponseDTO>>> getListSaleProductUser(
            @RequestParam Optional<Long> userId,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<ProductShowUserResponseDTO>> result = this.productSaleService.getListProductSaleUser(page,limit,userId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
