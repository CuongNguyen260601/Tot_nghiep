package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ComboRequestDTO;
import com.localbrand.dto.response.ComboResponseDTO;
import com.localbrand.dto.response.NewsResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ComboService {

    ServiceResult<ComboResponseDTO> addCombo(ComboRequestDTO comboRequestDTO);

    ServiceResult<List<ComboResponseDTO>> findAllCombo(Optional<Integer> sort,
                                                    Optional<Integer> idStatus,
                                                    Optional<Integer> page);

    ServiceResult<List<ComboResponseDTO>> findAllComboUser(Optional<Integer> limit,
                                                        Optional<Integer> page);
    ServiceResult<ComboResponseDTO> getById(Optional<Long> idCombo);

    ServiceResult<ComboResponseDTO> delete(Optional<Long> idCombo);

    ServiceResult<List<ComboResponseDTO>> searchByName (String nameCombo, Optional<Integer> page);

    ServiceResult<List<ComboResponseDTO>> searchByNameUser(String nameCombo, Optional<Integer> page);


}
