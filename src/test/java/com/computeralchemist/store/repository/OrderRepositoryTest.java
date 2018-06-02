package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.order.Order;
import com.computeralchemist.store.domain.order.OrderedProduct;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@FixMethodOrder(MethodSorters.JVM)
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderRepositoryTest {
    private static OrderRepository orderRepositorySt;
    private static OrderedProductRepository orderedProductRepositorySt;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedProductRepository metadataRepository;

    private final long CUSTOMER_ID = 3424;
    private final String CUSTOMER_USERNAME = "Mikolaj_Kopernik1492";
    private final long STORE_ID = 4533;
    private final String STORE_NAME = "Computer Alchemist Official";
    private final String ADDRESS = "Lublin, Lubelska 192";

    @Before
    public void setUp() {
        orderRepositorySt = orderRepository;
        orderedProductRepositorySt = metadataRepository;
    }

    private Order order;
    private static long assignedId;

    @Test
    public void findOrderTest() {
        saveOrder();
        saveProductMetadata();

        Optional<Order> order = orderRepository.findById(assignedId);
        Order order1 = order.get();

        assertEquals(2, order1.getProductList().size());
    }

    private void saveOrder() {
        Order order = new Order();
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);
        order.setAddress(ADDRESS);

        Order order1 = orderRepository.save(order);
        assignedId = order1.getOrderId();
    }

    private void saveProductMetadata() {
        Optional<Order> optional = orderRepository.findById(assignedId);
        Order order = optional.get();

        OrderedProduct cpu = new OrderedProduct();
        cpu.setOrder(order);

        OrderedProduct productMetadata = new OrderedProduct();
        productMetadata.setOrder(order);

        metadataRepository.save(cpu);
        metadataRepository.save(productMetadata);

    }

    @Test
    public void makeOrderWithProducts() {
        order = new Order();
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);
        order.setAddress(ADDRESS);
        order.setProductList(provideSet());

        Order optionalOrder = orderRepository.save(order);
        assertNotNull(optionalOrder);
    }

    private Set<OrderedProduct> provideSet() {
        OrderedProduct cpu = new OrderedProduct();
        cpu.setUserIdPlacingOrder(74322);
        cpu.setUsernamePlacingOrder("adi283");
        cpu.setOfferedId(3);
        cpu.setOrder(order);

        OrderedProduct motherboard = new OrderedProduct();
        motherboard.setUserIdPlacingOrder(74322);
        motherboard.setUsernamePlacingOrder("adi283");
        motherboard.setOfferedId(53);
        motherboard.setOrder(order);

        Set<OrderedProduct> set = new HashSet<>();
        set.add(cpu);
        set.add(motherboard);

        return set;
    }

    @Test
    public void saveOrderWithoutProducts() {
        Order order2 = new Order();
        order2.setCustomersId(CUSTOMER_ID);
        order2.setCustomersUsername(CUSTOMER_USERNAME);
        order2.setStoreId(STORE_ID);
        order2.setStoreName(STORE_NAME);
        order2.setAddress(ADDRESS);

        orderRepository.save(order2);
    }

    @Test
    public void findAllByStoreNameTest() {
        saveOrder();

        Optional<List<Order>> optionalList = orderRepository.findAllByStoreName(STORE_NAME);
        assertTrue(optionalList.isPresent());
        assertEquals(1, optionalList.get().size());
    }

    @After
    public void cleanEachTest() {
        orderRepository.deleteAll();
        metadataRepository.deleteAll();
    }

    @AfterClass
    public static void cleanUp() {
        orderRepositorySt.deleteAll();
        orderedProductRepositorySt.deleteAll();
    }

}