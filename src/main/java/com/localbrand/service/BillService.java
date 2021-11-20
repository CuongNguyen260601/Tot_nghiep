package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.BillProductResponseDTO;
import com.localbrand.dto.response.BillResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BillService {

    ServiceResult<BillResponseDTO> saveBillUser(BillRequestDTO billRequestDTO);

    ServiceResult<BillResponseDTO> saveBillAdmin(HttpServletRequest request, BillRequestDTO billRequestDTO);

    ServiceResult<BillResponseDTO> cancelBillUser(Optional<Long> idBill, String reason);

    ServiceResult<BillResponseDTO> cancelBillAdmin(HttpServletRequest request, Optional<Long> idBill, String reason);

    ServiceResult<List<BillResponseDTO>> getAllListBill(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser);

    ServiceResult<List<BillResponseDTO>> getListBillAndSort(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate);

    ServiceResult<List<BillProductResponseDTO>> getListBillProductByBill(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill);

    ServiceResult<List<BillResponseDTO>> getAllListBillAdmin(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<List<BillResponseDTO>> getListBillAndSortAdmin(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate);

    ServiceResult<List<BillProductResponseDTO>> getListBillProductByBillAdmin(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill);

}
