package com.computeralchemist.store.domain.api.consume;

import org.springframework.http.ResponseEntity;

/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

public interface ApiConsumer<T> {
    ResponseEntity<?> getEntity(String url, Class<?> objectClass) throws ClassNotFoundException;
}
