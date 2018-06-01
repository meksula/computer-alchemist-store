package com.computeralchemist.store.domain.order.realization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailSenderImplTest {

    @Autowired
    private MailSenderImpl mailSender;

    @Test
    public void shouldSendEmail() {
        mailSender.sendSimpleEmail("karol.meksula@onet.pl", "Welcome to Computer-alchemist-store", "Content of message");
    }

}