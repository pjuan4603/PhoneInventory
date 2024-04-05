package NYCREATES.phoneStore.service;

import NYCREATES.phoneStore.data.Phone;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface phoneService {

    Phone create(Phone phone);
    List<Phone> getPhonesByBrand(String brand, String indicator);

    List<Phone> getPhonesByModel(String model);

    List<Phone> getPhonesByColor(String color);

    List<Phone> getPhones(String brand,
                          String model,
                          Integer storage,
                          String color,
                          Integer minStorage,
                          Integer maxStorage,
                          String storageComp,
                          Double price,
                          Double minPrice,
                          Double maxPrice,
                          String priceComp);
    Phone update(Phone phone);

}
