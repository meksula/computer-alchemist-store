package com.computeralchemist.store.domain.order.realization;

import com.computeralchemist.store.domain.order.Order;
import com.computeralchemist.store.domain.order.address.Address;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.history.HistoryRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderRealizationTemplateMethodTest {

    @Autowired
    private OrderRealization orderRealization;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private HistoryRepository historyRepository;

    private final long STORE_ID = 5069;
    private static final String STORE_NAME = "Computer from Heaven";
    private final long CUSTOMER_ID = 38294;
    private final String CUSTOMER_USERNAME = "niko_kopernik1492";

    private Order saveOrder() {
        Order order = new Order();
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);

        Address address = new Address();
        address.setCity("Lublin");
        address.setCountry("Poland");
        address.setZipCode("21-313");
        address.setHouseNumber("324");

        order.setAddress(address.toString());
        order.setProductList(new HashSet<>());

        orderRepository.save(order);
        return order;
    }

    @Test
    public void realizeOrderTest() {
        Order order = saveOrder();

        orderRealization.realizeOrder(STORE_NAME,order.getOrderId());

        Optional<Order> optional = orderRepository.findById(order.getOrderId());
        assertFalse(optional.isPresent());

        Order optionalOrder = historyRepository.findOrder(order.getOrderId());
        assertNotNull(optionalOrder);
    }

    @After
    public void cleanUp() {
        historyRepository.clearHistory();
    }

}