package com.computeralchemist.store.domain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

public abstract class OrderCreator {
    protected Order order;
    protected Cart cart;

    public final Order create(Cart cart) {
        this.cart = cart;

        if (!validateCart())
            throw new EmptyCartException();

        this.order = createOrder();
        setAddress();
        setClientData();
        setStoreData();
        this.order = saveOrder();
        setProductCollection();
        sendEmail();

        return this.order;
    }

    protected abstract void setAddress();

    private boolean validateCart() {
        return new CartValidator().validateCart(this.cart);
    }

    protected abstract Order createOrder();

    protected abstract void setProductCollection();

    protected abstract void setClientData();

    protected abstract void setStoreData();

    protected abstract Order saveOrder();

    protected abstract void sendEmail();

}
