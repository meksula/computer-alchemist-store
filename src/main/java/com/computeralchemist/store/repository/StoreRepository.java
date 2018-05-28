package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

public interface StoreRepository extends CrudRepository<Store, Long> {
    Optional<Store> findByStoreName(String storeName);
}
