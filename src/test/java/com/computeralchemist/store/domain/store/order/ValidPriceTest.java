package com.computeralchemist.store.domain.store.order;

import com.computeralchemist.store.domain.store.ValidPrice;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

public class ValidPriceTest {
    private final BigDecimal PRICE = BigDecimal.valueOf(359.03342);
    private final BigDecimal ROUND_RESULT = BigDecimal.valueOf(359.03);

    @Test
    public void shouldRoundToTwoPlaceAfterComma() {
        BigDecimal validated = ValidPrice.valid(PRICE);
        assertEquals(ROUND_RESULT, validated);
    }
}