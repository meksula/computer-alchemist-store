package com.computeralchemist.store.domain.store.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Getter
@Setter
public class Cart {
    private long customerUserId;
    private String customerUsername;

    private long storeId;
    private String storeName;

    private Set<OrderedProduct> productInCart;

}
