package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.domain.api.consume.ApiConsumer;
import com.computeralchemist.store.domain.order.address.Address;
import com.computeralchemist.store.domain.order.address.AddressFetcher;
import com.computeralchemist.store.domain.order.realization.MailSender;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.OrderedProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Service
public class DefaultOrderCreator extends OrderCreator {
    private OrderRepository orderRepository;
    private OrderedProductRepository orderedProductRepository;
    private ApiConsumer<Address> apiConsumer;
    private MailSender mailSender;

    public DefaultOrderCreator(OrderRepository orderRepository,
                               OrderedProductRepository orderedProductRepository,
                               ApiConsumer<Address> apiConsumer, MailSender mailSender) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.apiConsumer = apiConsumer;
        this.mailSender = mailSender;
    }

    @Value("ca.user.address")
    private String url;

    @Override
    protected void setAddress() {
        if (cart.isFetchCustomerDataFromCa()) {
            order.setAddress(((AddressFetcher) userId -> {
                url = url.concat("/" + userId + "/address");
                return (Address) apiConsumer.getEntity(url, Address.class).getBody();
            }).toString());
        }

        else order.setAddress(cart.getAddress().toString());
    }

    @Override
    protected Order createOrder() {
        return this.order = new Order();
    }

    @Override
    protected void setClientData() {
        long customerId = cart.getCustomerUserId();
        String customerUsername = cart.getCustomerUsername();

        order.setCustomersId(customerId);
        order.setCustomersUsername(customerUsername);
    }

    @Override
    protected void setStoreData() {
        long storeId = cart.getStoreId();
        String storeName = cart.getStoreName();

        order.setStoreId(storeId);
        order.setStoreName(storeName);
    }

    @Override
    protected Order saveOrder() {
        return orderRepository.save(super.order);
    }

    @Override
    protected void setProductCollection() {
        Set<OrderedProduct> orderedProducts = cart.getProductInCart();

        if (orderedProducts == null || orderedProducts.size() == 0)
            throw new EmptyCartException();

        orderedProducts.forEach(product -> {
            product.setOrder(super.order);
            orderedProductRepository.save(product);
        });

        order.setProductList(orderedProducts);
    }

    @Override
    protected void sendEmail() {
        mailSender.postOrderConfirmation(this.order);
    }

}
