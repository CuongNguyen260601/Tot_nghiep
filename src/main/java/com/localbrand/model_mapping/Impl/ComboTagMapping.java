package com.localbrand.model_mapping.Impl;


import com.localbrand.dto.ComboTagDTO;
import com.localbrand.entity.ComboTag;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.TagRepository;
import org.springframework.stereotype.Component;

@Component
public class ComboTagMapping implements Mapping<ComboTagDTO, ComboTag> {

    private final TagRepository tagRepository;

    public ComboTagMapping(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public ComboTagDTO toDto(ComboTag comboTag) {
        ComboTagDTO comboTagDTO = ComboTagDTO
                .builder()
                .idComboTag(comboTag.getIdComboTag())
                .idCombo(comboTag.getIdCombo())
                .tag(tagRepository.getById(comboTag.getIdTag().longValue()))
                .build();
        return comboTagDTO;
    }

    @Override
    public ComboTag toEntity(ComboTagDTO comboTagDTO) {
        ComboTag comboTag = ComboTag
                .builder()
                .idComboTag(comboTagDTO.getIdComboTag())
                .idCombo(comboTagDTO.getIdCombo())
                .idTag(comboTagDTO.getTag().getIdTag().intValue())
                .build();


        return comboTag;
    }
}
