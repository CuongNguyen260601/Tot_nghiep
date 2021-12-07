package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.VoucherDTO;
import com.localbrand.dto.response.VoucherUserResponseDTO;
import com.localbrand.service.VoucherUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Interface_API.MAIN)
@CrossOrigin(Interface_API.Cors.CORS)
@RequiredArgsConstructor
public class VoucherUserController {

    private final VoucherUserService voucherUserService;

    @GetMapping(Interface_API.API.VoucherUser.VOUCHER_USER_GET_LIST)
    public ResponseEntity<ServiceResult<List<VoucherUserResponseDTO>>> getListVoucherOfUser(
            @RequestParam Optional<Integer> idUser,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit
            ){
        ServiceResult<List<VoucherUserResponseDTO>> result = this.voucherUserService.getListVoucherOfUser(idUser,page,limit);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping(Interface_API.API.VoucherUser.VOUCHER_USER_GET_DONATE)
    public ResponseEntity<ServiceResult<VoucherDTO>> getListVoucherDonate(
            @RequestParam Optional<Float> totalMoney
    ){
        ServiceResult<VoucherDTO> result = this.voucherUserService.getVoucherDonate(totalMoney);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
