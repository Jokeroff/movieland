package com.lebediev.movieland.web.controller.validations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static com.lebediev.movieland.web.controller.validations.MovieParamsValidator.isValidParams;
import static org.junit.Assert.assertEquals;

public class MovieParamsValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testIsValidParams() {
        Map<String, String> params = new HashMap<>();
        Map<String, String> actual;
        Map<String, String> expected = new HashMap<>();

        params.put("desc", null);
        actual = isValidParams(params);
        expected.put("rating", "desc");
        assertEquals(actual, expected);


        params.clear();
        expected.clear();
        params.put(null, "desc");
        actual = isValidParams(params);
        expected.put("price", "desc");
        assertEquals(actual, expected);

        params.clear();
        expected.clear();
        params.put(null, "asc");
        actual = isValidParams(params);
        expected.put("price", "asc");
        assertEquals(actual, expected);
    }

    @Test
    public void testIsValidParamsWithWrongParamsBoth() {
        Map<String, String> params = new HashMap<>();

        thrown.expect(IllegalArgumentException.class);
        params.put("both", "params");
        isValidParams(params);
    }

    @Test
    public void testIsValidParamsWithWrongParams() {
        Map<String, String> params = new HashMap<>();

        thrown.expect(IllegalArgumentException.class);
        params.put("asc", null);
        isValidParams(params);
    }

}
