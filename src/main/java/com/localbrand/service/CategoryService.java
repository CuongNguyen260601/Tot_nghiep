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

    ServiceResult<List<CategoryParentDTO>> findAllCategoryParent(Optional<Integer> page);

    ServiceResult<List<CategoryParentDTO>> findAllByPropertiesAndSort(Optional<Integer> sort, Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<CategoryParentDTO> getByIdCategoryParent(Optional<Long> idCategory);

    ServiceResult<CategoryParentDTO> saveCategoryParent(CategoryParentDTO categoryDTO);

    ServiceResult<CategoryParentDTO> deleteCategoryParent(CategoryParentDTO categoryDTO);

    ServiceResult<List<CategoryParentDTO>> searchByNameCategoryParent(String nameCategory, Optional<Integer> page);

    ServiceResult<List<CategoryParentDTO>> findByStatusCategoryParent(Optional<Integer> idStatus, Optional<Integer> page);

    ServiceResult<List<CategoryParentDTO>> getAllCategoryParent();

    // Category child

    ServiceResult<List<CategoryChildDTO>> findAllCategoryChild(Optional<Long> parentId, Optional<Integer> page);

    ServiceResult<List<CategoryChildDTO>> findAllCategoryChildAndSort(  Optional<Integer> sort, Optional<Long> parentId, Optional<Long> idStatus, Optional<Integer> page);

    ServiceResult<CategoryChildDTO> getByIdCategoryChild(Optional<Long> idCategory);

    ServiceResult<CategoryChildDTO> saveCategoryChild(CategoryChildDTO categoryChildDTO);

    ServiceResult<CategoryChildDTO> deleteCategoryChild(CategoryChildDTO categoryChildDTO);

    ServiceResult<List<CategoryChildDTO>> searchByNameCategoryChild(String nameCategory, Optional<Long> parentId, Optional<Integer> page);

    ServiceResult<List<CategoryChildDTO>> findByStatusCategoryChild(Optional<Integer> idStatus, Optional<Long> parentId, Optional<Integer> page);

    // Service to product

    ServiceResult<List<CategoryChildDTO>> getAllCategoryChildByParentId(Optional<Integer> parentId);

    ServiceResult<List<CategoryChildDTO>> getAllCategoryChildByParent(Optional<Integer> parentId);

}