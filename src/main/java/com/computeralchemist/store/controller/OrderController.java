package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.Store;
import com.computeralchemist.store.domain.StoreValidator;
import com.computeralchemist.store.domain.order.*;
import com.computeralchemist.store.domain.order.address.Address;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@RestController
@RequestMapping("/store/order/{storeName}")
public class OrderController {
    private OrderRepository orderRepository;
    private StoreRepository storeRepository;
    private OrderCreator orderCreator;
    private StoreValidator storeValidator;

    public OrderController(OrderRepository orderRepository,
                           StoreRepository storeRepository,
                           OrderCreator orderCreator,
                           StoreValidator storeValidator) {
        this.orderRepository = orderRepository;
        this.orderCreator = orderCreator;
        this.storeRepository = storeRepository;
        this.storeValidator = storeValidator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order postCartAndPlaceOrder(@PathVariable("storeName") String storeName,
                                       @RequestBody Cart cart) {

         if (storeValidator.isStoreExist(cart.getStoreName()))
            return orderCreator.create(cart);

         else throw new EmptyResultDataAccessException(0);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getListOfAllOrder(@PathVariable("storeName") String storeName) {
        Optional<List<Order>> optional = orderRepository.findAllByStoreName(storeName);

        if (optional.isPresent())
            return optional.get();

        else throw new EmptyResultDataAccessException(0);
    }

    /**
     * Poniższy endpoint ma:
     * - wysyłać maila do klienta, że zrealizowano zamówienie;
     * - przenosić zamówienie do historii (JDBC template)
     *
     * */

    @PostMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order completeOrder(@PathVariable("storeName") String storeName,
                               @PathVariable("orderId") Long orderId) {
        return null;
    }

}
