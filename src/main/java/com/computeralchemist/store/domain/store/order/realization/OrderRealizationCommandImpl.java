package com.computeralchemist.store.domain.store.order.realization;

import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.OrderedProductRepository;
import com.computeralchemist.store.repository.history.HistoryRepository;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

@Service
public class OrderRealizationCommandImpl extends OrderRealizationTemplateMethod {


    public OrderRealizationCommandImpl(OrderRepository orderRepository,
                                       HistoryRepository historyRepository,
                                       OrderedProductRepository orderedProductRepository) {
        super(orderRepository, historyRepository, orderedProductRepository);
    }
}
