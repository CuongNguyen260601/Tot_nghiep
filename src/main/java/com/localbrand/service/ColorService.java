package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ColorService {

    ServiceResult<List<ColorDTO>> findAll(HttpServletRequest request, Optional<Integer> page);

    ServiceResult<List<ColorDTO>> findAllAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus,Optional<Integer> page);

    ServiceResult<ColorDTO> getById(HttpServletRequest request, Optional<Long> idColor);

    ServiceResult<ColorDTO> save(HttpServletRequest request, ColorDTO colorDTO);

    ServiceResult<ColorDTO> delete(HttpServletRequest request, ColorDTO colorDTO);

    ServiceResult<List<ColorDTO>> searchByName (HttpServletRequest request, String nameColor, Optional<Integer> page);

    ServiceResult<List<ColorDTO>> findByStatus (HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<List<ColorDTO>> findAllExists ();
}
