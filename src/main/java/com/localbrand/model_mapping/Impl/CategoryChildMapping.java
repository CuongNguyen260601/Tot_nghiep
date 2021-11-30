package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.entity.Category;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class CategoryChildMapping implements Mapping<CategoryChildDTO, Category>{
    @Override
    public CategoryChildDTO toDto(Category category) {

        return CategoryChildDTO
                .builder()
                .idCategory(category.getIdCategory())
                .parentId(category.getParentId())
                .nameCategory(category.getNameCategory())
                .idStatus(category.getIdStatus())
                .build();
    }

    @Override
    public Category toEntity(CategoryChildDTO categoryChildDTO) {
        return Category
                .builder()
                .idCategory(categoryChildDTO.getIdCategory())
                .parentId(categoryChildDTO.getParentId())
                .nameCategory(categoryChildDTO.getNameCategory())
                .idStatus(categoryChildDTO.getIdStatus())
                .build();

    }
}
