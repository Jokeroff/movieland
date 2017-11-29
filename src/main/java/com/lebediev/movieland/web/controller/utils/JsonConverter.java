package com.lebediev.movieland.web.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.entity.Review;
import com.lebediev.movieland.service.authentication.AuthRequest;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;
import com.lebediev.movieland.web.controller.dto.MovieViews;
import com.lebediev.movieland.web.controller.dto.TokenDto;

import java.io.IOException;
import java.util.List;


public class JsonConverter {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public enum JsonView {
        BASE(MovieViews.BaseMovie.class),
        EXTENDED(MovieViews.ExtendedMovie.class),
        REVIEW(MovieViews.MovieWithReview.class);

        private final Class clazz;

        JsonView(Class clazz) {
            this.clazz = clazz;
        }

        public Class getClazz() {
            return clazz;
        }
    }

    public static String toJson(List <MovieDto> movieDtoList, JsonView jsonView) {
        try {
            return OBJECT_MAPPER.writerWithView(jsonView.getClazz()).writeValueAsString(movieDtoList);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in json ", e);
        }
    }

    public static String toJson(List <?> list) {
        try {
            return OBJECT_MAPPER.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in list", e);
        }
    }

    public static String toJson(MovieDto movieDto, JsonView jsonView) {
        try {
            return OBJECT_MAPPER.writerWithView(jsonView.getClazz()).writeValueAsString(movieDto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in json ", e);
        }
    }

    public static String toJson(TokenDto tokenDto) {
        try {
            return OBJECT_MAPPER.writeValueAsString(tokenDto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error in TokenDto " + tokenDto);
        }
    }

    public static AuthRequest toAuth(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, AuthRequest.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create AuthRequest from json " + e);
        }
    }

    public static Review toReview(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Review.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Review from json: " + json);
        }
    }


    public static String toJson(String value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not create json from string: " + value);
        }
    }

    public static Movie toMovie(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Movie.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Movie from json: " + json);
        }
    }

    public static MovieDtoForUpdate toMovieDtoForUpdate(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, MovieDtoForUpdate.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create MovieDtoForUpdate from json: " + json);
        }
    }
}
