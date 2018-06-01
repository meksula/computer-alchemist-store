package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.Store;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@FixMethodOrder(value = MethodSorters.JVM)
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class StoreControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private StoreRepository storeRepository;

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

    private final long USER_ID = 3432;
    private final String USERNAME = "heaven_comp83";
    private final String STORE_NAME = "Computer's of Heavens";
    private final String STORE_EMAIL = "heaven_comp@gmail.com";
    private final String PHONE = "2432-3344-22";
    private final String ACCOUNT = "00000000000000000000000000";
    private final String DESCRIPTION = "Our computers store is here from heaven to happy all people in the world.";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void instantiateTest() {
        assertNotNull(context);
        assertNotNull(mockMvc);
    }

    @Test
    public void storeShouldBeCreatedTest() throws Exception {
        mockMvc.perform(post("/store")
                .accept(mediaType)
                .contentType(mediaType)
                .content(parseCorrectStoreToJson()))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private String parseCorrectStoreToJson() throws JsonProcessingException {
        Store store = new Store();
        store.setUserId(USER_ID);
        store.setUsername(USERNAME);
        store.setStoreEmail(STORE_EMAIL);
        store.setStoreName(STORE_NAME);
        store.setPhoneNumber(PHONE);
        store.setAccountNumber(ACCOUNT);
        store.setDescription(DESCRIPTION);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(store);
    }

    @Test
    public void storeShouldNotBeCreated_badPropertiesTest() throws Exception {
        mockMvc.perform(post("/store")
                .accept(mediaType)
                .contentType(mediaType)
                .content(parseInvalidStoreToJson()))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private String parseInvalidStoreToJson() throws JsonProcessingException {
        Store store = new Store();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(store);
    }

    @After
    public void cleanUp() {
        storeRepository.deleteAll();
    }



}










