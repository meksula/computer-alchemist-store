package com.computeralchemist.store.domain.order

import com.computeralchemist.store.domain.order.address.Address
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

class CartValidatorTest extends Specification {
    def result
    def cart = new Cart()
    def validator = new CartValidator()

    def 'validator should return false'() {

        when: 'cart has no address'
            result = validator.validateCart(cart)

        then:
            !result
    }

    def 'validator should return true'() {

        setup:
            cart.setAddress(new Address("", "", "", "",))
            cart.setFetchCustomerDataFromCa(true)

        when:
            result = validator.validateCart(cart)

        then:
            result

    }

}
