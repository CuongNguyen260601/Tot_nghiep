package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboRequestDTO;
import com.localbrand.dto.response.ComboResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ComboService {

    ServiceResult<ComboResponseDTO> addCombo(HttpServletRequest request, ComboRequestDTO comboRequestDTO);

    ServiceResult<List<ComboResponseDTO>> findAll(HttpServletRequest request,Optional<Integer> page);

    ServiceResult<ComboResponseDTO> getById(HttpServletRequest request,Optional<Long> idCombo);

    ServiceResult<ComboResponseDTO> delete(HttpServletRequest request,Optional<Long> idCombo);

    ServiceResult<List<ComboResponseDTO>> searchByName (HttpServletRequest request,String nameCombo, Optional<Integer> page);


}
