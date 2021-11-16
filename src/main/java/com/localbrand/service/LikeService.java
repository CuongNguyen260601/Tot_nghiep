package com.localbrand.service;

import com.localbrand.common.ServiceResult;
import com.localbrand.dto.LikeDTO;

public interface LikeService {

    ServiceResult<Integer> likeOrDislike(LikeDTO likeDTO);

}
