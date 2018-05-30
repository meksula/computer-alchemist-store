package com.computeralchemist.store.repository.history;

import com.computeralchemist.store.domain.store.order.Order;

import java.util.List;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

public interface HistoryRepository {
    Order saveToHistory(Order order);

    Order findOrder(long orderId);

    void clearHistory();

    List<Order> findAllByStorename(String storeName);
}
