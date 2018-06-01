package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.InvalidStoreData;
import com.computeralchemist.store.domain.Store;
import com.computeralchemist.store.domain.StoreValidator;
import com.computeralchemist.store.domain.order.Cart;
import com.computeralchemist.store.domain.order.DefaultOrderCreator;
import com.computeralchemist.store.domain.order.Order;
import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@RestController
@RequestMapping(path = "/store")
public class StoreController {
    private StoreRepository storeRepository;
    private StoreValidator storeValidator;

    public StoreController(StoreRepository storeRepository,
                           StoreValidator storeValidator) {
        this.storeRepository = storeRepository;
        this.storeValidator = storeValidator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Store createNewStore(@RequestBody Store store) {
        if (storeValidator.validateStore(store))
            return storeRepository.save(store);
        else throw new InvalidStoreData();
    }

    @GetMapping(path = "/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public Store findStore(@PathVariable("storeId") long storeId) {
       return storeRepository.findById(storeId).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping(path = "/{storeId}")
    public ResponseEntity<?> removeStore(@PathVariable("storeId") long storeId) {
        storeRepository.deleteById(storeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
