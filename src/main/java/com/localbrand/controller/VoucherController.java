package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.VoucherDTO;
import com.localbrand.service.VoucherService;
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
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping(Interface_API.API.Voucher.VOUCHER_FIND_ALL)
    public ResponseEntity<ServiceResult<List<VoucherDTO>>> fillAll(@RequestParam Optional<Integer> page) {
        ServiceResult<List<VoucherDTO>> result = this.voucherService.findAll(page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Voucher.VOUCHER_FIND_SORT)
    public ResponseEntity<ServiceResult<List<VoucherDTO>>> fillAllAndSort(@RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus,@RequestParam Optional<Integer> page) {
        ServiceResult<List<VoucherDTO>> result = this.voucherService.findAllAndSort(sort, idStatus, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Voucher.VOUCHER_FIND_BY_ID)
    public ResponseEntity<ServiceResult<VoucherDTO>> getById(@PathVariable Optional<Long> idVoucher) {
        ServiceResult<VoucherDTO> result = this.voucherService.getById(idVoucher);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Voucher.VOUCHER_SAVE)
    public ResponseEntity<ServiceResult<VoucherDTO>> save(@Valid @RequestBody VoucherDTO voucherDTO) {
        ServiceResult<VoucherDTO> result = this.voucherService.save(voucherDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Voucher.VOUCHER_DELETE)
    public ResponseEntity<ServiceResult<VoucherDTO>> delete(@Valid @RequestBody VoucherDTO voucherDTO) {
        ServiceResult<VoucherDTO> result = this.voucherService.delete(voucherDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Voucher.VOUCHER_SEARCH)
    public ResponseEntity<ServiceResult<List<VoucherDTO>>> searchByName(@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<VoucherDTO>> result = this.voucherService.searchByName(name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Voucher.VOUCHER_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<VoucherDTO>>> findByStatus(@RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<VoucherDTO>> result = this.voucherService.findByStatus(idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
