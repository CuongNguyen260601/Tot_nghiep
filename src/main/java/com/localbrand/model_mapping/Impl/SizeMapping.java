package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.SizeDTO;
import com.localbrand.dto.response.SizeResponseDTO;
import com.localbrand.entity.Category;
import com.localbrand.entity.Size;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SizeMapping implements Mapping<SizeDTO, Size> {

    private final CategoryRepository categoryRepository;
    private final CategoryParentMapping categoryParentMapping;

    @Override
    public SizeDTO toDto(Size size) {
        return SizeDTO
                .builder()
                .idSize(size.getIdSize())
                .nameSize(size.getNameSize())
                .idCategory(size.getIdCategory())
                .idStatus(size.getIdStatus())
                .build();
    }

    public SizeResponseDTO toDtoResponse(Size size){
        Category category = this.categoryRepository.findById(size.getIdCategory().longValue()).orElse(null);
        SizeResponseDTO sizeResponseDTO = SizeResponseDTO
                .builder()
                .idSize(size.getIdSize())
                .nameSize(size.getNameSize())
                .idStatus(size.getIdStatus())
                .build();
        sizeResponseDTO.setCategoryParent(this.categoryParentMapping.toDto(category,null));
        return sizeResponseDTO;
    }

    @Override
    public Size toEntity(SizeDTO sizeDTO) {
        return Size
                .builder()
                .idSize(sizeDTO.getIdSize())
                .nameSize(sizeDTO.getNameSize())
                .idCategory(sizeDTO.getIdCategory())
                .idStatus(sizeDTO.getIdStatus())
                .build();
    }
}
