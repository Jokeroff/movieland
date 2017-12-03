package com.lebediev.movieland.service.validations;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import com.lebediev.movieland.dao.jdbc.entity.OrderBy;
import com.lebediev.movieland.dao.jdbc.entity.SortDirection;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.service.conversion.Currency;
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

        SortParams expected = new SortParams(OrderBy.PRICE, SortDirection.ASC);

        params.put("price", "asc");
        assertEquals(isValidParams(params).getOrderBy(), expected.getOrderBy());
        assertEquals(isValidParams(params).getSortDirection(), expected.getSortDirection());

        expected = new SortParams(OrderBy.PRICE, SortDirection.DESC);

        params.clear();
        params.put("pRice", "desC");
        assertEquals(isValidParams(params).getOrderBy(), expected.getOrderBy());
        assertEquals(isValidParams(params).getSortDirection(), expected.getSortDirection());

        expected = new SortParams(OrderBy.RATING, SortDirection.DESC);

        params.clear();
        params.put("rating", "DESC");
        assertEquals(isValidParams(params).getOrderBy(), expected.getOrderBy());
        assertEquals(isValidParams(params).getSortDirection(), expected.getSortDirection());

        expected = new SortParams();
        params.clear();
        assertEquals(isValidParams(params).getOrderBy(), expected.getOrderBy());
        assertEquals(isValidParams(params).getSortDirection(), expected.getSortDirection());
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

    @Test
    public void testIsValidParamsForCurrency() {
        assertEquals(isValidParams("usd"), Currency.USD);
        assertEquals(isValidParams("EuR"), Currency.EUR);

        thrown.expect(IllegalArgumentException.class);
        isValidParams("ABC");
    }

    @Test
    public void testIsValidParamsForMovieRatingDto() {
        MovieRating dto = new MovieRating();
        dto.setRating(5.2);
        assertEquals(isValidParams(dto), dto);
    }

    @Test
    public void testIsValidParamsForMovieRatingDtoEmpty() {
        MovieRating dto = new MovieRating();
        thrown.expect(IllegalArgumentException.class);
        isValidParams(dto);
    }

    @Test
    public void testIsValidParamsForMovieRatingDtoOutOfRange() {
        MovieRating dto = new MovieRating();
        thrown.expect(IllegalArgumentException.class);
        dto.setRating(-2);
        isValidParams(dto);
    }


}
