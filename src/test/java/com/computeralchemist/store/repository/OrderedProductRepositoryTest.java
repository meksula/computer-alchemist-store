package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.order.Order;
import com.computeralchemist.store.domain.order.OrderedProduct;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderedProductRepositoryTest {
    private static long ORDER_ID;
    private final long CUSTOMER_ID = 3424;
    private final String CUSTOMER_USERNAME = "Mikolaj_Kopernik1492";
    private final long STORE_ID = 4533;
    private final String STORE_NAME = "Computer Alchemist Official";
    private final String ADDRESS = "Lublin, Lubelska 192";

    @Autowired
    private OrderedProductRepository orderedProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    private static Order order;

    @Before
    public void setUp() {
        orderRepository.deleteAll();
        orderedProductRepository.deleteAll();
    }

    private void initOrder() {
        order = new Order();
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);
        order.setAddress(ADDRESS);

        orderRepository.save(order);
    }

    private void saveFiewOrderedProduct() {
        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setOrder(order);
        OrderedProduct orderedProduct2 = new OrderedProduct();
        orderedProduct2.setOrder(order);
        OrderedProduct orderedProduct3 = new OrderedProduct();
        orderedProduct3.setOrder(order);

        orderedProductRepository.save(orderedProduct);
        orderedProductRepository.save(orderedProduct2);
        orderedProductRepository.save(orderedProduct3);
    }

    @Test
    public void shouldRemoveAllEntitesById() {
        initOrder();
        saveFiewOrderedProduct();

        orderRepository.deleteAllByOrderId(order.getOrderId());

        assertEquals(0, orderedProductRepository.findAll().size());
    }

    @After
    public void cleanUp() {
        orderedProductRepository.deleteAll();
        orderRepository.deleteAll();
    }

}