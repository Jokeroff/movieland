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
    public enum JsonView{
        BASE,
        EXTENDED
    }

    public static String toJson(List <MovieDTO> movieDTOList, JsonView jsonView) throws JsonProcessingException {
        String json = "dummy json";
        if(jsonView.equals(jsonView.BASE)) {
            json = objectMapper.writerWithView(MovieViews.BaseMovie.class).writeValueAsString(movieDTOList);
        }
        else if(jsonView.equals(jsonView.EXTENDED)){
            json = objectMapper.writerWithView(MovieViews.ExtendedMovie.class).writeValueAsString(movieDTOList);
        }
        return json;
    }
}
