package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.ProductRequestDTO;
import com.localbrand.dto.response.*;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    ServiceResult<ProductResponseDTO> saveProduct (HttpServletRequest request, ProductRequestDTO productRequestDTO);

    ServiceResult<List<ProductParentResponseDTO>> getAllParent(HttpServletRequest request,
                                                              Optional<Integer> sort,
                                                              Optional<Integer> idStatus,
                                                              Optional<Integer> idCategoryParent,
                                                              Optional<Integer> idCategoryChild,
                                                              Optional<Integer> idGender,
                                                              Optional<Integer> page);

    ServiceResult<List<ProductChildResponseDTO>> getAllChild(HttpServletRequest request,
                                                             Optional<Integer> sort,
                                                             Optional<Integer> idProduct,
                                                             Optional<Integer> idStatus,
                                                             Optional<Integer> idColor,
                                                             Optional<Integer> idSize,
                                                             Optional<Integer> idTag,
                                                             Optional<Integer> page);

    ServiceResult<List<ProductParentResponseDTO>> searchByName(HttpServletRequest request, String name, Optional<Integer> page);

    ServiceResult<ProductResponseShowAdminDTO> showProduct(HttpServletRequest request, Optional<Long> idProduct);

    ServiceResult<ProductParentResponseDTO> deleteProductParent(HttpServletRequest request, Optional<Long> idProduct);

    ServiceResult<ProductChildResponseDTO> deleteProductChild(HttpServletRequest request, Optional<Long> idProductDetail);

    ServiceResult<List<ProductShowUserResponseDTO>> getAllProductOnUser(Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId);

    ServiceResult<List<ProductShowUserResponseDTO>> getAllProductShowUser(
                                                            Optional<Integer> sort,
                                                            Optional<Integer> idCategoryParent,
                                                            Optional<Integer> idCategoryChild,
                                                            Optional<Integer> idGender,
                                                            Optional<Float> minPrice,
                                                            Optional<Float> maxPrice,
                                                            Optional<Integer> page,
                                                            Optional<Integer> limit,
                                                            Optional<Long> userId
    );

    ServiceResult<List<ProductShowUserResponseDTO>> searchByNameOnUser(String name, Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId);

    ServiceResult<ProductShowUserResponseDTO> showProductOnUser(Optional<Long> idProduct);

    ServiceResult<ProductFilterColorResponseDTO> findProductUserByColor(Optional<Long> idProduct, Optional<Long> idColor);

    ServiceResult<ProductDetailUserDTO> findByIdProductAndIdColorAndIdSize(Optional<Long> idProduct, Optional<Integer> idColor, Optional<Integer> idSize);

    ServiceResult<List<ProductShowUserResponseDTO>> getListNewProduct(Optional<Integer> limit, Optional<Long> userId);

    ServiceResult<List<ProductShowUserResponseDTO>> getListHotProduct(Optional<Integer> limit, Optional<Long> userId);

    ServiceResult<List<ProductShowUserResponseDTO>> getListProductByCategory(Optional<Integer> idCategory, Optional<Integer> page, Optional<Integer> limit,Optional<Long> userId);

    ServiceResult<List<ProductShowUserResponseDTO>> getListProductLikeByUser(Optional<Integer> idUser, Optional<Integer> page, Optional<Integer> limit, Optional<Long> userId);
}
