package com.computeralchemist.store.repository;

import com.computeralchemist.store.domain.store.order.ComponentType;
import com.computeralchemist.store.domain.store.order.Offered;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @Author
 * Karol Meksuła
 * 28-05-2018
 * */

@FixMethodOrder(MethodSorters.JVM)
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OfferedRepositoryTest {
    private static Offered offered;

    @Autowired
    private OfferedRepository repository;

    private static final String COMPONENT_TYPE = "motherboard";
    private static final String STORE_NAME = "Computer-alchemist-official";
    private static final long PRODUCT_ID = 32516;
    private static final BigDecimal PRICE = BigDecimal.valueOf(459.39);
    private static final int PIECES = 10;

    @BeforeClass
    public static void setUp() {
        offered = new Offered();
        offered.setComponentType(ComponentType.valueOf(COMPONENT_TYPE));
        offered.setStoreName(STORE_NAME);
        offered.setProductId(PRODUCT_ID);
        offered.setPrice(PRICE);
        offered.setProductsInStock(PIECES);
    }

    @Test
    public void entityShouldBeCorrectlyInsertToDatabaseAndFindById() {
        Offered offeredSaved = repository.save(offered);
        assertNotNull(offeredSaved);

        Optional<Offered> found = repository.findById(offeredSaved.getId());
        assertTrue(found.isPresent());
    }

    @Test(expected = TransactionSystemException.class)
    public void exceptionShouldThrowBecauseNullNotAllowed() {
        Offered emptyOffered = new Offered();
        repository.save(emptyOffered);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionShouldThrowBecauseThereIsNoComponentType() {
        offered.setComponentType(ComponentType.valueOf("mobo"));
        repository.save(offered);
    }

    @Test
    public void findAllByComponentTypeTest() {
        Optional<List<Offered>> offeredList = repository.findAllByComponentType(ComponentType.motherboard);
        assertEquals(1, offeredList.get().size());
    }

    @Test
    public void findAllProductsOfferedByConcreteStore() {
        Offered offered2 = new Offered();
        offered2.setComponentType(ComponentType.cpu);
        offered2.setStoreName(STORE_NAME);
        offered2.setProductId(55322);
        offered2.setPrice(BigDecimal.valueOf(230.99));
        offered2.setProductsInStock(PIECES);

        Offered offered3 = new Offered();
        offered3.setComponentType(ComponentType.gpu);
        offered3.setStoreName(STORE_NAME);
        offered3.setProductId(47432);
        offered3.setPrice(BigDecimal.valueOf(1230.99));
        offered3.setProductsInStock(100);

        repository.save(offered2);
        repository.save(offered3);

        Optional<List<Offered>> offeredList = repository.findAllByStoreName(STORE_NAME);
        assertEquals(3, offeredList.get().size());
    }

    @Test
    public void removeProductTest() {
        repository.deleteById(2L);

        Optional<Offered> found = repository.findById(2L);
        assertFalse(found.isPresent());
    }
}