package com.lebediev.movieland.service.conversion;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.time.LocalDate;

import static com.lebediev.movieland.service.conversion.CachedCurrency.getCurrenciesFromUrl;
import static org.junit.Assert.*;
import static com.lebediev.movieland.service.conversion.CachedCurrency.isExpired;

public class CachedCurrencyTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetCurrenciesFromUrl(){
        thrown.expect(RuntimeException.class);
        getCurrenciesFromUrl("wrong url");
    }

    @Test
    public void testIsExpired(){
        CurrencyEntity currencyEntity = new CurrencyEntity(Currency.EUR, 30.44, LocalDate.now());
        assertEquals(isExpired(currencyEntity), false);

        currencyEntity = new CurrencyEntity(Currency.EUR, 30.44, LocalDate.now().minusDays(3));
        assertEquals(isExpired(currencyEntity), true);

    }
}
