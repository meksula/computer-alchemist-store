package com.computeralchemist.store.controller;


import com.computeralchemist.store.domain.store.InvalidStoreData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidStoreData.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String invalidStoreData() {
        return new InvalidStoreData().getMessage();
    }

}
