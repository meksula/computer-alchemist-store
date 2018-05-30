package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.components.ComponentType;
import com.computeralchemist.store.domain.store.order.Offered;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

public interface OfferedRepository extends CrudRepository<Offered, Long> {

    Optional<List<Offered>> findAllByComponentType(ComponentType componentType);

    Optional<List<Offered>> findAllByStoreName(String storeName);
}
