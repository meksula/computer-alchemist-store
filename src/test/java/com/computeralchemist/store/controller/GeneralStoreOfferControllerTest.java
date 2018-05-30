package com.computeralchemist.store.controller;

import com.computeralchemist.store.domain.store.components.ComponentType;
import com.computeralchemist.store.domain.store.order.Offered;
import com.computeralchemist.store.repository.OfferedRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@FixMethodOrder(MethodSorters.JVM)
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GeneralStoreOfferControllerTest {
    private MockMvc mockMvc;
    private static OfferedRepository offeredRepositorySt;

    @Autowired
    private OfferedRepository offeredRepository;

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

    @Before
    public void setUp() {
        offeredRepositorySt = offeredRepository;

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private String componentType = "cpu";
    private String otherType = "ram";

    private final String[]COMPONENT_TYPES = {"ram", "cpu", "cpu"};
    private final String STORE_NAME = "Computer Alchemist Official";
    private final long[] PRODUCT_ID = {450, 3344, 1344};
    private final BigDecimal[] PRICES = {BigDecimal.valueOf(29.33), BigDecimal.valueOf(999.00), BigDecimal.valueOf(2924.33)};
    private final int[] IN_STOCK = {30, 5, 3};

    @Test
    public void getListOfAllOfferedProductByType() throws Exception {
        insertFakeEntities();

        mockMvc.perform(get("/store/offer/" + componentType)
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getListOfAllOfferedProductBy_otherType() throws Exception {
        mockMvc.perform(get("/store/offer/" + otherType)
                .accept(mediaType)
                .contentType(mediaType))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private void insertFakeEntities() {
        for(int i = 0; i < 3; i++) {
            Offered offered = new Offered();
            offered.setComponentType(ComponentType.valueOf(COMPONENT_TYPES[i]));
            offered.setStoreName(STORE_NAME);
            offered.setProductId(PRODUCT_ID[i]);
            offered.setPrice(PRICES[i]);
            offered.setProductsInStock(IN_STOCK[i]);

            offeredRepository.save(offered);
        }
    }

    @AfterClass
    public static void cleanUp() {
        offeredRepositorySt.deleteAll();
    }

}