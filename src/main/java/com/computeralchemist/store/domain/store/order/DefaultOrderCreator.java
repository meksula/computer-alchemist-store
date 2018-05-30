package com.computeralchemist.store.domain.store.order;

import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.OrderedProductRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Service
public class DefaultOrderCreator extends OrderCreator {
    private OrderRepository orderRepository;
    private OrderedProductRepository orderedProductRepository;

    public DefaultOrderCreator(OrderRepository orderRepository,
                               OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    protected Order createOrder() {
        return this.order = new Order();
    }

    @Override
    protected void setClientData() {
        long customerId = cart.getCustomerUserId();
        String customerUsername = cart.getCustomerUsername();

        order.setCustomersId(customerId);
        order.setCustomersUsername(customerUsername);
    }

    @Override
    protected void setStoreData() {
        long storeId = cart.getStoreId();
        String storeName = cart.getStoreName();

        order.setStoreId(storeId);
        order.setStoreName(storeName);
    }

    @Override
    protected Order saveOrder() {
        return orderRepository.save(super.order);
    }

    @Override
    protected void setProductCollection() {
        Set<OrderedProduct> orderedProducts = cart.getProductInCart();

        if (orderedProducts == null)
            throw new EmptyCartException();

        orderedProducts.forEach(product -> {
            product.setOrder(super.order);
            orderedProductRepository.save(product);
        });

        order.setProductList(orderedProducts);
    }

}
