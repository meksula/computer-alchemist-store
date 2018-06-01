package com.computeralchemist.store.domain.api.consume;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

@Service
public class RestApiConsumer<T> implements ApiConsumer<T> {
    private RestTemplate restTemplate;

    public RestApiConsumer() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<?> getEntity(String url, Class<?> objectClass) {
        return restTemplate.getForEntity(url, objectClass);
    }
}
