package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.store.Store;
import com.computeralchemist.store.domain.store.components.ComponentType;
import com.computeralchemist.store.domain.store.order.Offered;
import com.computeralchemist.store.repository.OfferedRepository;
import com.computeralchemist.store.repository.StoreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
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

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class StoreOfferControllerTest {
    private MockMvc mockMvc;
    private static OfferedRepository offeredRepositorySt;

    @Autowired
    private OfferedRepository offeredRepository;

    @Autowired
    private StoreRepository storeRepository;

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

    private static final String COMPONENT_TYPE = "motherboard";
    private static final String STORE_NAME = "Computer-alchemist-official";
    private static final long PRODUCT_ID = 32516;
    private static final BigDecimal PRICE = BigDecimal.valueOf(459.39);
    private static final int PIECES = 10;

    private static Store store;
    private final long USER_ID = 3432;
    private final String USERNAME = "heaven_comp83";
    private final String STORE_EMAIL = "heaven_comp@gmail.com";
    private final String PHONE = "2432-3344-22";
    private final String ACCOUNT = "22843722334234444499330344";
    private final String DESCRIPTION = "Our computers store is here from heaven to happy all people in the world.";

    @Before
    public void setUp() {
        offeredRepositorySt = offeredRepository;

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private void createStore() {
        store = new Store();
        store.setUserId(USER_ID);
        store.setUsername(USERNAME);
        store.setStoreEmail(STORE_EMAIL);
        store.setStoreName(STORE_NAME);
        store.setPhoneNumber(PHONE);
        store.setAccountNumber(ACCOUNT);
        store.setDescription(DESCRIPTION);

        storeRepository.save(store);
    }

    @Test
    public void a_offeredProductShouldBeAdded() throws Exception {
        createStore();

        mockMvc.perform(post("/store/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType)
                .content(provideJson()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /*@Ignore
    @Test
    public void b_getOneOffertFromStore() throws Exception {
        mockMvc.perform(get("/store/" + STORE_NAME + "/" + store.getStoreId())
                .contentType(mediaType)
                .accept(mediaType))
                .andDo(print())
                .andExpect(status().isOk());
    }*/

    @Test
    public void c_offeredProductShouldNotBeAddedBecauseDataIsIncorrect() throws Exception {
        mockMvc.perform(post("/store/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType).content(provideInvalidJson()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test(expected = IllegalArgumentException.class)
    public void d_offeredProductShouldNotBeAddedBecauseTypeNotExist() throws Exception {
        mockMvc.perform(post("/store/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType).content(provideIncorrectEnumTypeJson()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private final Long INVALID_ID = 324234L;

    @Test
    public void e_offeredProductShouldBeRemovedCorrectly() throws Exception {
        mockMvc.perform(delete("/store/" + STORE_NAME + "/" + store.getStoreId())
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void f_offeredProductNotExist() throws Exception {
        mockMvc.perform(delete("/store/" + STORE_NAME + "/" + INVALID_ID)
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /*@Test
    public void g_getListOfOfferedStoreProducts() throws Exception {
        mockMvc.perform(get("/store/" + STORE_NAME + "/offered")
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }*/

    private String provideJson() throws JsonProcessingException {
        Offered offered = new Offered();
        offered.setComponentType(ComponentType.valueOf(COMPONENT_TYPE));
        offered.setStoreName(STORE_NAME);
        offered.setProductId(PRODUCT_ID);
        offered.setPrice(PRICE);
        offered.setProductsInStock(PIECES);

        return new ObjectMapper().writeValueAsString(offered);
    }

    /*
    * Provides not complete data.
    * */
    private String provideInvalidJson() throws JsonProcessingException {
        Offered offered = new Offered();

        offered.setStoreName(STORE_NAME);
        offered.setProductId(PRODUCT_ID);
        offered.setPrice(PRICE);
        offered.setProductsInStock(PIECES);

        return new ObjectMapper().writeValueAsString(offered);
    }

    /*
     * Provides not correct ComponentType - enum not exist.
     * */
    private String provideIncorrectEnumTypeJson() throws JsonProcessingException {
        Offered offered = new Offered();
        offered.setComponentType(ComponentType.valueOf("keyboard"));
        offered.setStoreName(STORE_NAME);
        offered.setProductId(PRODUCT_ID);
        offered.setPrice(PRICE);
        offered.setProductsInStock(PIECES);

        return new ObjectMapper().writeValueAsString(offered);
    }

    @AfterClass
    public static void cleanUp() {
        offeredRepositorySt.deleteAll();
    }

}