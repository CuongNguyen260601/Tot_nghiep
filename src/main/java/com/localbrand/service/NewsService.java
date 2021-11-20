package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface NewsService {

    ServiceResult<NewRequestDTO> saveNews (HttpServletRequest request, NewRequestDTO newsDTO);

    ServiceResult<List<NewsResponseDTO>> findAllNew(HttpServletRequest request ,
                                                    Optional<Integer> sort,
                                                    Optional<Integer> idStatus,
                                                    Optional<Integer> page);

    ServiceResult<List<NewsResponseDTO>> findAllNewUser(HttpServletRequest request ,
                                                        Optional<Integer> limit,
                                                        Optional<Integer> page);

    ServiceResult<NewsResponseDTO> getById(HttpServletRequest request,Optional<Long> idNews);

    ServiceResult<NewRequestDTO> delete(HttpServletRequest request,Optional<Long> idNews);

    ServiceResult<List<NewsResponseDTO>> searchByTitle (HttpServletRequest request,String titleNews, Optional<Integer> page);

}