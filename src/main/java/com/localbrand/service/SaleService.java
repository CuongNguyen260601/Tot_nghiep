package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.SaleDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface SaleService {

    ServiceResult<List<SaleDTO>> findAll(Optional<Integer> page);

    ServiceResult<List<SaleDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<SaleDTO> getById(Optional<Long> idSale);

    ServiceResult<SaleDTO> save(SaleDTO saleDTO);

    ServiceResult<SaleDTO> delete(SaleDTO saleDTO);

    ServiceResult<List<SaleDTO>> searchByName (String nameSale, Optional<Integer> page);

    ServiceResult<List<SaleDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

}
