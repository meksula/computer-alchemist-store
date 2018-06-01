package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.domain.components.ComponentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

public class OfferedTest {
    private static Offered offered;

    private static final String COMPONENT_TYPE = "motherboard";
    private static final String STORE_NAME = "Computer-alchemist-official";
    private static final long PRODUCT_ID = 32516;
    private static final BigDecimal PRICE = BigDecimal.valueOf(459.396422);
    private static final int PIECES = 10;

    private static final BigDecimal EXPECTED = BigDecimal.valueOf(459.39);

    @BeforeClass
    public static void setUp() {
        offered = new Offered();
        offered.setComponentType(ComponentType.valueOf(COMPONENT_TYPE));
        offered.setStoreName(STORE_NAME);
        offered.setProductId(PRODUCT_ID);
        offered.setPrice(PRICE);
        offered.setProductsInStock(PIECES);
    }

    @Test
    public void priceShouldHas2PlacesAfterComa() {
        assertEquals(EXPECTED, offered.getPrice());
    }

}