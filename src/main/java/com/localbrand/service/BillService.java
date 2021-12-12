package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.BillRequestDTO;
import com.localbrand.dto.response.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BillService {

    ServiceResult<BillResponseDTO> saveBillUser(BillRequestDTO billRequestDTO);

    ServiceResult<BillResponseDTO> saveBillAdmin(BillRequestDTO billRequestDTO);

    ServiceResult<BillResponseDTO> cancelBillUser(Optional<Long> idBill, String reason);

    ServiceResult<BillResponseDTO> cancelBillAdmin(  Optional<Long> idBill, String reason);

    ServiceResult<List<BillResponseDTO>> getAllListBill(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser);

    ServiceResult<List<BillResponseDTO>> getListBillAndSort(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate);

    ServiceResult<BillDetailResponseDTO> getBillDetailByBillUser(Optional<Integer> idBill);

    ServiceResult<List<BillResponseDTO>> getAllListBillAdmin(Optional<Integer> page, Optional<Integer> billType,Optional<Integer> limit);

    ServiceResult<List<BillResponseDTO>> getListBillAndSortAdmin(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate);

    ServiceResult<BillDetailResponseDTO> getBillDetailByBillAdmin(Optional<Integer> idBill);

    ServiceResult<List<BillProductResponseDTO>> getListBillProductByBillAdmin(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill);

    ServiceResult<List<BillComboResponseDTO>> getListBillComboByBillAdmin(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idBill);

    ServiceResult<List<BillResponseUserDTO>> getAllListBillUserOther(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser);

    ServiceResult<List<BillResponseUserDTO>> getAllListBillUserAndSortOther(Optional<Integer> page, Optional<Integer> limit, Optional<Integer> idUser, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Date> startDate, Optional<Date> endDate);

}
