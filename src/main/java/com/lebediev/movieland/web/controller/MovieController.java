package com.lebediev.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.utils.JsonConverter;
import com.lebediev.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public String getAllMovies( @RequestParam (name = "rating", required = false) String rating,
                                @RequestParam (name = "price", required = false) String price
                                ) throws JsonProcessingException {
        log.info("Start getting Json all movies (v1/movie) with params [rating({}) || price({})]", price, rating );
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList;
        try {
            if (price == null && rating == null) {
                moviesList = toMovieDtoList(movieService.getAllMovies());
            } else if (price != null && (price.equals("asc") || price.equals("desc"))) {
                moviesList = toMovieDtoList(movieService.getAllMovies("price", price));
            } else if (rating != null && rating.equals("desc")) {
                moviesList = toMovieDtoList(movieService.getAllMovies("rating", rating));
            } else throw new IllegalArgumentException("Wrong arguments for method getAllMovies!");
        }
        catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }
        String allMovies = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json all movies (v1/movie) with params [rating({}) || price({})]. It took {} ms", price, rating, System.currentTimeMillis() - startTime);
        return allMovies;
    }

    @RequestMapping(value="/random", method=RequestMethod.GET)
    @ResponseBody
    public String getRandomMovies() throws JsonProcessingException {
        log.info("Start getting Json random movies (v1/movie/random)");
        long startTime = System.currentTimeMillis();
        List <MovieDto> moviesList = toMovieDtoList(movieService.getRandomMovies());
        String allRandomMovies = toJson(moviesList, JsonConverter.JsonView.EXTENDED);
        log.info("Finish getting Json random movies (v1/movie/random). It took {} ms", System.currentTimeMillis() - startTime);
        return allRandomMovies;
    }

    @RequestMapping(value = "/genre/{genreId}", method=RequestMethod.GET)
    @ResponseBody
    public String getMoviesByGenreId(@PathVariable int genreId,
                                     @RequestParam (name = "rating", required = false) String rating,
                                     @RequestParam (name = "price", required = false) String price) throws JsonProcessingException{
        log.info("Start getting Json movies by genreId ={} (v1/movie/genre/{genreId} with params [rating({}) || price({})]))", genreId, rating, price);
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList;
        try {
            if (price == null && rating == null) {
                moviesList = toMovieDtoList(movieService.getMoviesByGenreId(genreId));
            } else if (price != null && (price.equals("asc") || price.equals("desc"))) {
                moviesList = toMovieDtoList(movieService.getMoviesByGenreId(genreId,"price", price));
            } else if (rating != null && rating.equals("desc")) {
                moviesList = toMovieDtoList(movieService.getMoviesByGenreId(genreId,"rating", rating));
            } else throw new IllegalArgumentException("Wrong arguments for method getAllMovies!");
        }
        catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }
        String moviesByGenreId = toJson(moviesList, JsonConverter.JsonView.BASE);
        log.info("Finish getting Json movies with genreId ={} (v1/movie/genre/{genreId} with params [rating({}) || price({})]). It took {} ms",genreId, rating, price, System.currentTimeMillis() - startTime);
        return moviesByGenreId;
    }
}
