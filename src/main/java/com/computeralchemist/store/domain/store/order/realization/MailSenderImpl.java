package com.computeralchemist.store.domain.store.order.realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Service
public class MailSenderImpl implements MailSender {
    private JavaMailSender javaMailSender;

    public MailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail() {

    }

}
