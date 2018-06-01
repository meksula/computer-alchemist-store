package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.Store;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 25-05-2018
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(value = MethodSorters.JVM)
public class StoreRepositoryTest {
    private static StoreRepository storeRepositorySt;

    @Autowired
    private StoreRepository storeRepository;

    private final long USER_ID = 3432;
    private final String USERNAME = "heaven_comp83";
    private final String STORE_NAME = "Computer's of Heavens";
    private final String STORE_EMAIL = "heaven_comp@gmail.com";
    private final String PHONE = "2432-3344-22";
    private final String ACCOUNT = "22843722334234444499330344";
    private final String DESCRIPTION = "Our computers store is here from heaven to happy all people in the world.";

    private static Store store;

    @Before
    public void setUp() {
        storeRepositorySt = storeRepository;

        store = new Store();
        store.setUserId(USER_ID);
        store.setUsername(USERNAME);
        store.setStoreEmail(STORE_EMAIL);
        store.setStoreName(STORE_NAME);
        store.setPhoneNumber(PHONE);
        store.setAccountNumber(ACCOUNT);
        store.setDescription(DESCRIPTION);
    }

    @Test
    public void saveEntityTest() {
        Store justSaved = storeRepository.save(store);
        assertNotNull(justSaved);
        assertEquals(USERNAME, justSaved.getUsername());
    }

    @Test
    public void findEntityByIdTest() {
        storeRepository.save(store);

        Optional<Store> found = storeRepository.findById(store.getStoreId());
        Store storeFound = found.get();

        assertEquals(USER_ID, storeFound.getUserId());
        assertEquals(USERNAME, storeFound.getUsername());
        assertEquals(STORE_NAME, storeFound.getStoreName());
        assertEquals(STORE_EMAIL, storeFound.getStoreEmail());
        assertEquals(PHONE, storeFound.getPhoneNumber());
        assertEquals(ACCOUNT, storeFound.getAccountNumber());
        assertEquals(DESCRIPTION, storeFound.getDescription());
    }

    @Test
    public void findByStoreNameTest() {
        storeRepository.save(store);

        Optional<Store> found = storeRepository.findByStoreName(STORE_NAME);
        assertTrue(found.isPresent());
    }

    @After
    public void cleanAfterEachTest() {
        storeRepository.deleteAll();
    }

    @AfterClass
    public static void cleanUp() {
        storeRepositorySt.deleteAll();
    }

}