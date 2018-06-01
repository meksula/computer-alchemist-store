package com.computeralchemist.store.controller;


import com.computeralchemist.store.domain.InvalidStoreData;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
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

    @ExceptionHandler({TransactionSystemException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String invalidProductAddingData() {
        return "One of required values is empty!";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String entityNoExist() {
        return "Entity not exist";
    }

}
