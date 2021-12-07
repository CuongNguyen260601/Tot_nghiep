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

    ServiceResult<ProductSaleResponseDTO> addSaleToProductDetail(HttpServletRequest request, ProductSaleRequestDTO productSaleRequestDTO);

    ServiceResult<List<ProductSaleListResponseDTO>> cancelSaleToProductDetail(HttpServletRequest request, List<ProductSaleCancelRequestDTO> productSaleCancelRequestDTOS);

    ServiceResult<List<ProductSaleListResponseDTO>> getListProductSale(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit);

}
