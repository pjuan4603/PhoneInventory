package NYCREATES.phoneStore.service.impl;

import NYCREATES.phoneStore.data.Phone;
import NYCREATES.phoneStore.service.phoneService;
import NYCREATES.phoneStore.dao.phoneRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneServiceImplTest {

    @Autowired
    private phoneService phoneService;

    @Mock
    private MongoTemplate mongoTemplate;

    @MockBean
    private phoneRepository phoneRepository;

    @Before
    public void setUp() {
        Phone phone = new Phone();
        phone.setBrand("Apple");
        phone.setColor("Black");
        phone.setModel("iPhone X");
        phone.setPrice(999.99);
        phone.setStorage(512);

        when(phoneRepository.findByBrandOrderByPriceAsc("Apple")).thenReturn(Arrays.asList(phone));
    }

    @Test
    public void testGetPhonesByBrand() {
        String brand = "Apple";
        List<Phone> phones_repo = phoneService.getPhonesByBrand(brand, "asc");

        assertEquals(1, phones_repo.size());
        assertEquals("Apple", phones_repo.get(0).getBrand());
        assertEquals("iPhone X", phones_repo.get(0).getModel());
        assertEquals("Black", phones_repo.get(0).getColor());
    }

}
