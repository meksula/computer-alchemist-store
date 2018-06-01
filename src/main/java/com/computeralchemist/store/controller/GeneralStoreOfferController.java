package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.components.ComponentType;
import com.computeralchemist.store.domain.order.Offered;
import com.computeralchemist.store.repository.OfferedRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@RestController
@RequestMapping(path = "/store/offer")
public class GeneralStoreOfferController {
    private OfferedRepository offeredRepository;

    public GeneralStoreOfferController(OfferedRepository offeredRepository) {
        this.offeredRepository = offeredRepository;
    }

    @GetMapping(value = "/{componentType}")
    @ResponseStatus(HttpStatus.OK)
    public List<Offered> getListOfAllOfferedProductByType(@PathVariable("componentType") String componentType) {
        Optional<List<Offered>> offeredIterable = offeredRepository.findAllByComponentType(ComponentType.valueOf(componentType));

        if (offeredIterable.isPresent())
            return offeredIterable.get();

        else throw new EmptyResultDataAccessException(0);
    }

}
