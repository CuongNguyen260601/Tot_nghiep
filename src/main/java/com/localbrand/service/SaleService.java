package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SaleDTO;
import com.localbrand.dto.SaleResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface SaleService {

    ServiceResult<List<SaleResponseDTO>> findAll(Optional<Integer> page);

    ServiceResult<List<SaleResponseDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<SaleResponseDTO> getById(Optional<Long> idSale);

    ServiceResult<SaleResponseDTO> save(SaleDTO saleDTO);

    ServiceResult<SaleResponseDTO> delete(SaleDTO saleDTO);

    ServiceResult<List<SaleResponseDTO>> searchByName (String nameSale, Optional<Integer> page);

    ServiceResult<List<SaleResponseDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

}
