package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.SaleResponseDTO;
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
    public ResponseEntity<ServiceResult<List<SaleResponseDTO>>> fillAll(@RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleResponseDTO>> result = this.saleService.findAll(page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_SORT)
    public ResponseEntity<ServiceResult<List<SaleResponseDTO>>> fillAllAndSort(@RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleResponseDTO>> result = this.saleService.findAllAndSort(sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_BY_ID)
    public ResponseEntity<ServiceResult<SaleResponseDTO>> getById(@PathVariable Optional<Long> idSale) {
        ServiceResult<SaleResponseDTO> result = this.saleService.getById(idSale);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Sale.SALE_SAVE)
    public ResponseEntity<ServiceResult<SaleResponseDTO>> save(@Valid @RequestBody SaleDTO saleDTO) {
        ServiceResult<SaleResponseDTO> result = this.saleService.save(saleDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Sale.SALE_DELETE)
    public ResponseEntity<ServiceResult<SaleResponseDTO>> delete(@Valid @RequestBody SaleDTO saleDTO) {
        ServiceResult<SaleResponseDTO> result = this.saleService.delete(saleDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_SEARCH)
    public ResponseEntity<ServiceResult<List<SaleResponseDTO>>> searchByName(@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleResponseDTO>> result = this.saleService.searchByName(name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<SaleResponseDTO>>> findByStatus(@RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleResponseDTO>> result = this.saleService.findByStatus(idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
