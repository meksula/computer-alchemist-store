package com.computeralchemist.store.repository

import com.computeralchemist.store.domain.order.ShippingUserData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 02-06-2018
 * */

@SpringBootTest
class ShippingUserDataRepositoryTest extends Specification {

    @Autowired
    private ShippingUserDataRepository repository

    def 'instantiateTest'() {
        expect:
        repository != null
    }

    private long userId = 193304
    private String customerName = "John"
    private String customerSurname = "Doe"

    private String customerEmail = "johny_doe_79@gmail.com"

    private String country = "England"
    private String city = "Cambridge"
    private String zipCode = "344-33"
    private String houseNumber = "90a"

    private ShippingUserData 'prepareEntity'() {
        def shippingUserData = new ShippingUserData()
        shippingUserData.setUserId(userId)
        shippingUserData.setCustomerName(customerName)
        shippingUserData.setCustomerSurname(customerSurname)
        shippingUserData.setCustomerEmail(customerEmail)
        shippingUserData.setCountry(country)
        shippingUserData.setCity(city)
        shippingUserData.setZipCode(zipCode)
        shippingUserData.setHouseNumber(houseNumber)
        return shippingUserData
    }

    def 'repository test'() {
        given:
        def savedShippingUserData

        and: 'prepare entity'
        def entity = prepareEntity()

        when: 'save to database'
        savedShippingUserData = repository.save(entity)

        then: 'saved entity is not null'
        savedShippingUserData != null

        cleanup:
        repository.deleteAll()
    }

    def 'find repository test'() {
        given:
        def found

        when:
        repository.save(prepareEntity())
        found = repository.findById(userId)

        then:
        found.isPresent()

        cleanup:
        repository.deleteAll()
    }

}
