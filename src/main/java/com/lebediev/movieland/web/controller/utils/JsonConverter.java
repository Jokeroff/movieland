package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.MovieViews;

import java.util.List;


public class JsonConverter {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public enum JsonView {
        BASE,
        EXTENDED,
        REVIEW
    }

    public static String toJson(List <MovieDto> movieDtoList, JsonView jsonView) {
        try {
            if (JsonView.BASE.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDtoList);
            } else if (JsonView.EXTENDED.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDtoList);
            } else if (JsonView.REVIEW.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.MovieWithReview.class).writeValueAsString(movieDtoList);
            }
            throw new IllegalArgumentException("Wrong parameters list.size(" + movieDtoList.size() + "), jsonView = " + jsonView);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJson(List <Genre> genreList) {
        try {
            return objectMapper.writeValueAsString(genreList);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJson(MovieDto movieDto, JsonView jsonView) {
        try {
            if (JsonView.BASE.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDto);
            } else if (JsonView.EXTENDED.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDto);
            } else if (JsonView.REVIEW.equals(jsonView)) {
                return objectMapper.writerWithView(MovieViews.MovieWithReview.class).writeValueAsString(movieDto);
            }
            throw new IllegalArgumentException("Wrong parameters (" + movieDto + "), jsonView = " + jsonView);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
