package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartComboDTO;

public interface CartComboService {

    ServiceResult findByName(CartComboDTO cartComboDTO, Integer page);

    ServiceResult save(CartComboDTO cartComboDTO);

    ServiceResult delete(CartComboDTO cartComboDTO);

}
