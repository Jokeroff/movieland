package com.lebediev.movieland.service.validations;

import com.lebediev.movieland.dao.jdbc.entity.OrderBy;
import com.lebediev.movieland.dao.jdbc.entity.SortDirection;
import com.lebediev.movieland.dao.jdbc.entity.SortParams;
import com.lebediev.movieland.service.conversion.Currency;

import java.util.Map;

import static com.lebediev.movieland.service.conversion.Currency.getCurrency;
import static com.lebediev.movieland.service.conversion.Currency.isValid;


public class MovieParamsValidator {


    public static SortParams isValidParams(Map <String, String> params) {
        if (params.size() > 1) {
            throw new IllegalArgumentException("To many parameters: " + params);
        } else if (params.isEmpty()) {
            return new SortParams();
        }

        for (Map.Entry <String, String> entrySet : params.entrySet()) {
            OrderBy orderBy = OrderBy.getOrderBy(entrySet.getKey());
            SortDirection sortDirection = SortDirection.getDirection(entrySet.getValue());

            if (orderBy == OrderBy.PRICE || orderBy == OrderBy.RATING && sortDirection == SortDirection.DESC) {
                return new SortParams(orderBy, sortDirection);
            }
        }
        throw new IllegalArgumentException("Invalid parameters: " + params);
    }

    public static Currency isValidParams(String currency){

        if(isValid(currency)){
            return getCurrency(currency);
        }
        throw new IllegalArgumentException("Wrong param: " + currency);
    }
}
