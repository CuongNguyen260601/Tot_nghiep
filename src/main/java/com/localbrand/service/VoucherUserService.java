package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.VoucherDTO;
import com.localbrand.dto.response.VoucherUserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface VoucherUserService {

    ServiceResult<List<VoucherUserResponseDTO>> getListVoucherOfUser(Optional<Integer> idUser, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<VoucherDTO> getVoucherDonate(Optional<Float> totalMoney);
}
