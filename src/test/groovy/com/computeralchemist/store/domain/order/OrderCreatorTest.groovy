package com.computeralchemist.store.domain.order

import com.computeralchemist.store.domain.order.address.Address
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@SpringBootTest
class OrderCreatorTest extends Specification {

    @Autowired
    private OrderCreator orderCreator

    private def order
    private def cart = new Cart()

    def 'injection test'() {
        expect:
            orderCreator != null
    }

    def 'cart should be NOT converted to order because parameters are not complete'() {

        when:
            orderCreator.create(this.cart)

        then:
            final EmptyCartException emptyCartException = thrown()
    }

    private final CUSTOMER_ID = 4334
    private final CUSTOMER_USERNAME = "eddie200"

    private final STORE_ID = 359
    private final STORE_NAME = "Computer alchemist official"
    private final PRODUCTS = [new OrderedProduct(), new OrderedProduct()]

    def 'cart should be correctly converted to order - address will be fetch from database'() {

        setup:
        cart.setFetchCustomerDataFromCa(true)
        cart.setCustomerUserId(CUSTOMER_ID)
        cart.setCustomerUsername(CUSTOMER_USERNAME)
        cart.setStoreId(STORE_ID)
        cart.setStoreName(STORE_NAME)
        cart.setProductInCart(PRODUCTS as Set<OrderedProduct>)

        when:
        order = orderCreator.create(cart)

        then:
        order != null

    }

    private final COUNTRY = "Poland"
    private final CITY = "Lublin"

    def 'cart should be correctly converted to order - address will be typed'() {
        setup:
        cart.setFetchCustomerDataFromCa(false)
        cart.setCustomerUserId(CUSTOMER_ID)
        cart.setCustomerUsername(CUSTOMER_USERNAME)
        cart.setStoreId(STORE_ID)
        cart.setStoreName(STORE_NAME)
        cart.setProductInCart(PRODUCTS as Set<OrderedProduct>)

        def address = new Address(COUNTRY, CITY, "", "")
        cart.setAddress(address)

        when:
        order = orderCreator.create(cart)

        then:
        order != null
    }

}
