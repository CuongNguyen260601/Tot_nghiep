package com.localbrand.service.impl;

import com.localbrand.common.*;
import com.localbrand.dto.NewDetailDTO;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.entity.*;
import com.localbrand.exception.Notification;
import com.localbrand.model_mapping.Impl.NewDetailMapping;
import com.localbrand.model_mapping.Impl.NewsMapping;
import com.localbrand.repository.NewDetailRepository;
import com.localbrand.repository.NewsRepository;
import com.localbrand.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);
    private final NewDetailRepository newDetailRepository;
    private final NewsRepository newsRepository;
    private final NewDetailMapping newDetailMapping;
    private final NewsMapping newsMapping;

    @Override
    public ServiceResult<NewRequestDTO> saveNews(NewRequestDTO newsRequestDTO) {

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
    public ServiceResult<NewRequestDTO> delete(Optional<Long> idNews) {

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
    public ServiceResult<NewsResponseDTO> getById(Optional<Long> idNews) {

        this.log.info("Get news by id");

        if (idNews.isEmpty() || idNews.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.News.Validate_News.VALIDATE_ID, null);

        News news = this.newsRepository.findById(idNews.orElse(1L)).orElse(null);

        if (Objects.isNull(news))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.News.GET_NEWS_BY_ID_FALSE, null);
        List<NewDetail> listNewDetail= newDetailRepository.findByIdNew(news.getIdNew().intValue());
        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_NEWS_BY_ID_SUCCESS,this.newsMapping.toResponseDto(news,listNewDetail.stream().map(this.newDetailMapping::toDto).collect(Collectors.toList())));
    }

    @Override
    public ServiceResult<NewsResponseDTO> getByIdUser(Optional<Long> idNews) {

        this.log.info("Get news by id");

        if (idNews.isEmpty() || idNews.get() < 1) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.News.Validate_News.VALIDATE_ID, null);

        News news = this.newsRepository.findByIdNewAndIdStatus(idNews.orElse(1L), Status_Enum.EXISTS.getCode()).orElse(null);

        if (Objects.isNull(news))
            return new ServiceResult<>(HttpStatus.NOT_FOUND, Notification.News.GET_NEWS_BY_ID_FALSE, null);
        List<NewDetail> listNewDetail= newDetailRepository.findByIdNew(news.getIdNew().intValue());
        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_NEWS_BY_ID_SUCCESS,this.newsMapping.toResponseDto(news,listNewDetail.stream().map(this.newDetailMapping::toDto).collect(Collectors.toList())));
    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> findAllNew(Optional<Integer> sort,
                                                           Optional<Integer> idStatus,
                                                           Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable;

        if(sort.get().equals(0))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.ASC,"dateCreate"));
        else if(sort.get().equals(1))
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode(), Sort.by(Sort.Direction.DESC,"dateCreate"));
        else
            pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<News> listNew = new ArrayList<>();

        if(idStatus.isEmpty() || idStatus.get() < 1){
            listNew = this.newsRepository.findAll(pageable).toList();
        }else {
            listNew = this.newsRepository.findAllByIdStatus(idStatus.get(), pageable).toList();
        }
        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_LIST_NEWS_SUCCESS, listNew.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> findAllNewUser( Optional<Integer> limit, Optional<Integer> page) {
        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), limit.get(), Sort.by(Sort.Direction.DESC,"dateCreate"));

        List<News> listNew = new ArrayList<>();

        listNew = this.newsRepository.findAllByIdStatus(Status_Enum.EXISTS.getCode(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.News.GET_LIST_NEWS_SUCCESS, listNew.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> searchByTitle(String titleNews, Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<News> listNews = this.newsRepository.findAllByTitleLike("%"+titleNews+"%", pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.News.SEARCH_NEWS_BY_TITLE_SUCCESS, listNews.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

    @Override
    public ServiceResult<List<NewsResponseDTO>> searchByTitleUser(String titleNews, Optional<Integer> page) {

        if (page.isEmpty() || page.get() < 0) return new ServiceResult<>(HttpStatus.BAD_REQUEST, Notification.PAGE_INVALID, null);

        Pageable pageable = PageRequest.of(page.orElse(0), Config_Enum.SIZE_PAGE.getCode());

        List<News> listNews = this.newsRepository.findAllByTitleLikeAndIdStatus("%"+titleNews+"%",Status_Enum.EXISTS.getCode(), pageable).toList();

        return new ServiceResult<>(HttpStatus.OK, Notification.News.SEARCH_NEWS_BY_TITLE_SUCCESS, listNews.stream().map(this.newsMapping::toResponseDto).collect(Collectors.toList()));
    }

}
