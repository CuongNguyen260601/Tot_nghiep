package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.CategoryParentDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // Category parent
    ServiceResult<List<CategoryChildDTO>> findAllCategoryParent();

    ServiceResult<List<CategoryParentDTO>> findAllCategoryParent(HttpServletRequest request, Optional<Integer> page);

    ServiceResult<List<CategoryParentDTO>> findAllByPropertiesAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<CategoryParentDTO> getByIdCategoryParent(HttpServletRequest request, Optional<Long> idCategory);

    ServiceResult<CategoryParentDTO> saveCategoryParent(HttpServletRequest request, CategoryParentDTO categoryDTO);

    ServiceResult<CategoryParentDTO> deleteCategoryParent(HttpServletRequest request, CategoryParentDTO categoryDTO);

    ServiceResult<List<CategoryParentDTO>> searchByNameCategoryParent(HttpServletRequest request, String nameCategory, Optional<Integer> page);

    ServiceResult<List<CategoryParentDTO>> findByStatusCategoryParent(HttpServletRequest request, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<List<CategoryParentDTO>> getAllCategoryParent();

    // Category child

    ServiceResult<List<CategoryChildDTO>> findAllCategoryChild(HttpServletRequest request, Optional<Long> parentId, Optional<Integer> page);

    ServiceResult<List<CategoryChildDTO>> findAllCategoryChildAndSort(HttpServletRequest request, Optional<Integer> sort, Optional<Long> parentId, Optional<Long> idStatus, Optional<Integer> page);

    ServiceResult<CategoryChildDTO> getByIdCategoryChild(HttpServletRequest request, Optional<Long> idCategory);

    ServiceResult<CategoryChildDTO> saveCategoryChild(HttpServletRequest request, CategoryChildDTO categoryChildDTO);

    ServiceResult<CategoryChildDTO> deleteCategoryChild(HttpServletRequest request, CategoryChildDTO categoryChildDTO);

    ServiceResult<List<CategoryChildDTO>> searchByNameCategoryChild(HttpServletRequest request, String nameCategory, Optional<Long> parentId, Optional<Integer> page);

    ServiceResult<List<CategoryChildDTO>> findByStatusCategoryChild(HttpServletRequest request, Optional<Integer> idStatus, Optional<Long> parentId, Optional<Integer> page);

    // Service to product

    ServiceResult<List<CategoryChildDTO>> getAllCategoryChildByParentId(HttpServletRequest request, Optional<Integer> parentId);

    ServiceResult<List<CategoryChildDTO>> getAllCategoryChildByParent(HttpServletRequest request, Optional<Integer> parentId);

}