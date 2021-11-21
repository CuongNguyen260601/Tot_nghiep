package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboRequestDTO;
import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.service.ComboService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
public class ComboController {
    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping(Interface_API.API.Combo.COMBO_FIND_ALL)
    public ResponseEntity<ServiceResult<List<ComboResponseDTO>>> findAllCombo(HttpServletRequest request,
                                                                            @RequestParam Optional<Integer> sort,
                                                                            @RequestParam Optional<Integer> idStatus,
                                                                            @RequestParam Optional<Integer> page) {
        ServiceResult<List<ComboResponseDTO>> result = this.comboService.findAllCombo(request,sort,idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Combo.COMBO_FIND_ALL_USER)
    public ResponseEntity<ServiceResult<List<ComboResponseDTO>>> findAllComboUser(HttpServletRequest request,
                                                                            @RequestParam Optional<Integer> limit,
                                                                            @RequestParam Optional<Integer> page) {
        ServiceResult<List<ComboResponseDTO>> result = this.comboService.findAllComboUser(request,limit,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Combo.COMBO_FIND_BY_ID)
    public ResponseEntity<ServiceResult<ComboResponseDTO>> getById(@PathVariable Optional<Long> idCombo) {
        ServiceResult<ComboResponseDTO> result = this.comboService.getById(idCombo);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Combo.COMBO_FIND_BY_ID_USER)
    public ResponseEntity<ServiceResult<ComboResponseDTO>> getByIdUser(@PathVariable Optional<Long> idCombo) {
        ServiceResult<ComboResponseDTO> result = this.comboService.getById(idCombo);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Combo.COMBO_SAVE)
    public ResponseEntity<ServiceResult<ComboResponseDTO>> save(HttpServletRequest request,@Valid @RequestBody ComboRequestDTO comboDTO) {
        ServiceResult<ComboResponseDTO> result = this.comboService.addCombo(request,comboDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Combo.COMBO_DELETE)
    public ResponseEntity<ServiceResult<ComboResponseDTO>> delete(HttpServletRequest request,@RequestParam Optional<Long> idCombo) {
        ServiceResult<ComboResponseDTO> result = this.comboService.delete(request,idCombo);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Combo.COMBO_SEARCH)
    public ResponseEntity<ServiceResult<List<ComboResponseDTO>>> searchByName(HttpServletRequest request,@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ComboResponseDTO>> result = this.comboService.searchByName(request,name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Combo.COMBO_SEARCH_USER)
    public ResponseEntity<ServiceResult<List<ComboResponseDTO>>> searchByNameUser(HttpServletRequest request,@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ComboResponseDTO>> result = this.comboService.searchByNameUser(request,name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }


}
