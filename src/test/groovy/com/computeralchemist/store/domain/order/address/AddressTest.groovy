package com.computeralchemist.store.domain.order.address

import spock.lang.Specification

/**
 * @Author
 * Karol Meksuła
 * 01-06-2018
 * */

class AddressTest extends Specification {
    private final CITY = "Lublin"
    private final COUNTRY = "Poland"
    private final ZIP_CODE = "23-334"
    private final HOUSE_NUMBER = 193

    def 'toString() test'() {
        def address = new Address(CITY, COUNTRY, ZIP_CODE, HOUSE_NUMBER as String)

        when:
        def expected = address.toString()

        then:
        expected.equals("country: Lublin; city: Poland; zip-code: 23-334; house number: 193")

    }
}
