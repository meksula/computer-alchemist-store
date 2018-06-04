package com.computeralchemist.store.domain.order.realization;

import com.computeralchemist.store.domain.order.Order;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

public interface OrderRealization {
    Order realizeOrder(String storeName, long orderId);
}
