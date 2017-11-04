package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.MovieViews;

import java.util.List;


public class JsonConverter {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public enum JsonView {
        BASE,
        EXTENDED
    }

    public static String toJson(List <MovieDto> movieDtoList, JsonView jsonView) {
        try {
            if (JsonView.BASE.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDtoList);
            } else if (JsonView.EXTENDED.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDtoList);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
