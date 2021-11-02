package com.localbrand.dto.response;

import com.localbrand.dto.SizeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeAndTagInProductShowUser {

    private SizeDTO sizeDTO;

    private List<Integer> listTag;

}
