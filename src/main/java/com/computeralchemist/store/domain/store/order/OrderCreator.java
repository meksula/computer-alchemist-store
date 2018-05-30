package com.computeralchemist.store.domain.store.order;

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

        this.order = createOrder();
        setClientData();
        setStoreData();
        this.order = saveOrder();
        setProductCollection();

        return this.order;
    }

    protected abstract Order createOrder();

    protected abstract void setProductCollection();

    protected abstract void setClientData();

    protected abstract void setStoreData();

    protected abstract Order saveOrder();

}
