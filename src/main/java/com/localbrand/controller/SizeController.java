package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SizeDTO;
import com.localbrand.dto.response.SizeResponseDTO;
import com.localbrand.service.SizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
public class SizeController {

    private final SizeService sizeService;

    public SizeController (SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping(Interface_API.API.Size.SIZE_FIND_ALL)
    public ResponseEntity<ServiceResult<List<SizeResponseDTO>>> fillAll(HttpServletRequest request, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SizeResponseDTO>> result = this.sizeService.findAll(request, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Size.SIZE_FIND_SORT)
    public ResponseEntity<ServiceResult<List<SizeResponseDTO>>> fillAllAndSort(HttpServletRequest request, @RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> idCategory,@RequestParam Optional<Integer> page) {
        ServiceResult<List<SizeResponseDTO>> result = this.sizeService.findAllAndSort(request, sort, idStatus, idCategory, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Size.SIZE_FIND_BY_ID)
    public ResponseEntity<ServiceResult<SizeResponseDTO>> getById(HttpServletRequest request, @PathVariable Optional<Long> idSize) {
        ServiceResult<SizeResponseDTO> result = this.sizeService.getById(request, idSize);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Size.SIZE_SAVE)
    public ResponseEntity<ServiceResult<SizeResponseDTO>> save(HttpServletRequest request, @Valid @RequestBody SizeDTO sizeDTO) {
        ServiceResult<SizeResponseDTO> result = this.sizeService.save(request, sizeDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Size.SIZE_DELETE)
    public ResponseEntity<ServiceResult<SizeResponseDTO>> delete(HttpServletRequest request, @Valid @RequestBody SizeDTO sizeDTO) {
        ServiceResult<SizeResponseDTO> result = this.sizeService.delete(request, sizeDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Size.SIZE_SEARCH)
    public ResponseEntity<ServiceResult<List<SizeResponseDTO>>> searchByName(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SizeResponseDTO>> result = this.sizeService.searchByName(request, name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Size.SIZE_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<SizeResponseDTO>>> findByStatus(HttpServletRequest request, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<SizeResponseDTO>> result = this.sizeService.findByStatus(request, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Size.SIZE_FIND_EXISTS)
    public ResponseEntity<ServiceResult<List<SizeResponseDTO>>> findByStatus(@PathVariable Optional<Integer> idCategory) {
        ServiceResult<List<SizeResponseDTO>> result = this.sizeService.getSizeByIdCategory(idCategory);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }
}
