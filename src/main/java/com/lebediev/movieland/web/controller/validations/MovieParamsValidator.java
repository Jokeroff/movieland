package com.lebediev.movieland.web.controller.validations;

import java.util.Map;
import java.util.Set;

public class MovieParamsValidator {

    private enum validSortDirections {
        ASC,
        DESC
    }

    private static String ratingSortDirection;
    private static String priceSortDirection;


    public static Map<String, String> isValidParams(Map<String, String> params) {
        Set<Map.Entry<String, String>> set = params.entrySet();
        for (Map.Entry<String, String> entrySet : set) {
            ratingSortDirection = entrySet.getKey();
            priceSortDirection = entrySet.getValue();
        }
        if (ratingSortDirection == null && priceSortDirection == null) {
            return params;
        } else if (ratingSortDirection != null && priceSortDirection != null) {
            throw new IllegalArgumentException();
        } else if (ratingSortDirection != null && ratingSortDirection.toUpperCase().equals(validSortDirections.DESC.toString())) {
            params.clear();
            params.put("rating", "desc");
            return params;
        } else if (priceSortDirection != null
                && (priceSortDirection.toUpperCase().equals(validSortDirections.DESC.toString()) || priceSortDirection.toUpperCase().equals(validSortDirections.ASC.toString()))) {
            params.clear();
            params.put("price", priceSortDirection);
            return params;
        } else throw new IllegalArgumentException();
    }
}
