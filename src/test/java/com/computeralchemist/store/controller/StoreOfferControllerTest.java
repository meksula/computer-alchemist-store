package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.store.order.ComponentType;
import com.computeralchemist.store.domain.store.order.Offered;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class StoreOfferControllerTest {
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

    private static final String COMPONENT_TYPE = "motherboard";
    private static final String STORE_NAME = "Computer-alchemist-official";
    private static final long PRODUCT_ID = 32516;
    private static final BigDecimal PRICE = BigDecimal.valueOf(459.39);
    private static final int PIECES = 10;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void offeredProductShouldBeAdded() throws Exception {
        mockMvc.perform(post("/store/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType).content(provideJson()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void offeredProductShouldNotBeAddedBecauseDataIsIncorrect() throws Exception {
        mockMvc.perform(post("/store/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType).content(provideInvalidJson()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test(expected = IllegalArgumentException.class)
    public void offeredProductShouldNotBeAddedBecauseTypeNotExist() throws Exception {
        mockMvc.perform(post("/store/" + STORE_NAME)
                .accept(mediaType)
                .contentType(mediaType).content(provideIncorrectEnumTypeJson()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private final Long ID = 2L;
    private final Long INVALID_ID = 324234L;

    @Test
    public void offeredProductShouldBeRemovedCorrectly() throws Exception {
        mockMvc.perform(delete("/store/" + STORE_NAME + "/" + ID)
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOneOffertFromStore() throws Exception {
        mockMvc.perform(get("/store/" + STORE_NAME + "/" + ID)
                .contentType(mediaType)
                .accept(mediaType))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void offeredProductNotExist() throws Exception {
        mockMvc.perform(delete("/store/" + STORE_NAME + "/" + INVALID_ID)
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getListOfOfferedStoreProducts() throws Exception {
        mockMvc.perform(get("/store/" + STORE_NAME + "/offered")
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

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
}