package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.dto.response.statistical.StatisticalDTO;

import java.util.List;
import java.util.Optional;

public interface StatisticalService {

    ServiceResult<StatisticalDTO> findAll(Integer month, Integer year);

}