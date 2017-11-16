package com.lebediev.movieland.service.conversion;


import com.lebediev.movieland.entity.Movie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import static org.mockito.Mockito.doReturn;


public class CurrencyConverterTest {

    @Spy
    private CachedCurrency cachedCurrency;
    @InjectMocks
    private CurrencyConverter currencyConverter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testConvertPrice(){
        CurrencyEntity currencyEntity = new CurrencyEntity(Currency.EUR, 30.44, LocalDate.now());
        doReturn(currencyEntity).when(cachedCurrency).getCurrencyEntity(Currency.EUR);
        Movie movie = new Movie();
        movie.setPrice(220);
        currencyConverter.convertPrice(movie, Currency.EUR);
        assertEquals(movie.getPrice(), 7.23, 0);
    }



}
