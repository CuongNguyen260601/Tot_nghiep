package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SizeDTO;
import com.localbrand.dto.response.SizeResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface SizeService {

    ServiceResult<List<SizeResponseDTO>> findAll(HttpServletRequest request, Optional<Integer> page);

    ServiceResult<List<SizeResponseDTO>> findAllAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> idCategory,Optional<Integer> page);

    ServiceResult<SizeResponseDTO> getById(HttpServletRequest request, Optional<Long> idSize);

    ServiceResult<SizeResponseDTO> save(HttpServletRequest request, SizeDTO sizeDTO);

    ServiceResult<SizeResponseDTO> delete(HttpServletRequest request, SizeDTO sizeDTO);

    ServiceResult<List<SizeResponseDTO>> searchByName (HttpServletRequest request, String nameSize, Optional<Integer> page);

    ServiceResult<List<SizeResponseDTO>> findByStatus (HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<List<SizeResponseDTO>> getSizeByIdCategory (Optional<Integer> idCategory);

}
