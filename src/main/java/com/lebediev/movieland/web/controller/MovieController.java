package com.lebediev.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.service.MovieService;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.utils.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDtoList;
import static com.lebediev.movieland.web.controller.validations.MovieParamsValidator.isValidParams;


@Controller
@RequestMapping(value = "/movie", produces = "text/plain;charset=UTF-8")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllMovies(@RequestParam(name = "rating", required = false) String ratingSortDirection,
                               @RequestParam(name = "price", required = false) String priceSortDirection) {
        log.info("Start getting Json all movies (/movie) with params [rating({}) || price({})]", ratingSortDirection, priceSortDirection);
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList;
        Map<String, String> params = new HashMap<>();
        params.put(ratingSortDirection, priceSortDirection);
        moviesList = toMovieDtoList(movieService.getAllMovies(isValidParams(params)));
        String allMovies = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json all movies (/movie) with params [rating({}) || price({})]. It took {} ms", ratingSortDirection, priceSortDirection, System.currentTimeMillis() - startTime);
        return allMovies;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public String getRandomMovies() throws JsonProcessingException {
        log.info("Start getting Json random movies (/movie/random)");
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList = toMovieDtoList(movieService.getRandomMovies());
        String allRandomMovies = toJson(moviesList, JsonConverter.JsonView.EXTENDED);
        log.info("Finish getting Json random movies (/movie/random). It took {} ms", System.currentTimeMillis() - startTime);
        return allRandomMovies;
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    @ResponseBody
    public String getMoviesByGenreId(@PathVariable int genreId,
                                     @RequestParam(name = "rating", required = false) String ratingSortDirection,
                                     @RequestParam(name = "price", required = false) String priceSortDirection) {
        log.info("Start getting Json movies by genreId ={} (/movie/genre/{genreId} with params [rating({}) || price({})]))", genreId, ratingSortDirection, priceSortDirection);
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList;
        Map<String, String> params = new HashMap<>();
        params.put(ratingSortDirection, priceSortDirection);
        moviesList = toMovieDtoList(movieService.getMoviesByGenreId(genreId, isValidParams(params)));
        String moviesByGenreId = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json movies with genreId ={} (/movie/genre/{genreId} with params [rating({}) || price({})]). It took {} ms", genreId, ratingSortDirection, priceSortDirection, System.currentTimeMillis() - startTime);
        return moviesByGenreId;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException() {
        log.info("IllegalArgumentException()");
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleJsonProcessingException() {
        log.info("JsonProcessingException()");
    }
}
