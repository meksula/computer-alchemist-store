package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.StoreValidator;
import com.computeralchemist.store.domain.order.*;
import com.computeralchemist.store.domain.order.realization.OrderRealizationTemplateMethod;
import com.computeralchemist.store.repository.OrderRepository;
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
    private OrderCreator orderCreator;
    private StoreValidator storeValidator;
    private OrderRealizationTemplateMethod orderRealizationTemplateMethod;

    public OrderController(OrderRepository orderRepository,
                           OrderCreator orderCreator,
                           StoreValidator storeValidator,
                           OrderRealizationTemplateMethod orderRealizationTemplateMethod) {
        this.orderRepository = orderRepository;
        this.orderCreator = orderCreator;
        this.storeValidator = storeValidator;
        this.orderRealizationTemplateMethod = orderRealizationTemplateMethod;
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

    @PostMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order completeOrder(@PathVariable("storeName") String storeName,
                               @PathVariable("orderId") Long orderId) {

        return orderRealizationTemplateMethod.realizeOrder(storeName, orderId);
    }

}
