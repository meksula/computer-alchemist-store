package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.order.ComponentType;
import com.computeralchemist.store.domain.store.order.Offered;
import com.computeralchemist.store.domain.store.order.Order;
import com.computeralchemist.store.domain.store.order.OrderedProductMetadata;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Table;
import java.math.BigDecimal;
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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductMetadataRepository metadataRepository;

    private final long CUSTOMER_ID = 3424;
    private final String CUSTOMER_USERNAME = "Mikolaj_Kopernik1492";
    private final long STORE_ID = 4533;
    private final String STORE_NAME = "Computer Alchemist Official";
    private final ComponentType TYPE = ComponentType.cpu;
    private final long PRODUCT_ID = 19394;

    private static final String COMPONENT_TYPE_OFFERED = "motherboard";
    private static final String STORE_NAME_OFFERED = "Computer-alchemist-official";
    private static final long PRODUCT_ID_OFFERED = 32516;
    private static final BigDecimal PRICE = BigDecimal.valueOf(459.39);
    private static final int PIECES = 10;

    @Test
    public void saveOrderTest() {
        Order order = new Order();
        order.setComponentType(TYPE);
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);
        order.setComponentType(TYPE);
        order.setProductId(PRODUCT_ID);

        orderRepository.save(order);
    }

    @Test
    public void saveProductMetaDataTest() {
        Optional<Order> optional = orderRepository.findById(1L);
        Order order = optional.get();

        OrderedProductMetadata cpu = new OrderedProductMetadata();
        cpu.setComponentType("cpu");
        cpu.setProductId(344);
        cpu.setOrder(order);

        OrderedProductMetadata productMetadata = new OrderedProductMetadata();
        productMetadata.setComponentType("motherboard");
        productMetadata.setProductId(2484);
        productMetadata.setOrder(order);

        metadataRepository.save(cpu);
        metadataRepository.save(productMetadata);

    }

    private Set<OrderedProductMetadata> prepareOfeeredSet() {
        Set<OrderedProductMetadata> offeredSet = new HashSet<>();

        OrderedProductMetadata productMetadata = new OrderedProductMetadata();
        productMetadata.setComponentType("cpu");
        productMetadata.setProductId(344);

        offeredSet.add(productMetadata);
        return offeredSet;
    }

    @Test
    public void findOrderTest() {
        Optional<Order> order = orderRepository.findById(1L);
        Order order1 = order.get();

        assertEquals(2, order1.getProductList().size());
    }

}