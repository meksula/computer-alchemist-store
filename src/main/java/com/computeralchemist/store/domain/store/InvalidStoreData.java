package com.computeralchemist.store.domain.store;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

public class InvalidStoreData extends RuntimeException {

    @Override
    public String getMessage() {
        return "The entered data is incorrect.";
    }
}
