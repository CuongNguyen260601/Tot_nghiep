package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface NewsService {

    ServiceResult<NewRequestDTO> saveNews (NewRequestDTO newsDTO);

    ServiceResult<List<NewsResponseDTO>> findAllNew(Optional<Integer> sort,
                                                    Optional<Integer> idStatus,
                                                    Optional<Integer> page);

    ServiceResult<List<NewsResponseDTO>> findAllNewUser(Optional<Integer> limit,
                                                        Optional<Integer> page);

    ServiceResult<NewsResponseDTO> getById(Optional<Long> idNews);

    ServiceResult<NewsResponseDTO> getByIdUser(Optional<Long> idNews);

    ServiceResult<NewRequestDTO> delete(Optional<Long> idNews);

    ServiceResult<List<NewsResponseDTO>> searchByTitle (String titleNews, Optional<Integer> page);

    ServiceResult<List<NewsResponseDTO>> searchByTitleUser (String titleNews, Optional<Integer> page);

}