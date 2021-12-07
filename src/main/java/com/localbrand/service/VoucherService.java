package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.VoucherDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VoucherService {

    ServiceResult<List<VoucherDTO>> findAll(Optional<Integer> page);

    ServiceResult<List<VoucherDTO>> findAllAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<VoucherDTO> getById(Optional<Long> idVoucher);

    ServiceResult<VoucherDTO> save(VoucherDTO voucherDTO);

    ServiceResult<VoucherDTO> delete(VoucherDTO voucherDTO);

    ServiceResult<List<VoucherDTO>> searchByName (String nameVoucher, Optional<Integer> page);

    ServiceResult<List<VoucherDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

}
