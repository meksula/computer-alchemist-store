package com.computeralchemist.store.domain.order.realization;

import com.computeralchemist.store.domain.order.Order;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.history.HistoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Service
public class OrderRealizationTemplateMethod implements OrderRealization {
    private OrderRepository orderRepository;
    private HistoryRepository historyRepository;
    private MailSender mailSender;
    private Order order;
    private String storeName;
    private long orderId;

    public OrderRealizationTemplateMethod(OrderRepository orderRepository,
                                          HistoryRepository historyRepository,
                                          MailSender mailSender) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.mailSender = mailSender;
    }

    @Override
    public final Order realizeOrder(String storeName, long orderId) {
        this.storeName = storeName;
        this.orderId = orderId;

        fetchOrder();
        saveOrderToHistory();
        deleteFromCurrentOrders();
        postEmailToCustomer();

        return historyRepository.findOrder(orderId);
    }

    private void fetchOrder() {
        List<Order> orderList;
        Optional<List<Order>> optionalOrders = orderRepository.findAllByStoreName(storeName);

        if (optionalOrders.isPresent()) {
            orderList = optionalOrders.get();
        }

        else throw new EmptyResultDataAccessException(0);

        orderList.stream()
                .filter(item -> item.getOrderId() == orderId)
                .findAny()
                .ifPresent(item -> this.order = item);

    }

    private void postEmailToCustomer() {
        mailSender.postShipmentConfirmation(order);
    }

    private void deleteFromCurrentOrders() {
        orderRepository.delete(order);
    }

    private void saveOrderToHistory() {
        historyRepository.saveToHistory(this.order);
    }

}
