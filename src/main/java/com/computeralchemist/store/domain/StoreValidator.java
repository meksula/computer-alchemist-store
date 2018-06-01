package com.computeralchemist.store.domain;

import com.computeralchemist.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@Service
public class StoreValidator {
    private StoreRepository storeRepository;
    private Store store;
    private boolean[] results = new boolean[8];

    public StoreValidator(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public boolean validateStore(Store store) {
        this.store = store;

        try {
            results[0] = checkStoreId();
            results[1] = checkUserId();
            results[2] = checkUsername();
            results[3] = checkStoreName();
            results[4] = checkStoreEmail();
            results[5] = checkPhoneNumber();
            results[6] = checkAccountNumber();
            results[7] = checkDescription();
        } catch (NullPointerException npe) {
            throw new InvalidStoreData();
        }

        return checkTable();
    }

    public boolean isStoreExist(String storeName) {
        return storeRepository.findByStoreName(storeName).isPresent();
    }

    private boolean checkStoreId() {
        return store.getStoreId() == 0;
    }

    private boolean checkUserId() {
        return store.getUserId() > 0;
    }

    private boolean checkUsername() {
        return !store.getUsername().isEmpty();
    }

    private boolean checkStoreName() {
        return !store.getStoreName().isEmpty()
                && store.getStoreName().length() > 5
                && store.getStoreName().length() < 25;
    }

    private boolean checkStoreEmail() {
        return store.getStoreEmail().contains("@")
                && store.getStoreEmail().contains(".");
    }

    private boolean checkPhoneNumber() {
        return store.getPhoneNumber().length() >= 9;
    }

    private boolean checkAccountNumber() {
        return store.getAccountNumber().length() == 26;
    }

    private boolean checkDescription() {
        return !store.getDescription().isEmpty();
    }

    private boolean checkTable() {
        for (boolean b : results) {
            if (!b)
                return false;
        }
        return true;
    }

}
