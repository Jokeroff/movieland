package com.lebediev.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.utils.JsonConverter;
import com.lebediev.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDtoList;


@Controller
@RequestMapping(value = "/v1/movie", produces = "text/plain;charset=UTF-8")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public String getAllMovies() throws JsonProcessingException {
        log.info("Start getting Json AllMovies from controller (v1/movie)");
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesList = toMovieDtoList(movieService.getAllMovies());
        String allMovies = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json AllMovies from controller (v1/movie). It took {} ms", System.currentTimeMillis() - startTime);
        return allMovies;
    }

    @RequestMapping(value="/random", method=RequestMethod.GET)
    @ResponseBody
    public String getRandomMovies() throws JsonProcessingException {
        log.info("Start getting Json RandomMovies from controller (v1/movie/random)");
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesList = toMovieDtoList(movieService.getRandomMovies());
        String allRandomMovies = toJson(moviesList, JsonConverter.JsonView.EXTENDED);
        log.info("Finish getting Json RandomMovies from controller (v1/movie/random). It took {} ms", System.currentTimeMillis() - startTime);
        return allRandomMovies;
    }

    @RequestMapping(value = "/genre/{genreId}")
    @ResponseBody
    public String getMoviesByGenreId(@PathVariable int genreId) throws JsonProcessingException{
        log.info("Start getting Json movies with genreId = " + genreId + " from controller (v1/movie/genre/{genreId})");
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesByGenreIdDTOList = toMovieDtoList(movieService.getMoviesByGenreId(genreId));
        String moviesByGenreId = toJson(moviesByGenreIdDTOList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json movies with genreId = " + genreId + " from controller (v1/movie/genre/{genreId}). It took {} ms", System.currentTimeMillis() - startTime);
        return moviesByGenreId;
    }
}
