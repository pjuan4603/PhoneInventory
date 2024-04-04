package NYCREATES.phoneStore.dao;

import NYCREATES.phoneStore.data.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface phoneRepository extends MongoRepository<Phone, String> {

    List<Phone> findByBrandOrderByPriceAsc(String brand);
    List<Phone> findByBrandOrderByPriceDesc(String brand);

    List<Phone> findByModelRegex(String modelRegex);

    List<Phone> findByColor(String color);
}
