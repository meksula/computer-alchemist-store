package com.computeralchemist.store.domain.order.address;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Address implements Serializable {
    private String country;
    private String city;
    private String zipCode;
    private String houseNumber;

    @Override
    public String toString() {
        return "country: " + country + "; " +
                "city: " + city + "; " +
                "zip-code: " + zipCode + "; " +
                "house number: " + houseNumber;
    }
}

