package com.computeralchemist.store.domain.order;

/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

public class CartValidator {

    public boolean validateCart(Cart cart) {
        if (cart.isFetchCustomerDataFromCa() && cart.getAddress() == null)
            return true;

        else if (cart.getAddress() != null)
            return true;

        else return false;
    }

}
