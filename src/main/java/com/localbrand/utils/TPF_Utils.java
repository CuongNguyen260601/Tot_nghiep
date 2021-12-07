package com.localbrand.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;

@Component
@RequiredArgsConstructor
public class TPF_Utils {

    private final ServletContext app;

    public String getFile(MultipartFile file){
        File file1 = new File(app.getRealPath("/Image/"));
        if(!file1.exists()){
            file1.mkdirs();
        }
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            File newFile = new File(app.getRealPath("/Image/"+fileName));
            try {
                file.transferTo(newFile);
            }catch (Exception e){
                return null;
            }
            return "/Image/"+fileName;

        }
        return  null;
    }
}
