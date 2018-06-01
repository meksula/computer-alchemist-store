package com.computeralchemist.store.domain.order;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

public class EmptyCartException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Cart is empty. Fill Cart and next make an order.";
    }
}
