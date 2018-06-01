package com.computeralchemist.store.repository.history;

import com.computeralchemist.store.domain.order.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
public class HistoryRepositoryImplTest {

    @Autowired
    private HistoryRepositoryImpl historyRepository;

    private Order order;
    private final long ORDER_ID = 100092;
    private final long CUSTOMER_ID = 3424;
    private final String CUSTOMER_USERNAME = "Mikolaj_Kopernik1492";
    private final long STORE_ID = 4533;
    private final String STORE_NAME = "Computer Alchemist Official";

    @Test
    public void insertIntoHistoryTest() {
        initOrder();
        historyRepository.saveToHistory(order);
    }

    @Test
    public void findOrderInHistoryTest() {
        initOrder();
        historyRepository.saveToHistory(order);

        Order order = historyRepository.findOrder(ORDER_ID);
        assertEquals(CUSTOMER_USERNAME, order.getCustomersUsername());
    }

    @Test
    public void findListOfOrderTest() {
        initOrder();
        historyRepository.saveToHistory(order);
        historyRepository.saveToHistory(order);

        List<Order> orderList = historyRepository.findAllByStorename(STORE_NAME);
        assertEquals(2, orderList.size());
    }

    private void initOrder() {
        order = new Order();
        order.setOrderId(ORDER_ID);
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);
    }

    @After
    public void cleanAfterEach() {
        historyRepository.clearHistory();
    }

}