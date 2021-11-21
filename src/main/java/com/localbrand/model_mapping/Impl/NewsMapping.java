package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.NewDetailDTO;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.entity.News;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.NewDetailRepository;
import com.localbrand.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMapping implements Mapping<NewRequestDTO, News> {

    private final UserRepository userRepository;
    private final NewDetailRepository newDetailRepository;
    private final NewDetailMapping newDetailMapping;

    public NewsMapping(UserRepository userRepository, NewDetailRepository newDetailRepository, NewDetailMapping newDetailMapping) {
        this.userRepository = userRepository;
        this.newDetailRepository = newDetailRepository;
        this.newDetailMapping = newDetailMapping;
    }

    public NewRequestDTO toRequestDto(News news) {
        NewRequestDTO newsDTO = NewRequestDTO
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
        return newsDTO;
    }

    public NewsResponseDTO toResponseDto(News news, List<NewDetailDTO> listNewDetailDTO) {
        NewsResponseDTO newsDTO = NewsResponseDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .idStatus(news.getIdStatus())
                .imageNew(news.getImageNew())
                .build();
        String firstName = userRepository.findById(news.getIdUser().longValue()).orElse(null).getFirstName();
        String lastName = userRepository.findById(news.getIdUser().longValue()).orElse(null).getLastName();
        newsDTO.setAuthor(firstName + " " + lastName );
        newsDTO.setListNewDetail(listNewDetailDTO);
        return newsDTO;
    }

    public NewsResponseDTO toResponseDto(News news) {
        NewsResponseDTO newsDTO = NewsResponseDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .idStatus(news.getIdStatus())
                .imageNew(news.getImageNew())
                .build();
        String firstName = userRepository.findById(news.getIdUser().longValue()).orElse(null).getFirstName();
        String lastName = userRepository.findById(news.getIdUser().longValue()).orElse(null).getLastName();
        newsDTO.setAuthor(firstName + " " + lastName );
        return newsDTO;
    }

    @Override
    public NewRequestDTO toDto(News news) {
        return null;
    }

    @Override
    public News toEntity(NewRequestDTO newsDTO) {
        News news = News
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
        return news;
    }
}