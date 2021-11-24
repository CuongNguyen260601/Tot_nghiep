package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboRequestDTO;
import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.dto.response.NewsResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ComboService {

    ServiceResult<ComboResponseDTO> addCombo(HttpServletRequest request, ComboRequestDTO comboRequestDTO);

    ServiceResult<List<ComboResponseDTO>> findAllCombo(HttpServletRequest request ,
                                                    Optional<Integer> sort,
                                                    Optional<Integer> idStatus,
                                                    Optional<Integer> page);

    ServiceResult<List<ComboResponseDTO>> findAllComboUser(HttpServletRequest request ,
                                                        Optional<Integer> limit,
                                                        Optional<Integer> page);
    ServiceResult<ComboResponseDTO> getById(Optional<Long> idCombo);

    ServiceResult<ComboResponseDTO> delete(HttpServletRequest request,Optional<Long> idCombo);

    ServiceResult<List<ComboResponseDTO>> searchByName (HttpServletRequest request,String nameCombo, Optional<Integer> page);

    ServiceResult<List<ComboResponseDTO>> searchByNameUser(HttpServletRequest request,String nameCombo, Optional<Integer> page);


}
