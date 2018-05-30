package com.computeralchemist.store.domain.store.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@Getter
@Setter
@Entity
@Table(name = "order_placed")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @NotNull
    private long customersId;

    @NotNull
    private String customersUsername;

    @NotNull
    private long storeId;

    @NotNull
    private String storeName;



    @Transient
    private String realizeDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderedProduct> productList;
}
