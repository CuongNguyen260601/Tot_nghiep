package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.ColorDTO;
import com.localbrand.dto.NewsDTO;
import com.localbrand.service.ColorService;
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
    public ResponseEntity<ServiceResult<List<NewsDTO>>> fillAll(@RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsDTO>> result = this.newsService.findAll(page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @GetMapping(Interface_API.API.News.NEWS_FIND_BY_ID)
    public ResponseEntity<ServiceResult<NewsDTO>> getById(@PathVariable Optional<Long> idNews) {
        ServiceResult<NewsDTO> result = this.newsService.getById(idNews);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

    @PostMapping(Interface_API.API.News.NEWS_SAVE)
    public ResponseEntity<ServiceResult<NewsDTO>> save(@Valid @RequestBody NewsDTO newsDTO) {
        ServiceResult<NewsDTO> result = this.newsService.saveNews(newsDTO);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

//    @DeleteMapping(Interface_API.API.News.NEWS_DELETE)
//    public ResponseEntity<ServiceResult<ColorDTO>> delete(@Valid @RequestBody ColorDTO colorDTO) {
//        ServiceResult<ColorDTO> result = this.colorService.delete(colorDTO);
//        return ResponseEntity
//                .status(result.getStatus().value())
//                .body(result);
//    }

    @GetMapping(Interface_API.API.News.NEWS_SEARCH)
    public ResponseEntity<ServiceResult<List<NewsDTO>>> searchByName(@RequestParam String title, @RequestParam Optional<Integer> page) {
        ServiceResult<List<NewsDTO>> result = this.newsService.searchByTitle(title,page);
        return ResponseEntity
                .status(result.getStatus().value())
                .body(result);
    }

//    @GetMapping(Interface_API.API.News.NEWS_FIND_BY_STATUS)
//    public ResponseEntity<ServiceResult<List<ColorDTO>>> findByStatus(@RequestParam Optional<Integer> idStatus, @RequestParam Optional<Integer> page) {
//        ServiceResult<List<ColorDTO>> result = this.colorService.findByStatus(idStatus,page);
//        return ResponseEntity
//                .status(result.getStatus().value())
//                .body(result);
//    }
//
//    @GetMapping(Interface_API.API.News.NEWS_FIND_EXISTS)
//    public ResponseEntity<ServiceResult<List<ColorDTO>>> findColorExist() {
//        ServiceResult<List<ColorDTO>> result = this.colorService.findAllExists();
//        return ResponseEntity
//                .status(result.getStatus().value())
//                .body(result);
//    }

}
