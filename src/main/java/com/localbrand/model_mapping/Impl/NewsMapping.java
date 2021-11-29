package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.NewDetailDTO;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.entity.News;
import com.localbrand.entity.User;
import com.localbrand.exception.ErrorCodes;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.NewDetailRepository;
import com.localbrand.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsMapping implements Mapping<NewRequestDTO, News> {

    private final UserRepository userRepository;

    public NewRequestDTO toRequestDto(News news) {
        return NewRequestDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .idUser(news.getIdUser())
                .idStatus(news.getIdStatus())
                .imageNew(news.getImageNew())
                .build();
    }

    public NewsResponseDTO toResponseDto(News news, List<NewDetailDTO> listNewDetailDTO) {

        User user  = userRepository.findById(news.getIdUser().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.USER_IS_NULL));

        return NewsResponseDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .idStatus(news.getIdStatus())
                .imageNew(news.getImageNew())
                .listNewDetail(listNewDetailDTO)
                .author(user.getFirstName() + " " + user.getLastName())
                .build();

    }

    public NewsResponseDTO toResponseDto(News news) {
        User user  = userRepository.findById(news.getIdUser().longValue())
                .orElseThrow(() -> new RuntimeException(ErrorCodes.USER_IS_NULL));

        return NewsResponseDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .idStatus(news.getIdStatus())
                .author(user.getFirstName() + " " + user.getLastName())
                .imageNew(news.getImageNew())
                .build();
    }

    @Override
    public NewRequestDTO toDto(News news) {
        return null;
    }

    @Override
    public News toEntity(NewRequestDTO newsDTO) {
        return News
                .builder()
                .idNew(newsDTO.getIdNew())
                .title(newsDTO.getTitle())
                .shortContent(newsDTO.getShortContent())
                .dateCreate(newsDTO.getDateCreate())
                .viewNews(newsDTO.getViewNews())
                .idUser(newsDTO.getIdUser())
                .idStatus(newsDTO.getIdStatus())
                .imageNew(newsDTO.getImageNew())
                .build();
    }
}