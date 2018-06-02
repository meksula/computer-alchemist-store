package com.computeralchemist.store.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author
 * Karol Meksuła
 * 02-06-2018
 * */

@Getter
@Setter
@Entity
@Table(name = "shipping_user_data")
public class ShippingUserData {

    @Id
    private long userId;

    private String customerName;
    private String customerSurname;

    private String customerEmail;

    private String country;
    private String city;
    private String zipCode;
    private String houseNumber;
}
