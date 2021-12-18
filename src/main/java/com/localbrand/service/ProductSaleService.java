package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.CancelSaleRequestDTO;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;
import com.localbrand.dto.response.ProductShowUserResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ProductSaleService {

    ServiceResult<ProductSaleResponseDTO> addSaleToProductDetail(ProductSaleRequestDTO productSaleRequestDTO);

    ServiceResult<ProductSaleResponseDTO> cancelSaleToProductDetail(CancelSaleRequestDTO cancelSaleRequestDTO);

    ServiceResult<ProductSaleResponseDTO> getListProductSale(Optional<Integer> idSale, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<List<ProductShowUserResponseDTO>> getListProductSaleUser(Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId);
}
