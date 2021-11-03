package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.entity.Cart;

public interface CartService {

    ServiceResult deleteCartByUser(Integer idUser);

}
