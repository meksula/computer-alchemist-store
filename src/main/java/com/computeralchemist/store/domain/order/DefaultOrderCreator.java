package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.domain.Store;
import com.computeralchemist.store.domain.order.address.Address;
import com.computeralchemist.store.domain.order.realization.MailSender;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.OrderedProductRepository;
import com.computeralchemist.store.repository.ShippingUserDataRepository;
import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
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
    private StoreRepository storeRepository;
    private ShippingUserDataRepository shippingUserDataRepository;
    private MailSender mailSender;
    private PriceCalculator priceCalculator;

    public DefaultOrderCreator(OrderRepository orderRepository,
                               OrderedProductRepository orderedProductRepository,
                               MailSender mailSender, StoreRepository storeRepository,
                               ShippingUserDataRepository shippingUserDataRepository,
                               PriceCalculator priceCalculator) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.mailSender = mailSender;
        this.storeRepository = storeRepository;
        this.shippingUserDataRepository = shippingUserDataRepository;
        this.priceCalculator = priceCalculator;
    }

    @Override
    protected void sumOfPrices() {
        BigDecimal totalPrice = priceCalculator.calculateTotalPrice(cart.getProductInCart());
        order.setTotalPrice(String.valueOf(totalPrice));
    }

    @Override
    protected Order createOrder() {
        this.order = new Order();
        order.setOrderPlaceDate(LocalDateTime.now().toString());
        return order;
    }

    @Override
    protected void setClientData() {
        long customerId = cart.getCustomerUserId();
        String customerUsername = cart.getCustomerUsername();

        order.setCustomersId(customerId);
        order.setCustomersUsername(customerUsername);

        if (cart.isFetchCustomerDataFromDatabase()) {
            Optional<ShippingUserData> optional = shippingUserDataRepository.findById(customerId);
            optional.ifPresent(shippingUserData -> {
                order.setCustomerName(shippingUserData.getCustomerName());
                order.setCustomerSurname(shippingUserData.getCustomerSurname());
                order.setCustomerEmail(shippingUserData.getCustomerEmail());

                Address address = new Address();
                address.setCountry(shippingUserData.getCountry());
                address.setCity(shippingUserData.getCity());
                address.setZipCode(shippingUserData.getZipCode());
                address.setHouseNumber(shippingUserData.getHouseNumber());

                order.setAddress(address.toString());
            });
        } else {
            order.setCustomerName(cart.getCustomerName());
            order.setCustomerSurname(cart.getCustomerSurname());
            order.setCustomerEmail(cart.getCustomerEmail());

            order.setAddress(cart.getAddress().toString());
        }
    }

    @Override
    protected void setStoreData() {
        long storeId = cart.getStoreId();
        String storeName = cart.getStoreName();

        order.setStoreId(storeId);
        order.setStoreName(storeName);
        order.setAccountNumber(storeAccountNumber());
    }
    
    private String storeAccountNumber() {
        Optional<Store> optional = storeRepository.findById(cart.getStoreId());
        return optional
                .map(Store::getAccountNumber)
                .orElse(String.valueOf("unknown"));
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
        try {
            mailSender.postOrderConfirmation(this.order);
        } catch (MessagingException e) {
            e.getMessage();
        }
    }

}
