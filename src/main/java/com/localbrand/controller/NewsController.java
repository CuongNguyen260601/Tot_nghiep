package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewsRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService  newsService) {
        this.newsService = newsService;
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_ALL)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> fillAll(@RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.findAll(page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_BY_STATUS)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> fillByStatus(@RequestParam Optional<Integer> page ,@RequestParam Optional<Integer> idStatus) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.findByStatus(idStatus,page);
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

    @PostMapping(Interface_API.API.News.NEWS_SAVE)
    public ResponseEntity<ServiceResult<NewsRequestDTO>> save(@Valid @RequestBody NewsRequestDTO newsDTO) {
        ServiceResult<NewsRequestDTO> result = this.newsService.saveNews(newsDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.News.NEWS_DELETE)
    public ResponseEntity<ServiceResult<NewsRequestDTO>> delete(@RequestParam Optional<Long> idNews) {
        ServiceResult<NewsRequestDTO> result = this.newsService.delete(idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_SEARCH)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> searchByName(@RequestParam String title, @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.searchByTitle(title,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

}
