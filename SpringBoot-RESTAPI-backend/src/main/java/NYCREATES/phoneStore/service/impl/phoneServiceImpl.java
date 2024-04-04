package NYCREATES.phoneStore.service.impl;

import NYCREATES.phoneStore.dao.phoneRepository;
import NYCREATES.phoneStore.data.Phone;
import NYCREATES.phoneStore.service.phoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class phoneServiceImpl implements phoneService {

    private static final Logger LOG = LoggerFactory.getLogger(phoneServiceImpl.class);

    @Autowired
    private phoneRepository phoneRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Phone create(Phone phone) {
        LOG.debug("Creating phone [{}]", phone);

        phone.setId(UUID.randomUUID().toString());
        phoneRepository.insert(phone);

        return phone;
    }

    @Override
    public List<Phone> getPhonesByBrand(String brand, String indicator) {
        LOG.debug("Retrieving phone with brand [{}]", brand);

        List<Phone> phoneList = new LinkedList<>();
        if (indicator.equalsIgnoreCase("desc")) {
            phoneList = phoneRepository.findByBrandOrderByPriceDesc(brand);
        } else {
            phoneList = phoneRepository.findByBrandOrderByPriceAsc(brand);
        }

        if (phoneList == null) {
            LOG.debug("No matches found for brand: " + brand);
        }

        return phoneList;
    }

    @Override
    public List<Phone> getPhonesByModel(String model) {
        LOG.debug("Retrieving phone with model [{}]", model);

        String regex = ".*" + model + ".*";

        List<Phone> phoneList = phoneRepository.findByModelRegex(regex);

        if (phoneList == null) {
            LOG.debug("Mo matches found for model: " + model);
        }

        return phoneList;
    }

    @Override
    public List<Phone> getPhonesByColor(String color) {

        LOG.debug("Retrieving phone with color [{}]", color);

        List<Phone> phoneList = phoneRepository.findByColor(color);

        if (phoneList == null) {
            LOG.debug("Mo matches found for color: " + color);
        }

        return phoneList;
    }

    @Override
    public List<Phone> getPhonesByPriceRange(String indicator, int min, int max) {



        return null;
    }

    @Override
    public List<Phone> getPhonesByStorageRange(String indicator, int min, int max) {
        return null;
    }

    @Override
    public List<Phone> getPhones( String brand,
                                  String model,
                                  Integer storage,
                                  String color,
                                  Integer storageMin,
                                  Integer storageMax,
                                  String storageComp,
                                  Double price,
                                  Double priceMin,
                                  Double priceMax,
                                  String priceComp) {

        // Start building the criteria
        Criteria criteria = new Criteria();
        List<Sort.Order> sortingOrder = new ArrayList<>();

        // Add criteria for each provided parameter
        if (brand != null) {
            Pattern regexPattern = Pattern.compile(".*" + brand + ".*", Pattern.CASE_INSENSITIVE);
            criteria.and("brand").regex(regexPattern);
            sortingOrder.add(Sort.Order.asc("brand"));
        }

        if (model != null) {
            Pattern regexPattern = Pattern.compile(".*" + model + ".*", Pattern.CASE_INSENSITIVE);
            criteria.and("model").regex(regexPattern);
            sortingOrder.add(Sort.Order.asc("model"));
        }

        if (color != null) {
            Pattern regexPattern = Pattern.compile(".*" + color + ".*", Pattern.CASE_INSENSITIVE);
            criteria.and("color").regex(regexPattern);
            sortingOrder.add(Sort.Order.asc("color"));
        }

        if (storageComp != null) {
            if (storageComp.equals("between")) {
                if (storageMin != null && storageMax != null) {
                    criteria.and("storage").gte(storageMin).lte(storageMax);
                }
            } else if (storageComp.equals("gte")) {
                criteria.and("storage").gte(storage);
            } else if (storageComp.equals("lte")) {
                criteria.and("storage").lte(storage);
            } else {
                criteria.and("storage").is(storage);
            }

            sortingOrder.add(Sort.Order.asc("storage"));
        }

        if (priceComp != null) {
            if (priceComp.equals("between")) {
                if (priceMin != null && priceMax != null) {
                    criteria.and("price").gte(priceMin).lte(priceMax);
                }
            } else if (priceComp.equals("gte")) {
                criteria.and("price").gte(price);
            } else if (priceComp.equals("lte")) {
                criteria.and("price").lte(price);
            } else {
                criteria.and("price").is(price);
            }

            sortingOrder.add(Sort.Order.asc("price"));
        }

        // If brand was not part of the search param we can still sort on brand as the last sorting priority
        if (brand == null) {
            sortingOrder.add(Sort.Order.asc("brand"));
        }

        Query query = new Query(criteria);
        LOG.debug("Query: " + query);

        Sort sort = Sort.by(sortingOrder);
        query.with(sort);

        return mongoTemplate.find(query, Phone.class);
    }

    @Override
    public Phone update(Phone phone) {
        LOG.debug("Updating phone [{}]", phone);

        return phoneRepository.save(phone);
    }
}
