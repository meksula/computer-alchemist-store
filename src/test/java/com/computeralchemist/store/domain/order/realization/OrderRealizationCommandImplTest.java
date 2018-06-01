package com.computeralchemist.store.domain.order.realization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 30-05-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderRealizationCommandImplTest {

    @Value("${ca.main.url}")
    private String url;

    private final String EXPECTED = "http://localhost:8080/api/v1/";

    @Test
    public void environmentLoadProperty() {
        assertEquals(EXPECTED, url);
    }

}