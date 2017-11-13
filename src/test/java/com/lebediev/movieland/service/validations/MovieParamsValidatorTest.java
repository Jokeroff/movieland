package com.lebediev.movieland.service.validations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static com.lebediev.movieland.service.validations.MovieParamsValidator.isValidParams;
import static org.junit.Assert.assertEquals;

public class MovieParamsValidatorTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIsValidParams() {
        Map<String, String> params = new HashMap<>();

        params.put("rating", "DESC");
        assertEquals(isValidParams(params), params);

        params.clear();
        params.put("pRice", "desC");
        assertEquals(isValidParams(params), params);

        params.clear();
        params.put("price", "asc");
        assertEquals(isValidParams(params), params);

        params.clear();
        assertEquals(isValidParams(params), params);

    }

    @Test
    public void testIsValidParamsWithWrongParams() {
        Map<String, String> params = new HashMap<>();
        thrown.expect(IllegalArgumentException.class);
        params.put("wrong", "params");
        isValidParams(params);
    }

    @Test
    public void testIsValidParamsWithMultiParams() {
        Map<String, String> params = new HashMap<>();
        thrown.expect(IllegalArgumentException.class);
        params.put("some", "params");
        params.put("another", "param");
        isValidParams(params);
    }


}
