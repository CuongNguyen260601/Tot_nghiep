package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewsRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    ServiceResult<NewsRequestDTO> saveNews (NewsRequestDTO newsDTO);

    ServiceResult<List<NewsResponseDTO>> findAll(Optional<Integer> page);

    ServiceResult<NewsResponseDTO> getById(Optional<Long> idNews);

    ServiceResult<NewsRequestDTO> delete(Optional<Long> idNews);

    ServiceResult<List<NewsResponseDTO>> searchByTitle (String titleNews, Optional<Integer> page);

    ServiceResult<List<NewsResponseDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

}
