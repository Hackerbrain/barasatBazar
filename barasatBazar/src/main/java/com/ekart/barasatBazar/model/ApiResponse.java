package com.ekart.barasatBazar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponse<T>{
    private LocalDateTime timeStamp;
    private int status;
    private String message;
    private T data;
    public ApiResponse(int status, String message, T data){
        this.status=status;
        this.message=message;
        this.data=data;
        this.timeStamp=LocalDateTime.now();
    }
}
