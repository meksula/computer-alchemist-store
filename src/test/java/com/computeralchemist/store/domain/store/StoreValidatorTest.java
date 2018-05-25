package com.computeralchemist.store.domain.store;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

public class StoreValidatorTest {
    /**
     * Valid values
     * */
    private final long STORE_ID = 0; //because id is initialize after insert to DB
    private final long USER_ID = 3432;
    private final String USERNAME = "heaven_comp83";
    private final String STORE_NAME = "Computer's of Heavens";
    private final String STORE_EMAIL = "heaven_comp@gmail.com";
    private final String PHONE = "2432-3344-22";
    private final String ACCOUNT = "22843722334234444499330344";
    private final String DESCRIPTION = "Our computers store is here from heaven to happy all people in the world.";

    /**
     * Invalid values
     * */
    private final String INVALID_EMAIL = "computer.comp$dc.pl";


    private Store store;
    private StoreValidator storeValidator;

    @Before
    public void setUp() {
        storeValidator = new StoreValidator();

        store = new Store();
        store.setStoreId(STORE_ID);
        store.setUserId(USER_ID);
        store.setUsername(USERNAME);
        store.setStoreEmail(STORE_EMAIL);
        store.setStoreName(STORE_NAME);
        store.setPhoneNumber(PHONE);
        store.setAccountNumber(ACCOUNT);
        store.setDescription(DESCRIPTION);
    }

    @Test
    public void validatorShouldAllowTest() {
        boolean decission = storeValidator.validateStore(store);
        assertTrue(decission);
    }

    @Test
    public void validatorShouldNotAllowTest() {
        store.setStoreEmail(INVALID_EMAIL);
        boolean decission = storeValidator.validateStore(store);
        assertFalse(decission);
    }

    @Test(expected = InvalidStoreData.class)
    public void emptyEntityShouldThrowException() {
        storeValidator.validateStore(new Store());
    }

}