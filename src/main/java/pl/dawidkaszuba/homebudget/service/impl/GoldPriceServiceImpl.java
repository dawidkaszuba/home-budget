package pl.dawidkaszuba.homebudget.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.GoldPrice;
import pl.dawidkaszuba.homebudget.service.GoldPriceService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class GoldPriceServiceImpl implements GoldPriceService {

    private final ObjectMapper objectMapper;

    public GoldPriceServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<GoldPrice> getPrice() {
        try {
            return objectMapper.readValue(new URL("http://api.nbp.pl/api/cenyzlota"),
                    new TypeReference<List<GoldPrice>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
