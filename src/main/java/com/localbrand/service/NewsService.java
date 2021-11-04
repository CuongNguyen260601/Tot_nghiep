package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;
import com.localbrand.dto.NewsDTO;
import com.localbrand.dto.request.ProductRequestDTO;
import com.localbrand.dto.response.*;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    ServiceResult<NewsDTO> saveNews (NewsDTO newsDTO);

    ServiceResult<List<NewsDTO>> findAll(Optional<Integer> page);

    ServiceResult<NewsDTO> getById(Optional<Long> idNews);

    ServiceResult<NewsDTO> delete(NewsDTO newsDTO);

    ServiceResult<List<NewsDTO>> searchByTitle (String titleNews, Optional<Integer> page);

    ServiceResult<List<NewsDTO>> findByStatus (Optional<Integer> idStatus, Optional<Integer> page);

}
