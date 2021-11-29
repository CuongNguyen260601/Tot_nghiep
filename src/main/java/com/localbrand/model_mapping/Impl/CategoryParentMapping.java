package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.CategoryChildDTO;
import com.localbrand.dto.CategoryParentDTO;
import com.localbrand.entity.Category;
import com.localbrand.model_mapping.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryParentMapping implements Mapping<CategoryParentDTO, Category> {

    private final CategoryChildMapping categoryChildMapping;

    @Override
    public CategoryParentDTO toDto(Category category) {
        return CategoryParentDTO
                .builder()
                .idCategory(category.getIdCategory())
                .nameCategory(category.getNameCategory())
                .idStatus(category.getIdStatus())
                .build();
    }

    public CategoryParentDTO toDto(Category category, List<Category> listCategory) {
        CategoryParentDTO categoryParentDTO = CategoryParentDTO
                .builder()
                .idCategory(category.getIdCategory())
                .nameCategory(category.getNameCategory())
                .idStatus(category.getIdStatus())
                .build();
        if(!Objects.isNull(listCategory))
            categoryParentDTO.setListCategoryChildDTO(listCategory.stream().map(this.categoryChildMapping::toDto).collect(Collectors.toList()));
        return  categoryParentDTO;
    }

    @Override
    public Category toEntity(CategoryParentDTO categoryParentDTO) {

        return Category
                .builder()
                .idCategory(categoryParentDTO.getIdCategory())
                .parentId(null)
                .nameCategory(categoryParentDTO.getNameCategory())
                .idStatus(categoryParentDTO.getIdStatus())
                .build();
    }

    public Category toEntity(CategoryChildDTO categoryChildDTO, Integer parentId) {

        return Category
                .builder()
                .idCategory(categoryChildDTO.getIdCategory())
                .parentId(parentId)
                .nameCategory(categoryChildDTO.getNameCategory())
                .idStatus(categoryChildDTO.getIdStatus())
                .build();
    }
}
