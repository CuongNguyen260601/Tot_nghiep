package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping(Interface_API.API.News.NEWS_FIND_ALL)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> fillAllNew(@RequestParam Optional<Integer> sort,
                                                                        @RequestParam Optional<Integer> idStatus,
                                                                        @RequestParam Optional<Integer> page) {

        ServiceResult<List<NewsResponseDTO>> result = this.newsService.findAllNew(sort,idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_ALL_USER)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> fillAllNewUser(@RequestParam Optional<Integer> limit,
                                                                           @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.findAllNewUser(limit,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_BY_ID)
    public ResponseEntity<ServiceResult<NewsResponseDTO>> getById(@PathVariable Optional<Long> idNews) {
        ServiceResult<NewsResponseDTO> result = this.newsService.getById(idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_SEARCH)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> searchByTitle(@RequestParam String title, @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.searchByTitle(title,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_SEARCH_USER)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> searchByTitleUser(@RequestParam String title, @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.searchByTitleUser(title,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_BY_ID_USER)
    public ResponseEntity<ServiceResult<NewsResponseDTO>> getByIdUser(@PathVariable Optional<Long> idNews) {
        ServiceResult<NewsResponseDTO> result = this.newsService.getByIdUser(idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.News.NEWS_SAVE)
    public ResponseEntity<ServiceResult<NewRequestDTO>> save(@Valid @RequestBody NewRequestDTO newsDTO) {
        ServiceResult<NewRequestDTO> result = this.newsService.saveNews(newsDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.News.NEWS_DELETE)
    public ResponseEntity<ServiceResult<NewRequestDTO>> delete(@RequestParam Optional<Long> idNews) {
        ServiceResult<NewRequestDTO> result = this.newsService.delete(idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }





}
