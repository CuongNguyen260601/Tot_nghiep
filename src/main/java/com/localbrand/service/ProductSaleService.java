package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ProductSaleCancelRequestDTO;
import com.localbrand.dto.request.ProductSaleRequestDTO;
import com.localbrand.dto.response.ProductSaleListResponseDTO;
import com.localbrand.dto.response.ProductSaleResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ProductSaleService {

    ServiceResult<ProductSaleResponseDTO> addSaleToProductDetail(ProductSaleRequestDTO productSaleRequestDTO);

    ServiceResult<ProductSaleResponseDTO> cancelSaleToProductDetail(List<ProductSaleCancelRequestDTO> productSaleCancelRequestDTOS);

    ServiceResult<ProductSaleResponseDTO> getListProductSale(Optional<Integer> idSale, Optional<Integer> page, Optional<Integer> limit);

}
