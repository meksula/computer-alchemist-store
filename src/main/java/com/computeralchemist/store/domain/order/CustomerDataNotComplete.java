package com.computeralchemist.store.domain.order;

/**
 * @Author
 * Karol Meksuła
 * 02-06-2018
 * */

public class CustomerDataNotComplete extends RuntimeException {

    @Override
    public String getMessage() {
        return "You have to complete your address and personal data.";
    }
}
