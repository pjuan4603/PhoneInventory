package NYCREATES.phoneStore.controller;

import NYCREATES.phoneStore.data.Phone;
import NYCREATES.phoneStore.service.phoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class phoneController {
    private static final Logger LOG = LoggerFactory.getLogger(phoneController.class);

    @Autowired
    private phoneService phoneService;

    @PostMapping("/phone/create")
    public Phone create(@RequestBody Phone phone) {
        LOG.debug("Received employee create request for [{}]", phone);

        return phoneService.create(phone);
    }

    @GetMapping("/phone/{brand}")
    public List<Phone> getPhonesByBrand(@PathVariable String brand) {
        LOG.debug("Received phone get request for brand [{}]", brand);

        return phoneService.getPhonesByBrand(brand, "desc");
    }

    @GetMapping("/phones")
    public ResponseEntity<?> filterPhones(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer storage,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer storageMin,
            @RequestParam(required = false) Integer storageMax,
            @RequestParam(required = false) String storageComp,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String priceComp) {

        LOG.debug("Received phone filtering request with brand [{}], model [{}], storage [{}], color [{}]", brand, model, storage, color);

        if ( (storage != null && storage < 0)
                || (storageMin != null && storageMin < 0)
                || (storageMax != null && storageMax < 0)
                || (storageMax != null && storageMin != null && storageMin > storageMax)
                || (storageMax == null || storageMin == null) && (storageComp!= null && storageComp.equals("between")) ) {
            return ResponseEntity.badRequest().body("Invalid Input within 'storage', 'storageMin' and 'storageMax'. ");
        }

        if ( (price != null && price < 0)
                || (priceMin != null && priceMin < 0)
                || (priceMax != null && priceMax < 0)
                || (priceMax != null && priceMin != null && priceMin > priceMax)
                || (priceComp != null && priceComp.equals("between") && (priceMax == null || priceMin == null) ) ) {
            return ResponseEntity.badRequest().body("Invalid Input within 'price', 'priceMin' and 'priceMax'. ");
        }

        List<Phone> filteredPhones = phoneService.getPhones(brand, model, storage, color, storageMin, storageMax, storageComp, price, priceMin, priceMax, priceComp);

        return ResponseEntity.ok(filteredPhones);
    }


    @PutMapping("/phone/{id}")
    public Phone update(@PathVariable String id, @RequestBody Phone phone) {
        LOG.debug("Received phone update request for id [{}]", id);

        phone.setId(id);
        return phoneService.update(phone);
    }

}
