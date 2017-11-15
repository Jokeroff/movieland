package com.lebediev.movieland.service.conversion;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.lebediev.movieland.service.conversion.Currency.getCurrency;
import static com.lebediev.movieland.service.conversion.Currency.isValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CurrencyTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetCurrency(){
        assertEquals(getCurrency("usd"), Currency.USD);
        assertEquals(getCurrency("EuR"), Currency.EUR);

        thrown.expect(IllegalArgumentException.class);
                assertEquals(getCurrency("RUB"),Currency.USD);
    }

    @Test
    public void testIsValid(){
        assertTrue(isValid("EUR"));
        assertTrue(isValid("USD"));
        assertFalse(isValid("UAH"));
    }
}
