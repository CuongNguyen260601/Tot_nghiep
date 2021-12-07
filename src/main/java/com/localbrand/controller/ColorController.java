package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;
import com.localbrand.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ServiceResult<List<ColorDTO>>> fillAll(@RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.findAll(page);
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
    public ResponseEntity<ServiceResult<List<ColorDTO>>> fillAllAndSort(@RequestParam Optional<Integer> sort, @RequestParam Optional<Integer> idStatus,@RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.findAllAndSort(sort, idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_FIND_BY_ID)
    public ResponseEntity<ServiceResult<ColorDTO>> getById(@PathVariable Optional<Long> idColor) {
        ServiceResult<ColorDTO> result = this.colorService.getById(idColor);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.Color.COLOR_SAVE)
    public ResponseEntity<ServiceResult<ColorDTO>> save(@Valid @RequestBody ColorDTO colorDTO) {
        ServiceResult<ColorDTO> result = this.colorService.save(colorDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.Color.COLOR_DELETE)
    public ResponseEntity<ServiceResult<ColorDTO>> delete(@Valid @RequestBody ColorDTO colorDTO) {
        ServiceResult<ColorDTO> result = this.colorService.delete(colorDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_SEARCH)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> searchByName(@RequestParam String name, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.searchByName(name,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.Color.COLOR_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<ColorDTO>>> findByStatus(@RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
        ServiceResult<List<ColorDTO>> result = this.colorService.findByStatus(idStatus,page);
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
