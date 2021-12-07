package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboDetailRequestDTO;
import com.localbrand.dto.response.ComboDetailResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ComboDetailService {

    ServiceResult<ComboDetailResponseDTO> saveComboDetail(ComboDetailRequestDTO comboDetailRequestDTO);

    ServiceResult<List<ComboDetailResponseDTO>> findAll(Optional<Integer> page);

    ServiceResult<ComboDetailResponseDTO> getById(Optional<Long> idComboDetail);

    ServiceResult<ComboDetailResponseDTO> delete(Optional<Long> idComboDetail);

    ServiceResult<List<ComboDetailResponseDTO>> findByIdCombo (Optional<Integer> idCombo, Optional<Integer> page);

}
