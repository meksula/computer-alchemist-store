package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.store.InvalidStoreData;
import com.computeralchemist.store.domain.store.Store;
import com.computeralchemist.store.domain.store.StoreValidator;
import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
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

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Store createNewStore(@RequestBody Store store) {
        if (new StoreValidator().validateStore(store))
            return storeRepository.save(store);
        else throw new InvalidStoreData();
    }

    @GetMapping(path = "/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public Store findStore(@PathVariable("storeId") long storeId) {
       return storeRepository.findById(storeId).orElseThrow(ResourceNotFoundException::new);
    }

}
