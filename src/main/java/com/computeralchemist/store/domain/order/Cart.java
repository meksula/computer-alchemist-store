package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.domain.order.address.Address;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Cart implements Serializable {
    private long customerUserId;
    private String customerUsername;

    /**
     * @param fetchCustomerDataFromCa determines that customer allowed to get data from the Computer Alchemist database.
     * if is true - get data from Computer Alchemist database.
     * if is false - customer has to type data by hand.
     * */
    private boolean fetchCustomerDataFromCa;
    private Address address;

    private long storeId;
    private String storeName;

    private Set<OrderedProduct> productInCart;

}
