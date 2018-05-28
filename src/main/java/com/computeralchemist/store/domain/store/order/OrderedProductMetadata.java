package com.computeralchemist.store.domain.store.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
//@Table(name = "order_metadata")
public class OrderedProductMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long metadataId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String componentType;
    private long productId;
}
