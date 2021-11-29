package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.common.ServiceResult;
import com.localbrand.dto.LikeDTO;
import com.localbrand.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(Interface_API.Cors.CORS)
@RequiredArgsConstructor
@RequestMapping(Interface_API.MAIN)
public class LikeController {

    private final LikeService likeService;

    @PostMapping(Interface_API.API.Like.LIKE_SAVE)
    ResponseEntity<ServiceResult<Integer>> saleLike(@Valid @RequestBody LikeDTO likeDTO){

        ServiceResult<Integer> result = this.likeService.likeOrDislike(likeDTO);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
