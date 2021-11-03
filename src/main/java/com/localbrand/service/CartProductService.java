package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CartProductDTO;

public interface CartProductService {

    ServiceResult save(CartProductDTO cartProductDTO);

    ServiceResult delete(Long id);

    ServiceResult findByName(Integer page, CartProductDTO cartProductDTO);

}
