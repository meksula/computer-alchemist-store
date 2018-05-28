package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.store.InvalidStoreData;
import com.computeralchemist.store.domain.store.order.Offered;
import com.computeralchemist.store.repository.OfferedRepository;
import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@RestController
@RequestMapping(path = "/store/{storeName}")
public class StoreOfferController {
    private OfferedRepository offeredRepository;
    private StoreRepository storeRepository;

    public StoreOfferController(OfferedRepository offeredRepository,
                                StoreRepository storeRepository) {
        this.offeredRepository = offeredRepository;
        this.storeRepository = storeRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offered addNewOfferedProduct(@PathVariable("storeName") String storeName,
                                        @RequestBody Offered offered) {
        if (storeRepository.findByStoreName(storeName).isPresent()) {
            offered.setStoreName(storeName);
            return offeredRepository.save(offered);
        }

        else throw new InvalidStoreData();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeOfferedProduct(@PathVariable("id") Long id) {
        offeredRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Offered getOfferedProduct(@PathVariable("id") Long id) {
        Optional<Offered> found = offeredRepository.findById(id);

        if (found.isPresent())
            return found.get();

        else throw new EmptyResultDataAccessException(Math.toIntExact(id));
    }

    @GetMapping(value = "/offered")
    @ResponseStatus(HttpStatus.OK)
    public List<Offered> getListOfAllOfferedProducts() {
        Iterable<Offered> iterableProducts = offeredRepository.findAll();

        List<Offered> offeredList = new ArrayList<>();
        iterableProducts.forEach(offeredList::add);

        if(offeredList.size() == 0)
            throw new EmptyResultDataAccessException(0);

        return offeredList;
    }

}
