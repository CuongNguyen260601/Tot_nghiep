package com.localbrand.controller;

import com.localbrand.common.Interface_API;
import com.localbrand.utils.TPF_Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@CrossOrigin(Interface_API.Cors.CORS)
@RestController
@RequestMapping(Interface_API.MAIN)
@RequiredArgsConstructor
public class UploadImage {

    private final TPF_Utils tpf_utils;

    @PostMapping(Interface_API.UPLOAD_IMAGE)
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file){

        if(Objects.isNull(file))
            return ResponseEntity.status(400).body("Invalid file upload");

        String link = this.tpf_utils.getFile(file);

        return ResponseEntity.status(200).body(link);
    }
}
