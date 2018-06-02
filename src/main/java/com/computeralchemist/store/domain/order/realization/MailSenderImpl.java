package com.computeralchemist.store.domain.order.realization;

import com.computeralchemist.store.domain.order.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

@Service
public class MailSenderImpl implements MailSender {
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    public MailSenderImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendSimpleEmail(String address, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(title);
        message.setText(content);
        javaMailSender.send(message);
    }

    @Override
    public void postOrderConfirmation(Order order) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        Context context = new Context();
        context.setVariable("customerName", order.getCustomerName());
        context.setVariable("customerSurname", order.getCustomerSurname());
        context.setVariable("address", order.getAddress());

        context.setVariable("storeName", order.getStoreName());
        context.setVariable("orderId", order.getOrderId());
        context.setVariable("price", order.getTotalPrice());
        context.setVariable("accountNumber", order.getAccountNumber());

        context.setVariable("products", order.getProductList());

        String htmlContent = this.templateEngine.process("confirmation.html", context);

        try {
            messageHelper.setSubject("Computer alchemist order confirmation");
            messageHelper.setTo(order.getCustomerEmail());
            messageHelper.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void postShipmentConfirmation(Order order) {

    }

}
