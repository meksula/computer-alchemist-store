package com.computeralchemist.store.domain.order.address;



/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

public interface AddressFetcher {
    Address fetchAddress(long userId) throws ClassNotFoundException;
}
