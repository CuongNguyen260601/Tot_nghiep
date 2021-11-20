package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.request.NewRequestDTO;
import com.localbrand.dto.response.NewsResponseDTO;
import com.localbrand.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> fillAllNew(HttpServletRequest request,
                                                                        @RequestParam Optional<Integer> sort,
                                                                        @RequestParam Optional<Integer> idStatus,
                                                                        @RequestParam Optional<Integer> page) {

        String usser = request.getAttribute("USER_NAME").toString();

        ServiceResult<List<NewsResponseDTO>> result = this.newsService.findAllNew(request,sort,idStatus,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_ALL_USER)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> fillAllNewUser(HttpServletRequest request,
                                                                           @RequestParam Optional<Integer> limit,
                                                                           @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.findAllNewUser(request,limit,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_BY_ID)
    public ResponseEntity<ServiceResult<NewsResponseDTO>> getById(HttpServletRequest request,@PathVariable Optional<Long> idNews) {
        ServiceResult<NewsResponseDTO> result = this.newsService.getById(request,idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.News.NEWS_SAVE)
    public ResponseEntity<ServiceResult<NewRequestDTO>> save(HttpServletRequest request,@Valid @RequestBody NewRequestDTO newsDTO) {
        ServiceResult<NewRequestDTO> result = this.newsService.saveNews(request,newsDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @DeleteMapping(Interface_API.API.News.NEWS_DELETE)
    public ResponseEntity<ServiceResult<NewRequestDTO>> delete(HttpServletRequest request,@RequestParam Optional<Long> idNews) {
        ServiceResult<NewRequestDTO> result = this.newsService.delete(request,idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_SEARCH)
    public ResponseEntity<ServiceResult<List<NewsResponseDTO>>> searchByName(HttpServletRequest request,@RequestParam String title, @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsResponseDTO>> result = this.newsService.searchByTitle(request,title,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

}
