package com.localbrand.service.impl;

import com.localbrand.common.Config_Enum;
import com.localbrand.common.ServiceResult;
import com.localbrand.common.Status_Enum;
import com.localbrand.dto.NewDetailDTO;
import com.localbrand.dto.request.NewsRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.NewDetailMapping;
import com.localbrand.model_mapping.Impl.NewsMapping;
import com.localbrand.repository.NewDetailRepository;
import com.localbrand.repository.NewsRepository;
import com.localbrand.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);
    private final NewDetailRepository newDetailRepository;
    private final NewsRepository newsRepository;
    private final NewDetailMapping newDetailMapping;
    private final NewsMapping newsMapping;

    public NewsServiceImpl(NewDetailRepository newDetailRepository, NewsRepository newsRepository, NewDetailMapping newDetailMapping, NewsMapping newsMapping) {
        this.newDetailRepository = newDetailRepository;
        this.newsRepository = newsRepository;
        this.newDetailMapping = newDetailMapping;
        this.newsMapping = newsMapping;
    }

    @Override
    public ServiceResult<NewsRequestDTO> saveNews(NewsRequestDTO newsRequestDTO) {

        this.log.info("Save news: "+ newsRequestDTO);

        try {
            News news = this.newsMapping.toEntity(newsRequestDTO);

            List<NewDetail> listNewDetail = new ArrayList<>();

            for (NewDetailDTO dto: newsRequestDTO.getListNewDetail()) {
                listNewDetail.add(new NewDetail(
                        dto.getIdNewDetail(),
                        null,
                        dto.getTitleNewDetail(),
                        dto.getImageNewDetail(),
                        dto.getContent()
                ));
            }

            News newSave = this.newsRepository.save(news);
            listNewDetail = listNewDetail.stream().peek(
                    detail -> detail.setIdNew(newSave.getIdNew().intValue())
            ).collect(Collectors.toList());

            this.newDetailRepository.saveAll(listNewDetail);
            return new ServiceResult<>(HttpStatus.OK,Notification.News.SAVE_NEWS_SUCCESS, null);
        }catch (Exception ex){
            this.log.error(ex.getMessage(), ex);

            return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, Notification.News.SAVE_NEWS_FALSE, null);
        }
    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> findAll(Optional<Integer> page) {
        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<News> listNews = this.newsRepository.findAll( pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_LIST_NEWS_SUCCESS, listNews.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> findByStatus(Optional<Integer> idStatus,Optional<Integer> page) {
        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<News> listNews = this.newsRepository.findAllByIdStatus(idStatus.orElse(2), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_LIST_NEWS_SUCCESS, listNews.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<NewsResponseDTO> getById(Optional<Long> idNews) {

        this.log.info("Get news by id");

        if (idNews.isEmpty() || idNews.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.News.Validate_News.VALIDATE_ID, null);

        News news = this.newsRepository.findById(idNews.orElse(1L)).orElse(null);

        if (Objects.isNull(news))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.News.GET_NEWS_BY_ID_FALSE, null);
        List<NewDetail> listNewDetail= newDetailRepository.findByIdNew(news.getIdNew().intValue());
        System.out.println("dddd" + listNewDetail);
        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_NEWS_BY_ID_SUCCESS,this.newsMapping.toResponseDto(news,listNewDetail.stream().map(this.newDetailMapping::toDto).collect(Collectors.toList())));

    }

    @Override
    public ServiceResult<NewsRequestDTO> delete( Optional<Long> idNews) {
        News news = this.newsRepository.findById(idNews.get()).orElse(null);

        if(Objects.isNull(news))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.News.DELETE_NEWS_FALSE, null);

        if(news.getIdStatus().equals(Status_Enum.DELETE.getCode()))
            news.setIdStatus(Status_Enum.EXISTS.getCode());
        else
            news.setIdStatus(Status_Enum.DELETE.getCode());

        news = this.newsRepository.save(news);

        return new ServiceResult<>(HttpStatus.OK, Notification.News.DELETE_NEWS_SUCCESS, this.newsMapping.toDto(news));

    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> searchByTitle(String titleNews, Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<News> listNews = this.newsRepository.findAllByTitleLike("%"+titleNews+"%", pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.News.SEARCH_NEWS_BY_TITLE_SUCCESS, listNews.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

}
