package NYCREATES.phoneStore;

import NYCREATES.phoneStore.dao.phoneRepository;
import NYCREATES.phoneStore.data.Phone;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

@Component
public class DataLoad {
    private static final String DATASTORE_LOCATION = "/static/cellphonedata.txt";

    @Autowired
    private phoneRepository phoneRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Phone phone = parsePhone(line);
                phoneRepository.insert(phone);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading data file", e);
        }
    }

    public Phone parsePhone(String line) {
        String[] tokens = line.split(",");

        Phone phone = new Phone();
        phone.setId(UUID.randomUUID().toString());
        phone.setBrand(tokens[0]);
        phone.setModel(tokens[1]);
        if (tokens[2].contains("GB")) {
            phone.setStorage(Integer.parseInt(tokens[2].replace(" GB", "")));
        } else if (tokens[2].contains("TB")) {
            phone.setStorage(Integer.parseInt(tokens[2].replace(" TB", "")) * 1024);
        }
        phone.setColor(tokens[3]);
        phone.setPrice(Double.parseDouble(tokens[4]));

        return phone;
    }
}
