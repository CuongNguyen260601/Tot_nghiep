package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SizeDTO;
import com.localbrand.dto.response.SizeResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface SizeService {

    ServiceResult<List<SizeResponseDTO>> findAll(Optional<Integer> page);

    ServiceResult<List<SizeResponseDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> idCategory,Optional<Integer> page);

    ServiceResult<SizeResponseDTO> getById(Optional<Long> idSize);

    ServiceResult<SizeResponseDTO> save(SizeDTO sizeDTO);

    ServiceResult<SizeResponseDTO> delete(SizeDTO sizeDTO);

    ServiceResult<List<SizeResponseDTO>> searchByName (String nameSize, Optional<Integer> page);

    ServiceResult<List<SizeResponseDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<List<SizeResponseDTO>> getSizeByIdCategory (Optional<Integer> idCategory);

    ServiceResult<List<SizeResponseDTO>> getSizeByCategory (Optional<Integer> idCategory);

}
