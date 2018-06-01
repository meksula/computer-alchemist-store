package com.computeralchemist.store.domain.order.realization;

import com.computeralchemist.store.domain.order.Order;

import javax.mail.MessagingException;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

public interface MailSender {
    void sendSimpleEmail(String address, String title, String content);

    void postOrderConfirmation(Order order) throws MessagingException;

    void postShipmentConfirmation(Order order);
}
