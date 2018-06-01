package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.order.Order;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@Transactional
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);

    Optional<List<Order>> findAllByStoreName(String storeName);

    void deleteAllByOrderId(long orderId);
}
