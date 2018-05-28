package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.order.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

public interface OrderRepository extends CrudRepository<Order, Long> {
}
