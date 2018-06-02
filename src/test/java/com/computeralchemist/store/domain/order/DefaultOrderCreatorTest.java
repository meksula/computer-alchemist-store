package com.computeralchemist.store.domain.order;

import com.computeralchemist.store.domain.Store;
import com.computeralchemist.store.domain.order.address.Address;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.ShippingUserDataRepository;
import com.computeralchemist.store.repository.StoreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefaultOrderCreatorTest {

    @Autowired
    private OrderCreator orderCreator;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShippingUserDataRepository shippingUserDataRepository;

    @Autowired
    private StoreRepository storeRepository;

    private static OrderRepository orderRepositoryST;
    private static ShippingUserDataRepository shippingUserDataRepositoryST;
    private static StoreRepository storeRepositoryST;

    private static Cart cart;
    private static Store store;

    private static long STORE_ID;
    private static final long USER_ID = 3432;
    private static final String USERNAME = "heaven_comp83";
    private static final String STORE_NAME = "Computer's of Heavens";
    private static final String STORE_EMAIL = "heaven_comp@gmail.com";
    private static final String PHONE = "2432-3344-22";
    private static final String ACCOUNT = "22843722334234444499330344";
    private static final String DESCRIPTION = "Our computers store is here from heaven to happy all people in the world.";

    private final long CUSTOMER_USER_ID = 84923;
    private final String CUSTOMER_USERNAME = "Edek2000";
    private final String CUSTOMER_NAME = "Edward";
    private final String CUSTOMER_SURNAME = "Kowalski";
    private final String CUSTOMER_EMAIL = "karol.meksula@onet.pl";

    private final String COUNTRY = "Poland";
    private final String CITY = "Warszawa";
    private final String ZIP = "444-34";
    private final String HOUSE = "459/22";

    public void repositoryAsStatic() {
        storeRepositoryST = storeRepository;
    }

    @Before
    public void setUp() {
        repositoryAsStatic();
        saveStore();
    }

    private Set<OrderedProduct> getSetOfProduct() {
        Set<OrderedProduct> productInCart = new LinkedHashSet<>();

        OrderedProduct orderedProduct1 = new OrderedProduct();
        OrderedProduct orderedProduct2 = new OrderedProduct();

        productInCart.add(orderedProduct1);
        productInCart.add(orderedProduct2);

        return productInCart;
    }

    private static void saveStore() {
        store = new Store();
        store.setUserId(USER_ID);
        store.setUsername(USERNAME);
        store.setStoreEmail(STORE_EMAIL);
        store.setStoreName(STORE_NAME);
        store.setPhoneNumber(PHONE);
        store.setAccountNumber(ACCOUNT);
        store.setDescription(DESCRIPTION);

        Store storeupdated = storeRepositoryST.save(store);
        STORE_ID = storeupdated.getStoreId();
    }

    private Address prepareAddress() {
        Address address = new Address();
        address.setCountry(COUNTRY);
        address.setCity(CITY);
        address.setZipCode(ZIP);
        address.setHouseNumber(HOUSE);
        return address;
    }

    private void prepareCartDataByHand() {
        cart = new Cart();
        cart.setFetchCustomerDataFromDatabase(false);
        cart.setCustomerUserId(CUSTOMER_USER_ID);
        cart.setCustomerUsername(CUSTOMER_USERNAME);
        cart.setCustomerName(CUSTOMER_NAME);
        cart.setCustomerSurname(CUSTOMER_SURNAME);
        cart.setCustomerEmail(CUSTOMER_EMAIL);

        cart.setStoreId(STORE_ID);
        cart.setStoreName(STORE_NAME);

        cart.setAddress(prepareAddress());

        cart.setProductInCart(getSetOfProduct());
    }

    private void shouldBeCorrectlyConverted(Order order) {
        assertEquals(ACCOUNT, order.getAccountNumber());
        assertEquals(CUSTOMER_EMAIL, order.getCustomerEmail());
        assertEquals(CUSTOMER_NAME, order.getCustomerName());
        assertEquals(CUSTOMER_SURNAME, order.getCustomerSurname());
        assertEquals(CUSTOMER_USER_ID, order.getCustomersId());
        assertEquals(prepareAddress().toString(), order.getAddress());
    }

    @Test
    public void a_firstScenario_conversionFromCartToOrder_userDataInputtedByHand() throws JsonProcessingException {
        prepareCartDataByHand();

        Order order = orderCreator.create(cart);
        assertNotNull(order);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);
        log.info(json);

        shouldBeCorrectlyConverted(order);
    }

    private void prepareCartDataFetchFromDatabase() {
        cart = new Cart();
        cart.setFetchCustomerDataFromDatabase(true);
        cart.setCustomerUserId(CUSTOMER_USER_ID);
        cart.setCustomerUsername(CUSTOMER_USERNAME);

        cart.setStoreId(STORE_ID);
        cart.setStoreName(STORE_NAME);

        cart.setProductInCart(getSetOfProduct());
    }

    @Test
    public void b_secondScenario_conversionFromCartToOrder_userDataFromDatabase() throws JsonProcessingException {
        shippingUserDataRepository.save(prepareShippingUserData());
        prepareCartDataFetchFromDatabase();

        Order order = orderCreator.create(cart);
        assertNotNull(order);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);
        log.info(json);

        shouldBeCorrectlyConverted(order);
    }

    private ShippingUserData prepareShippingUserData() {
        ShippingUserData shippingUserData = new ShippingUserData();
        shippingUserData.setUserId(CUSTOMER_USER_ID);
        shippingUserData.setCustomerName(CUSTOMER_NAME);
        shippingUserData.setCustomerSurname(CUSTOMER_SURNAME);
        shippingUserData.setCustomerEmail(CUSTOMER_EMAIL);
        shippingUserData.setCountry(COUNTRY);
        shippingUserData.setCity(CITY);
        shippingUserData.setZipCode(ZIP);
        shippingUserData.setHouseNumber(HOUSE);
        return shippingUserData;
    }

    @After
    public void cleanUp() {
        orderRepository.deleteAll();
        shippingUserDataRepository.deleteAll();
    }

    @AfterClass
    public static void cleanClass() {
        storeRepositoryST.deleteAll();
    }

}