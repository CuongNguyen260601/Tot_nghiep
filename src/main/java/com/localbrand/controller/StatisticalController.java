package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;
import com.localbrand.dto.response.statistical.StatisticalDTO;
import com.localbrand.service.ColorService;
import com.localbrand.service.StatisticalService;
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
public class StatisticalController {

    private final StatisticalService statisticalService;

    @GetMapping(Interface_API.API.Statistical.STATISTICAL_GET_ALL)
    public ResponseEntity<ServiceResult<StatisticalDTO>> findAll(@RequestParam Integer month, @RequestParam Integer year ) {
        ServiceResult<StatisticalDTO> result = this.statisticalService.findAll(month,year);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

}
