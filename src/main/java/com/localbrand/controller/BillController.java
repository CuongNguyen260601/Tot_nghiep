package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.*;
import com.localbrand.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(Interface_API.Cors.CORS)
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping(Interface_API.API.Bill.BILL_SAVE_USER)
    public ResponseEntity<ServiceResult<BillResponseDTO>> saveBillUser(@Valid @RequestBody BillRequestDTO billRequestDTO){
        ServiceResult<BillResponseDTO> result = this.billService.saveBillUser(billRequestDTO);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @PostMapping(Interface_API.API.Bill.BILL_SAVE_ADMIN)
    public ResponseEntity<ServiceResult<BillResponseDTO>> saveBillAdmin(HttpServletRequest request, @Valid @RequestBody BillRequestDTO billRequestDTO){
        ServiceResult<BillResponseDTO> result = this.billService.saveBillAdmin(request,billRequestDTO);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @PostMapping(Interface_API.API.Bill.BILL_CANCEL_USER)
    public ResponseEntity<ServiceResult<BillResponseDTO>> cancelBillUser(
            @RequestParam Optional<Long> idBill,
            @RequestParam String reason
    ){
        ServiceResult<BillResponseDTO> result = this.billService.cancelBillUser(idBill, reason);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @PostMapping(Interface_API.API.Bill.BILL_CANCEL_ADMIN)
    public ResponseEntity<ServiceResult<BillResponseDTO>> cancelBillAdmin(
            HttpServletRequest request,
            @RequestParam Optional<Long> idBill,
            @RequestParam String reason
    ){
        ServiceResult<BillResponseDTO> result = this.billService.cancelBillAdmin(request, idBill, reason);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_USER)
    public ResponseEntity<ServiceResult<List<BillResponseDTO>>> getAllListBillUser(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> idUser
    ){
        ServiceResult<List<BillResponseDTO>> result = this.billService.getAllListBill(page,limit,idUser);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_AND_FILTER_USER)
    public ResponseEntity<ServiceResult<List<BillResponseDTO>>> getAllListBillUserAndFilterUser(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Date> startDate,
            @RequestParam Optional<Date> endDate
    ){
        ServiceResult<List<BillResponseDTO>> result = this.billService.getListBillAndSort(page, limit, idUser, sort, idStatus, startDate, endDate);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_BILL_DETAIL_USER)
    public ResponseEntity<ServiceResult<BillDetailResponseDTO>> getAllListProductBill(
            @RequestParam Optional<Integer> idBill
    ){
        ServiceResult<BillDetailResponseDTO> result = this.billService.getBillDetailByBillUser(idBill);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }


    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_ADMIN)
    public ResponseEntity<ServiceResult<List<BillResponseDTO>>> getAllListBillAdmin(
            HttpServletRequest request,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
    ){
        ServiceResult<List<BillResponseDTO>> result = this.billService.getAllListBillAdmin(request, page,limit);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_AND_FILTER_ADMIN)
    public ResponseEntity<ServiceResult<List<BillResponseDTO>>> getAllListBillUserAndFilterAdmin(
            HttpServletRequest request,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Date> startDate,
            @RequestParam Optional<Date> endDate
    ){
        ServiceResult<List<BillResponseDTO>> result = this.billService.getListBillAndSortAdmin(request, page, limit, sort, idStatus, startDate, endDate);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_BILL_DETAIL_ADMIN)
    public ResponseEntity<ServiceResult<BillDetailResponseDTO>> getBillDetailAdmin(
            HttpServletRequest request,
            @RequestParam Optional<Integer> idBill
    ){
        ServiceResult<BillDetailResponseDTO> result = this.billService.getBillDetailByBillAdmin(request, idBill);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_COMBO_BILL_ADMIN)
    public ResponseEntity<ServiceResult<List<BillComboResponseDTO>>> getAllListBillComboUserAndFilterAdmin(
            HttpServletRequest request,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> idBill
    ){
        ServiceResult<List<BillComboResponseDTO>> result = this.billService.getListBillComboByBillAdmin(request, page, limit, idBill);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_USER_OTHER)
    public ResponseEntity<ServiceResult<List<BillResponseUserDTO>>> getAllListBillUserOther(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> idUser
    ){
        ServiceResult<List<BillResponseUserDTO>> result = this.billService.getAllListBillUserOther(page,limit,idUser);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }

    @GetMapping(Interface_API.API.Bill.BILL_GET_ALL_LIST_AND_FILTER_USER_OTHER)
    public ResponseEntity<ServiceResult<List<BillResponseUserDTO>>> getAllListBillUserAndFilterUserOther(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<Integer> sort,
            @RequestParam Optional<Integer> idStatus,
            @RequestParam Optional<Date> startDate,
            @RequestParam Optional<Date> endDate
    ){
        ServiceResult<List<BillResponseUserDTO>> result = this.billService.getAllListBillUserAndSortOther(page, limit, idUser, sort, idStatus, startDate, endDate);
        return ResponseEntity
                .status(result.getStatus())
                .body(result);
    }
}
