package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SaleDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface SaleService {

    ServiceResult<List<SaleDTO>> findAll(HttpServletRequest request, Optional<Integer> page);

    ServiceResult<List<SaleDTO>> findAllAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<SaleDTO> getById(HttpServletRequest request, Optional<Long> idSale);

    ServiceResult<SaleDTO> save(HttpServletRequest request, SaleDTO saleDTO);

    ServiceResult<SaleDTO> delete(HttpServletRequest request, SaleDTO saleDTO);

    ServiceResult<List<SaleDTO>> searchByName (HttpServletRequest request, String nameSale, Optional<Integer> page);

    ServiceResult<List<SaleDTO>> findByStatus (HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page);

}
