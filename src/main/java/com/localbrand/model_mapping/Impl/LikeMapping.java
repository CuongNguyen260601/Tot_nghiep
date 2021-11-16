package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.LikeDTO;
import com.localbrand.entity.Like;
import com.localbrand.model_mapping.Mapping;
import org.springframework.stereotype.Component;

@Component
public class LikeMapping implements Mapping<LikeDTO, Like> {

    @Override
    public LikeDTO toDto(Like like) {
        return LikeDTO
                .builder()
                .idUser(like.getIdUser())
                .idProduct(like.getIdProduct())
                .idCombo(like.getIdCombo())
                .isLike(like.getLikeProduct())
                .build();
    }

    @Override
    public Like toEntity(LikeDTO likeDTO) {
        return Like
                .builder()
                .idUser(likeDTO.getIdUser())
                .idProduct(likeDTO.getIdProduct())
                .idCombo(likeDTO.getIdCombo())
                .likeProduct(likeDTO.getIsLike())
                .build();
    }
}
