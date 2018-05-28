package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.order.OrderedProductMetadata;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

public interface ProductMetadataRepository extends CrudRepository<OrderedProductMetadata, Long> {
}
