package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.repository.OfferedRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

/**
 * @Author
 * Karol Meksuła
 * 02-06-2018
 * */

@Service
public class PriceCalculator {
    private OfferedRepository offeredRepository;
    private BigDecimal totalPrice;

    public PriceCalculator(OfferedRepository offeredRepository) {
        this.offeredRepository = offeredRepository;
    }

    public BigDecimal calculateTotalPrice(Set<OrderedProduct> orderedProducts) {
        this.totalPrice = new BigDecimal(0);

        orderedProducts.forEach(orderedProduct -> {
            long offeredId = orderedProduct.getOfferedId();
            Optional<Offered> offered = offeredRepository.findById(offeredId);
            offered.ifPresent(offeredProduct -> sumPrices(offeredProduct.getPrice()));
        });
        return totalPrice;
    }

    private void sumPrices(BigDecimal price) {
        this.totalPrice = totalPrice.add(price);
    }

}
