package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboDetailRequestDTO;
import com.localbrand.dto.response.ComboDetailResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ComboDetailService {

    ServiceResult<ComboDetailResponseDTO> saveComboDetail(HttpServletRequest request, ComboDetailRequestDTO comboDetailRequestDTO);

    ServiceResult<List<ComboDetailResponseDTO>> findAll(HttpServletRequest request,Optional<Integer> page);

    ServiceResult<ComboDetailResponseDTO> getById(HttpServletRequest request,Optional<Long> idComboDetail);

    ServiceResult<ComboDetailResponseDTO> delete(HttpServletRequest request,Optional<Long> idComboDetail);

    ServiceResult<List<ComboDetailResponseDTO>> findByIdCombo (HttpServletRequest request,Integer idCombo, Optional<Integer> page);

}
