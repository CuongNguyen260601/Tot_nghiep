package com.localbrand.model_mapping.Impl;

import com.localbrand.dto.NewDetailDTO;
import com.localbrand.dto.NewsDTO;
import com.localbrand.entity.News;
import com.localbrand.model_mapping.Mapping;
import com.localbrand.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMapping implements Mapping<NewsDTO, News> {

    private final UserRepository userRepository;

    public NewsMapping(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public NewsDTO toDto(News news) {
        NewsDTO newsDTO = NewsDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .user(userRepository.findById(news.getIdUser().longValue()).orElse(null))
                .idStatus(news.getIdStatus())
                .imageNew(news.getImageNew())
                .build();
        return newsDTO;
    }

    public NewsDTO toDto(News news, List<NewDetailDTO> listNewDetailDTO) {
        NewsDTO newsDTO = NewsDTO
                .builder()
                .idNew(news.getIdNew())
                .title(news.getTitle())
                .shortContent(news.getShortContent())
                .dateCreate(news.getDateCreate())
                .viewNews(news.getViewNews())
                .user(userRepository.findById(news.getIdUser().longValue()).orElse(null))
                .idStatus(news.getIdStatus())
                .imageNew(news.getImageNew())
                .build();
        newsDTO.setListNewDetail(listNewDetailDTO);
        return newsDTO;
    }

    @Override
    public News toEntity(NewsDTO newsDTO) {
        News news = News
                .builder()
                .idNew(newsDTO.getIdNew())
                .title(newsDTO.getTitle())
                .shortContent(newsDTO.getShortContent())
                .dateCreate(newsDTO.getDateCreate())
                .viewNews(newsDTO.getViewNews())
                .idUser(newsDTO.getUser().getIdUser().intValue())
                .idStatus(newsDTO.getIdStatus())
                .imageNew(newsDTO.getImageNew())
                .build();
        return news;
    }
}
