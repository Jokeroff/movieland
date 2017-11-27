package com.lebediev.movieland.service.conversion;

import com.lebediev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.lebediev.movieland.service.conversion.CachedCurrency.isExpired;


@Service
public class CurrencyConverter {
    private final static Logger LOG = LoggerFactory.getLogger(CurrencyConverter.class);
    @Autowired
    private CachedCurrency cachedCurrency;

    public Movie convertPrice(Movie movie, Currency currency){
        LOG.info("Start converting price for movie with id: {} for currency: {}", movie.getId(), currency);
        long startTime = System.currentTimeMillis();
        CurrencyEntity currencyEntity = cachedCurrency.getCurrencyEntity(currency);
        if(isExpired(currencyEntity)){
            movie.setPrice(0);
            LOG.info("Finish converting price for movie with id: {} for currency: {}. Currency exchange date is expired: price set to 0. It took {} ms", movie.getId(), currency, System.currentTimeMillis() - startTime);
        return movie;
        }
        double price = movie.getPrice()/currencyEntity.getRate();
        BigDecimal priceRounded  = new BigDecimal(Double.toString(price));
        priceRounded = priceRounded.setScale(2, RoundingMode.HALF_UP);
        movie.setPrice(priceRounded.doubleValue());
        LOG.info("Finish converting price for movie with id: {} for currency: {}. It took {} ms", movie.getId(), currency, System.currentTimeMillis() - startTime);
        return movie;
    }
}
