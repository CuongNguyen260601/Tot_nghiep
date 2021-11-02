package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SaleDTO;
import com.localbrand.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
public class SaleController {
    private final SaleService saleService;

    public SaleController (SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_ALL)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> fillAll(@RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.findAll(page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_SORT)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> fillAllAndSort(@RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.findAllAndSort(sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_BY_ID)
    public ResponseEntity<ServiceResult<SaleDTO>> getById(@PathVariable Optional<Long> idSale) {
        ServiceResult<SaleDTO> result = this.saleService.getById(idSale);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Sale.SALE_SAVE)
    public ResponseEntity<ServiceResult<SaleDTO>> save(@Valid @RequestBody SaleDTO saleDTO) {
        ServiceResult<SaleDTO> result = this.saleService.save(saleDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Sale.SALE_DELETE)
    public ResponseEntity<ServiceResult<SaleDTO>> delete(@Valid @RequestBody SaleDTO saleDTO) {
        ServiceResult<SaleDTO> result = this.saleService.delete(saleDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_SEARCH)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> searchByName(@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.searchByName(name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Sale.SALE_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<SaleDTO>>> findByStatus(@RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SaleDTO>> result = this.saleService.findByStatus(idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
