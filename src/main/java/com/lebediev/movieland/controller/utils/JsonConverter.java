package com.lebediev.movieland.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lebediev.movieland.controller.dto.MovieDto;
import com.lebediev.movieland.controller.dto.MovieViews;

import java.util.List;


public class JsonConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public enum JsonView {
        BASE,
        EXTENDED
    }

    public static String toJson(List <MovieDto> movieDtoList, JsonView jsonView) {
        try {
            if (jsonView.equals(jsonView.BASE)) {
                return objectMapper.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDtoList);
            } else if (jsonView.equals(jsonView.EXTENDED)) {
                return objectMapper.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDtoList);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
