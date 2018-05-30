package com.computeralchemist.store.domain.store.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "order_placed_item")
public class OrderedProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private long userIdPlacingOrder;
    private String usernamePlacingOrder;

    private long offeredId;
}
