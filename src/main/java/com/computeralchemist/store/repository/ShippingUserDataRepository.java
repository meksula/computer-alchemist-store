package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.order.ShippingUserData;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author
 * Karol Meksuła
 * 02-06-2018
 * */

public interface ShippingUserDataRepository extends CrudRepository<ShippingUserData, Long> {
}
