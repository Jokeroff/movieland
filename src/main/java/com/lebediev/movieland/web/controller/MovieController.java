package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.service.MovieService;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.utils.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDto;
import static com.lebediev.movieland.service.validations.MovieParamsValidator.isValidParams;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDtoList;


@Controller
@RequestMapping(value = "/movie", produces = "text/plain;charset=UTF-8")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllMovies(@RequestParam(required = false) Map <String, String> params) {
        log.info("Start getting Json all movies /movie with params: ", params);
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesList = toMovieDtoList(movieService.getAllMovies(isValidParams(params)));
        String allMovies = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json all movies /movie with params: {} . It took {} ms", params, System.currentTimeMillis() - startTime);
        return allMovies;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public String getRandomMovies() {
        log.info("Start getting Json random movies (/movie/random)");
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesList = toMovieDtoList(movieService.getRandomMovies());
        String allRandomMovies = toJson(moviesList, JsonConverter.JsonView.EXTENDED);
        log.info("Finish getting Json random movies (/movie/random). It took {} ms", System.currentTimeMillis() - startTime);
        return allRandomMovies;
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    @ResponseBody
    public String getMoviesByGenreId(@PathVariable int genreId,
                                     @RequestParam(required = false) Map <String, String> params) {
        log.info("Start getting Json movies by genreId ={} /movie/genre/{genreId} with params: ", genreId, params);
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesList = toMovieDtoList(movieService.getMoviesByGenreId(genreId, isValidParams(params)));
        String moviesByGenreId = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json movies with genreId ={} /movie/genre/{genreId} with params: {} . It took {} ms", genreId, params, System.currentTimeMillis() - startTime);
        return moviesByGenreId;
    }


    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    @ResponseBody
    public String getMovieById(@PathVariable int movieId) {
        log.info("Start getting Json movie by movieId ={} /movie/{movieId}: ", movieId);
        long startTime = System.currentTimeMillis();
        MovieDto movie = toMovieDto(movieService.getMovieById(movieId));
        String movieById = toJson(movie, JsonConverter.JsonView.REVIEW);
        log.info("Finish getting Json movie by movieId ={} /movie/{movieId}. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return movieById;
    }

}
