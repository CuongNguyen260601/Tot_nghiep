package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;
import com.localbrand.service.ProductSaleService;
import com.localbrand.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final ProductSaleService productSaleService;

    @GetMapping(Interface_API.API.Sale.SALE_FIND_ALL)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> fillAll(HttpServletRequest request, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.findAll(request, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_SORT)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> fillAllAndSort(HttpServletRequest request, @RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.findAllAndSort(request, sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_BY_ID)
    public ResponseEntity<ServiceResult<SaleDTO>> getById(HttpServletRequest request, @PathVariable Optional<Long> idSale) {
        ServiceResult<SaleDTO> result = this.saleService.getById(request, idSale);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Sale.SALE_SAVE)
    public ResponseEntity<ServiceResult<SaleDTO>> save(HttpServletRequest request, @Valid @RequestBody SaleDTO saleDTO) {
        ServiceResult<SaleDTO> result = this.saleService.save(request, saleDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Sale.SALE_DELETE)
    public ResponseEntity<ServiceResult<SaleDTO>> delete(HttpServletRequest request, @Valid @RequestBody SaleDTO saleDTO) {
        ServiceResult<SaleDTO> result = this.saleService.delete(request, saleDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_SEARCH)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> searchByName(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.searchByName(request, name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> findByStatus(HttpServletRequest request, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.findByStatus(request, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Sale.SALE_ADD_TO_LIST_PRODUCT_DETAIL)
    public ResponseEntity<ServiceResult<ProductSaleResponseDTO>> addSaleToListProductDetail(HttpServletRequest request, @Valid @RequestBody ProductSaleRequestDTO productSaleRequestDTO) {
        ServiceResult<ProductSaleResponseDTO> result = this.productSaleService.addSaleToProductDetail(request, productSaleRequestDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Sale.SALE_CANCEL_TO_LIST_PRODUCT_DETAIL)
    public ResponseEntity<ServiceResult<List<ProductSaleListResponseDTO>>> cancelSaleToListProductDetail(HttpServletRequest request, List<ProductSaleCancelRequestDTO> productSaleCancelRequestDTOS) {
        ServiceResult<List<ProductSaleListResponseDTO>> result = this.productSaleService.cancelSaleToProductDetail(request, productSaleCancelRequestDTOS);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_GET_LIST_PRODUCT_SALE)
    public ResponseEntity<ServiceResult<List<ProductSaleListResponseDTO>>> cancelSaleToListProductDetail(HttpServletRequest request, @RequestParam Optional<Integer> page,@RequestParam Optional<Integer> limit) {
        ServiceResult<List<ProductSaleListResponseDTO>> result = this.productSaleService.getListProductSale(request, page, limit);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
