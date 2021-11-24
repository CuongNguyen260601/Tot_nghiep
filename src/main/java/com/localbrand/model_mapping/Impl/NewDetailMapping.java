package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.NewDetailDTO;
import com.localbrand.entity.NewDetail;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class NewDetailMapping implements Mapping<NewDetailDTO, NewDetail> {

    @Override
    public NewDetailDTO toDto(NewDetail newDetail) {
        NewDetailDTO newDetailDTO = NewDetailDTO
                .builder()
                .idNewDetail(newDetail.getIdNewDetail())
                .idNew(newDetail.getIdNew())
                .titleNewDetail(newDetail.getTitleNewDetail())
                .imageNewDetail(newDetail.getImageNewDetail())
                .content(newDetail.getContent())
                .build();
        return newDetailDTO;
    }

    @Override
    public NewDetail toEntity(NewDetailDTO newDetailDTO) {
        NewDetail newDetail = NewDetail
                .builder()
                .idNewDetail(newDetailDTO.getIdNewDetail())
                .idNew(newDetailDTO.getIdNew())
                .titleNewDetail(newDetailDTO.getTitleNewDetail())
                .imageNewDetail(newDetailDTO.getImageNewDetail())
                .content(newDetailDTO.getContent())
                .build();
        return newDetail;
    }
}