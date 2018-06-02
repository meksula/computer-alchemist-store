package com.computeralchemist.store.domain.order;

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

        order = createOrder();
        setClientData();
        setStoreData();
        sumOfPrices();
        order = saveOrder();
        setProductCollection();
        sendEmail();

        return this.order;
    }

    protected abstract void sumOfPrices();

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
