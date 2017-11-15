package com.lebediev.movieland.service.conversion;

import com.lebediev.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyConverter {
    @Autowired
    CachedCurrency cachedCurrency;
    public Movie convertPrice(Movie movie, Currency currency){
        double rate = 1;
        List<CurrencyEntity> currencyEntityList = cachedCurrency.getAll();
         for(CurrencyEntity entity : currencyEntityList){
             if(entity.getCurrency() == currency){
                 rate = entity.getRate();
             }
         }
         movie.setPrice(movie.getPrice()/rate);
        return movie;
    }
}
