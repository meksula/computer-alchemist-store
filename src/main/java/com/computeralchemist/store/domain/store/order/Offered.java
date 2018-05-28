package com.computeralchemist.store.domain.store.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@Getter
@Setter
@Entity
@Table(name = "offered")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Offered {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private ComponentType componentType;

    @NotNull
    private String storeName;

    @NotNull
    private long productId;

    @NotNull
    private BigDecimal price;

    private int productsInStock;

}
