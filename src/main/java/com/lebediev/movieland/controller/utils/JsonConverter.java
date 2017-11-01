package com.lebediev.movieland.controller.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lebediev.movieland.controller.dto.movie.MovieDTO;
import com.lebediev.movieland.controller.dto.movie.MovieViews;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(List<MovieDTO> movieDTOList) throws JsonProcessingException {
        return objectMapper.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDTOList);
    }
}
