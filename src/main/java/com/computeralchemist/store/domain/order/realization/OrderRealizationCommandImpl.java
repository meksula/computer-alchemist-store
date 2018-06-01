package com.computeralchemist.store.domain.order.realization;

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
                                       OrderedProductRepository orderedProductRepository,
                                       MailSender mailSender) {
        super(orderRepository, historyRepository, orderedProductRepository, mailSender);
    }

    @Override
    protected void postEmailToCustomer() {
        //TODO interfejs jest, jeszcze tylko dopisz tutaj jak konkretnie tego maila wysyłać.
        //TODO + Nie zapomnij o testach i nie rób długu technicznego! ;]

    }

    private String fetchUsersEmail() {
        long userId = super.order.getCustomersId();

        /**
         * Napisz w Computer Alchemist endpoint do pobrania tylko emaila użytkownika
         * */
        return null;
    }

}
