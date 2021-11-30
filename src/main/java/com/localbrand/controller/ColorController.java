package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;
import com.localbrand.service.ColorService;
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
public class ColorController {

    private final ColorService colorService;

    @GetMapping(Interface_API.API.Color.COLOR_FIND_ALL)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> fillAll(HttpServletRequest request, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.findAll(request, page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_GET_ALL)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> fillAll() {
        ServiceResult<List<ColorDTO>> result = this.colorService.getAll();
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }


    @GetMapping(Interface_API.API.Color.COLOR_FIND_SORT)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> fillAllAndSort(HttpServletRequest request, @RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus,@RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.findAllAndSort(request, sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_FIND_BY_ID)
    public ResponseEntity<ServiceResult<ColorDTO>> getById(HttpServletRequest request, @PathVariable Optional<Long> idColor) {
        ServiceResult<ColorDTO> result = this.colorService.getById(request, idColor);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Color.COLOR_SAVE)
    public ResponseEntity<ServiceResult<ColorDTO>> save(HttpServletRequest request, @Valid @RequestBody ColorDTO colorDTO) {
        ServiceResult<ColorDTO> result = this.colorService.save(request, colorDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Color.COLOR_DELETE)
    public ResponseEntity<ServiceResult<ColorDTO>> delete(HttpServletRequest request, @Valid @RequestBody ColorDTO colorDTO) {
        ServiceResult<ColorDTO> result = this.colorService.delete(request, colorDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_SEARCH)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> searchByName(HttpServletRequest request, @RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.searchByName(request, name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> findByStatus(HttpServletRequest request, @RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.findByStatus(request, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_FIND_EXISTS)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> findColorExist() {
        ServiceResult<List<ColorDTO>> result = this.colorService.findAllExists();
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

}
