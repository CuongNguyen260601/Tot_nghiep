package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ColorService {

    ServiceResult<List<ColorDTO>> findAll(Optional<Integer> page);

    ServiceResult<List<ColorDTO>> getAll();

    ServiceResult<List<ColorDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus,Optional<Integer> page);

    ServiceResult<ColorDTO> getById(Optional<Long> idColor);

    ServiceResult<ColorDTO> save(ColorDTO colorDTO);

    ServiceResult<ColorDTO> delete(ColorDTO colorDTO);

    ServiceResult<List<ColorDTO>> searchByName (String nameColor, Optional<Integer> page);

    ServiceResult<List<ColorDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<List<ColorDTO>> findAllExists ();
}
