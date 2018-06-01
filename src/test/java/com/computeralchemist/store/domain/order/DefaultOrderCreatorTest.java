package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefaultOrderCreatorTest {

    @Autowired
    private OrderCreator orderCreator;

    @Autowired
    private OrderRepository orderRepository;

    private static Cart cart;

    private final long customerUserId = 84923;
    private final String customerUsername = "Edek2000";

    private final long storeId = 10393;
    private final String storeName = "Computer Alchemist Official";

    private Set<OrderedProduct> productInCart = new LinkedHashSet<>();

    @Before
    public void setUp() {
        cart = new Cart();
        cart.setCustomerUserId(customerUserId);
        cart.setCustomerUsername(customerUsername);
        cart.setStoreId(storeId);
        cart.setStoreName(storeName);
        cart.setProductInCart(productInCart);

        OrderedProduct orderedProduct1 = new OrderedProduct();
        OrderedProduct orderedProduct2 = new OrderedProduct();

        productInCart.add(orderedProduct1);
        productInCart.add(orderedProduct2);
    }

    private static Order order;

    @Test
    public void a_conversionFromCartToOrderTest() {
        order = orderCreator.create(cart);
        assertNotNull(order);
        assertEquals(customerUsername, order.getCustomersUsername());
    }

    private final static Long ID = 1L;

    @Test
    public void b_convertedOrderShouldHasCorrectProperties() {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(order.getOrderId());
        Order order = optionalOrder.get();

        assertEquals(2, order.getProductList().size());
    }

}