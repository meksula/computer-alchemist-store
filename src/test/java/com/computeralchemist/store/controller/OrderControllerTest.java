package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.store.Store;
import com.computeralchemist.store.domain.store.order.Cart;
import com.computeralchemist.store.domain.store.order.Order;
import com.computeralchemist.store.repository.OrderRepository;
import com.computeralchemist.store.repository.StoreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author
 * Karol Meksuła
 * 29-05-2018
 * */

@FixMethodOrder(value = MethodSorters.JVM)
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class OrderControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private MediaType mediaType = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    public void setMessageConverter(HttpMessageConverter<?>[] convs) {
        mappingJackson2HttpMessageConverter = Arrays.stream(convs)
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
    }

    private final long STORE_ID = 5069;
    private static final String STORE_NAME = "Computer from Heaven";
    private final long CUSTOMER_ID = 38294;
    private final String CUSTOMER_USERNAME = "niko_kopernik1492";

    private final long USER_ID = 3432;
    private final String USERNAME = "heaven_comp83";
    private final String STORE_EMAIL = "heaven_comp@gmail.com";
    private final String PHONE = "2432-3344-22";
    private final String ACCOUNT = "22843722334234444499330344";
    private final String DESCRIPTION = "Our computers store is here from heaven to happy all people in the world.";

    private Store store;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        store = new Store();
        store.setUserId(USER_ID);
        store.setUsername(USERNAME);
        store.setStoreEmail(STORE_EMAIL);
        store.setStoreName(STORE_NAME);
        store.setPhoneNumber(PHONE);
        store.setAccountNumber(ACCOUNT);
        store.setDescription(DESCRIPTION);
    }

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void makeOrderTest() throws Exception {
        storeRepository.save(store);

        mockMvc.perform(post("/store/order/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType)
                .content(orderJson()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private String orderJson() throws JsonProcessingException {
        Cart cart = new Cart();
        cart.setCustomerUserId(CUSTOMER_ID);
        cart.setCustomerUsername(CUSTOMER_USERNAME);
        cart.setStoreId(STORE_ID);
        cart.setStoreName(STORE_NAME);
        cart.setProductInCart(new HashSet<>());

        return new ObjectMapper().writeValueAsString(cart);
    }

    @Test
    public void showListOfAllOrdersOfMyStore() throws Exception {
        saveOrder();

        mockMvc.perform(get("/store/order/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private void saveOrder() {
        Order order = new Order();
        order.setCustomersId(CUSTOMER_ID);
        order.setCustomersUsername(CUSTOMER_USERNAME);
        order.setStoreId(STORE_ID);
        order.setStoreName(STORE_NAME);
        order.setProductList(new HashSet<>());

        orderRepository.save(order);
    }

    @After
    public void cleanUp() {
        orderRepository.deleteAll();
    }

}