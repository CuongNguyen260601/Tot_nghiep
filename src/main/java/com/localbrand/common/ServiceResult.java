package com.localbrand.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResult<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public ServiceResult(HttpStatus httpStatus, String message){
        this.status = httpStatus;
        this.message = message;
        this.data = null;
    }

}
