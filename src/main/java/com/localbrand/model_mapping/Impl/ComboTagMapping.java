package com.localbrand.model_mapping.Impl;


import com.localbrand.dto.ComboTagDTO;
import com.localbrand.entity.ComboTag;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComboTagMapping implements Mapping<ComboTagDTO, ComboTag> {

    private final TagRepository tagRepository;

    @Override
    public ComboTagDTO toDto(ComboTag comboTag) {
        return ComboTagDTO
                .builder()
                .idComboTag(comboTag.getIdComboTag())
                .idCombo(comboTag.getIdCombo())
                .tag(tagRepository.getById(comboTag.getIdTag().longValue()))
                .build();
    }

    @Override
    public ComboTag toEntity(ComboTagDTO comboTagDTO) {
        return ComboTag
                .builder()
                .idComboTag(comboTagDTO.getIdComboTag())
                .idCombo(comboTagDTO.getIdCombo())
                .idTag(comboTagDTO.getTag().getIdTag().intValue())
                .build();
    }
}
