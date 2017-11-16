package com.lebediev.movieland.service.conversion;

import com.lebediev.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverter {
    private final static Logger log = LoggerFactory.getLogger(CurrencyConverter.class);
    @Autowired
    private CachedCurrency cachedCurrency;

    public Movie convertPrice(Movie movie, Currency currency){
        log.info("Start converting price for movie with id: {} for currency: {}", movie.getMovieId(), currency);
        long startTime = System.currentTimeMillis();
        CurrencyEntity currencyEntity = cachedCurrency.getCurrencyEntity(currency);
        double rate = currencyEntity.getRate();
        movie.setPrice(movie.getPrice()/rate);
        log.info("Finish converting price for movie with id: {} for currency: {}. It took {} ms",movie.getMovieId(), currency, System.currentTimeMillis() - startTime);
        return movie;
    }
}
