package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.ColorDTO;
import com.localbrand.entity.Color;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class ColorMapping implements Mapping<ColorDTO, Color>{
    @Override
    public ColorDTO toDto(Color color) {
        return ColorDTO
                .builder()
                .idColor(color.getIdColor())
                .nameColor(color.getNameColor())
                .idStatus(color.getIdStatus())
                .build();
    }

    @Override
    public Color toEntity(ColorDTO colorDTO) {
        return Color
                .builder()
                .idColor(colorDTO.getIdColor())
                .nameColor(colorDTO.getNameColor())
                .idStatus(colorDTO.getIdStatus())
                .build();
    }
}
