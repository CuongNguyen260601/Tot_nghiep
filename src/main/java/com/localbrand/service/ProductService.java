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

    ServiceResult<List<ProductShowUserResponseDTO>> getAllProductOnUser(HttpServletRequest request, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<List<ProductShowUserResponseDTO>> getAllProductShowUser(
                                                            HttpServletRequest request,
                                                            Optional<Integer> sort,
                                                            Optional<Integer> idCategoryParent,
                                                            Optional<Integer> idCategoryChild,
                                                            Optional<Integer> idGender,
                                                            Optional<Float> minPrice,
                                                            Optional<Float> maxPrice,
                                                            Optional<Integer> page,
                                                            Optional<Integer> limit
    );

    ServiceResult<List<ProductShowUserResponseDTO>> searchByNameOnUser(HttpServletRequest request, String name, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<ProductShowUserResponseDTO> showProductOnUser(Optional<Long> idProduct);

    ServiceResult<ProductFilterColorResponseDTO> findProductUserByColor(Optional<Long> idProduct, Optional<Long> idColor);

    ServiceResult<ProductDetailUserDTO> findByIdProductAndIdColorAndIdSize(Optional<Long> idProduct, Optional<Integer> idColor, Optional<Integer> idSize);

    ServiceResult<List<ProductShowUserResponseDTO>> getListNewProduct(HttpServletRequest request, Optional<Integer> limit);

    ServiceResult<List<ProductShowUserResponseDTO>> getListHotProduct(HttpServletRequest request, Optional<Integer> limit);

    ServiceResult<List<ProductShowUserResponseDTO>> getListProductByCategory(HttpServletRequest request, Optional<Integer> idCategory, Optional<Integer> page, Optional<Integer> limit);

    ServiceResult<List<ProductShowUserResponseDTO>> getListProductLikeByUser(HttpServletRequest request, Optional<Integer> idUser, Optional<Integer> page, Optional<Integer> limit);
}
