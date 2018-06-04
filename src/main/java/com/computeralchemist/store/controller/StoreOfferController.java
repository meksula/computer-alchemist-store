package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.InvalidStoreData;
import com.computeralchemist.store.domain.components.cpu.Cpu;
import com.computeralchemist.store.domain.order.Offered;
import com.computeralchemist.store.repository.OfferedRepository;
import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

        Offered offered;
        ResponseEntity<Cpu> computerComponent;

        if (found.isPresent()) {
            offered = found.get();
        }

        else throw new EmptyResultDataAccessException(Math.toIntExact(id));

        /**
         * TODO trzeba to ładnie ułożyć. Konsumowanie zasobów powinno być tylko w jednym miejscu.
         * Autentykacja powinna być na podstawie UserDetailsService, który będzie powiązany z Computer Alchemist
         * Wczytywanie linków z properties
         *
         * !!!! Nie będę mapował każdego typu osobno. Klasa Offered ma zawierać jakiś komponent
         * */

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("karoladmin", "karoladmin"));
        computerComponent = restTemplate.getForEntity("http://localhost:8080/components/"
                + offered.getComponentType().toString() + "/"
                + String.valueOf(offered.getProductId()), Cpu.class);

        offered.setComputerComponent(computerComponent.getBody());

        return offered;
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
