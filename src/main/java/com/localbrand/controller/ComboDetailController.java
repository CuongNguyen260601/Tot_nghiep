package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.response.ComboDetailResponseDTO;
import com.localbrand.service.ComboDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class ComboDetailController {

    private final ComboDetailService comboDetailService;

    @GetMapping(Interface_API.API.ComboDetail.COMBO_DETAIL_FIND_BY_ID_COMBO)
    public ResponseEntity<ServiceResult<List<ComboDetailResponseDTO>>> findByIdCombo(@RequestParam Optional<Integer> idCombo, @RequestParam Optional<Integer> page){
        ServiceResult<List<ComboDetailResponseDTO>> result = comboDetailService.findByIdCombo(idCombo,page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.ComboDetail.COMBO_DETAIL_FIND_ALL)
    public ResponseEntity<ServiceResult<List<ComboDetailResponseDTO>>> findAll(@RequestParam Optional<Integer> page){
        ServiceResult<List<ComboDetailResponseDTO>> result = comboDetailService.findAll(page);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @GetMapping(Interface_API.API.ComboDetail.COMBO_DETAIL_FIND_BY_ID)
    public ResponseEntity<ServiceResult<ComboDetailResponseDTO>> getById(@PathVariable Optional<Long> idComboDetail) {
        ServiceResult<ComboDetailResponseDTO> result = this.comboDetailService.getById(idComboDetail);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
