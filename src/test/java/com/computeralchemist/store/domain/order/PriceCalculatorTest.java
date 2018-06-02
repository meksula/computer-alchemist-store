package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.domain.components.ComponentType;
import com.computeralchemist.store.repository.OfferedRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 02-06-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
public class PriceCalculatorTest {

    @Autowired
    private OfferedRepository offeredRepository;

    @Autowired
    private PriceCalculator priceCalculator;

    @Test
    public void totalPriceTest() {
        Offered offered = new Offered();
        offered.setComponentType(ComponentType.motherboard);
        offered.setPrice(BigDecimal.valueOf(200.00));
        offered.setStoreName("blabla");
        offered.setProductId(22222);
        offeredRepository.save(offered);

        Set<OrderedProduct> orderedProducts = new HashSet<>();
        OrderedProduct orderedProduct = new OrderedProduct();
        OrderedProduct orderedProduct2 = new OrderedProduct();
        orderedProduct.setOfferedId(offered.getId());
        orderedProduct2.setOfferedId(offered.getId());
        orderedProducts.add(orderedProduct);
        orderedProducts.add(orderedProduct2);

        BigDecimal totalPrice = priceCalculator.calculateTotalPrice(orderedProducts);
        assertThat(BigDecimal.valueOf(400.00),  Matchers.comparesEqualTo(totalPrice));
    }

    @After
    public void cleanUp() {
        offeredRepository.deleteAll();
    }

}