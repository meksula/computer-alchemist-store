package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.order.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
}
