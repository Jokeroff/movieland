package com.lebediev.movieland.service.validations;

import java.util.Map;

public class MovieParamsValidator {

    private enum validSortDirections {
        ASC,
        DESC
    }

    private enum validOrderBy {
        RATING,
        PRICE
    }

    public static Map<String, String> isValidParams(Map<String, String> params) {
        String orderBy = "";
        String sortDirection = "";

        for (Map.Entry<String, String> entrySet : params.entrySet()) {
            orderBy = String.valueOf(entrySet.getKey()).toUpperCase();
            sortDirection = String.valueOf(entrySet.getValue()).toUpperCase();
        }
        if (params.size() > 1) {
            throw new IllegalArgumentException("To many parameters: " + params.size());
        } else if (params.isEmpty()) {
            return params;
        } else if (orderBy.equals(validOrderBy.RATING.toString()) && sortDirection.equals(validSortDirections.DESC.toString())) {
            return params;
        } else if (orderBy.equals(validOrderBy.PRICE.toString()) &&
                (sortDirection.equals(validSortDirections.ASC.toString()) || sortDirection.equals(validSortDirections.DESC.toString()))) {
            return params;
        }
        throw new IllegalArgumentException("Invalid parameters: " + orderBy + " " + sortDirection);
    }
}
