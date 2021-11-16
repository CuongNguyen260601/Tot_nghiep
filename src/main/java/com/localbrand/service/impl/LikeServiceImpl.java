package com.localbrand.service.impl;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.LikeDTO;
import com.localbrand.entity.Like;
import com.localbrand.model_mapping.Impl.LikeMapping;
import com.localbrand.repository.LikeRepository;
import com.localbrand.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapping likeMapping;

    @Override
    public ServiceResult<Integer> likeOrDislike(LikeDTO likeDTO) {

        Integer idProduct = Objects.isNull(likeDTO.getIdProduct()) ? -1:likeDTO.getIdProduct();
        Integer idCombo = Objects.isNull(likeDTO.getIdCombo()) ? -1:likeDTO.getIdCombo();

        Like like = likeRepository.findByProductOrComboAndUser(idProduct,idCombo,likeDTO.getIdUser()).orElse(null);

        if(Objects.isNull(like)){
            Like likeSave = this.likeMapping.toEntity(likeDTO);
            this.likeRepository.save(likeSave);
        }else{
            like.setLikeProduct(likeDTO.getIsLike());
            this.likeRepository.save(like);
        }

        Integer totalLike = this.likeRepository.totalLikeByProductOrCombo(idProduct,idCombo).orElse(0);

        return new ServiceResult<>(HttpStatus.OK, "Like/Dislike is success", totalLike);
    }
}
