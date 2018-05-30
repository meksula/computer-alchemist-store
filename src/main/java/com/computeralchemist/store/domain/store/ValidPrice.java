package com.computeralchemist.store.domain.store;

import java.math.BigDecimal;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

public class ValidPrice {

    public static BigDecimal valid(BigDecimal price) {
        return price.setScale(2, BigDecimal.ROUND_DOWN);
    }

}
