package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.VoucherDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VoucherService {

    ServiceResult<List<VoucherDTO>> findAll(HttpServletRequest request, Optional<Integer> page);

    ServiceResult<List<VoucherDTO>> findAllAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<VoucherDTO> getById(HttpServletRequest request, Optional<Long> idVoucher);

    ServiceResult<VoucherDTO> save(HttpServletRequest request, VoucherDTO voucherDTO);

    ServiceResult<VoucherDTO> delete(HttpServletRequest request, VoucherDTO voucherDTO);

    ServiceResult<List<VoucherDTO>> searchByName (HttpServletRequest request, String nameVoucher, Optional<Integer> page);

    ServiceResult<List<VoucherDTO>> findByStatus (HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page);

}
