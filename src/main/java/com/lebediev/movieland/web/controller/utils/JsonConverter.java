package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lebediev.movieland.entity.Genre;
import com.lebediev.movieland.service.authentication.AuthRequest;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.MovieViews;
import com.lebediev.movieland.web.controller.dto.TokenDto;

import java.io.IOException;
import java.util.List;


public class JsonConverter {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public enum JsonView {
        BASE,
        EXTENDED,
        REVIEW
    }

    public static String toJson(List<MovieDto> movieDtoList, JsonView jsonView) {
        try {
            if (JsonView.BASE.equals(jsonView)) {
                return OBJECT_MAPPER.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDtoList);
            } else if (JsonView.EXTENDED.equals(jsonView)) {
                return OBJECT_MAPPER.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDtoList);
            } else if (JsonView.REVIEW.equals(jsonView)) {
                return OBJECT_MAPPER.writerWithView(MovieViews.MovieWithReview.class).writeValueAsString(movieDtoList);
            }
            throw new IllegalArgumentException("Wrong parameters list.size(" + movieDtoList.size() + "), jsonView = " + jsonView);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJson(List<Genre> genreList) {
        try {
            return OBJECT_MAPPER.writeValueAsString(genreList);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in genre list");
        }
    }

    public static String toJson(MovieDto movieDto, JsonView jsonView) {
        try {
            if (JsonView.BASE.equals(jsonView)) {
                return OBJECT_MAPPER.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDto);
            } else if (JsonView.EXTENDED.equals(jsonView)) {
                return OBJECT_MAPPER.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDto);
            } else if (JsonView.REVIEW.equals(jsonView)) {
                return OBJECT_MAPPER.writerWithView(MovieViews.MovieWithReview.class).writeValueAsString(movieDto);
            }
            throw new IllegalArgumentException("Wrong parameters (" + movieDto + "), jsonView = " + jsonView);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String toJson(TokenDto tokenDto) {
        try {
            return OBJECT_MAPPER.writeValueAsString(tokenDto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in TokenDto " + tokenDto);
        }
    }

    public static AuthRequest getAuthFromJson(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, AuthRequest.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create AuthRequest from json " + e);
        }
    }

    public static String toJson(String value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not create json from string: " + value);
        }
    }
}
