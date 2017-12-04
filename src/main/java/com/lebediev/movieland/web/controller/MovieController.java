package com.lebediev.movieland.web.controller;

import com.lebediev.movieland.dao.jdbc.entity.MovieRating;
import com.lebediev.movieland.dao.jdbc.entity.Role;
import com.lebediev.movieland.entity.Movie;
import com.lebediev.movieland.service.MovieService;
import com.lebediev.movieland.service.authentication.AuthService;
import com.lebediev.movieland.service.conversion.CurrencyConverter;
import com.lebediev.movieland.web.controller.dto.MovieDto;
import com.lebediev.movieland.web.controller.dto.MovieDtoForUpdate;
import com.lebediev.movieland.web.controller.utils.JsonConverter;
import com.lebediev.movieland.web.controller.utils.RoleRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.lebediev.movieland.web.controller.utils.JsonConverter.toJson;
import static com.lebediev.movieland.web.controller.utils.JsonConverter.toMovieDtoForUpdate;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDto;
import static com.lebediev.movieland.service.validations.MovieParamsValidator.isValidParams;
import static com.lebediev.movieland.web.controller.utils.MovieDtoConverter.toMovieDtoList;


@Controller
@RequestMapping(value = "/movie", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MovieController {
    private static final Logger LOG = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;
    @Autowired
    private CurrencyConverter currencyConverter;
    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllMovies(@RequestParam(required = false) Map<String, String> params) {
        LOG.info("Start getting Json all movies /movie with params: ", params);
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList = toMovieDtoList(movieService.getAllMovies(isValidParams(params)));
        String allMovies = toJson(moviesList, JsonConverter.JsonView.BASE);
        LOG.info("Finish getting Json all movies /movie with params: {} . It took {} ms", params, System.currentTimeMillis() - startTime);
        return allMovies;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public String getRandomMovies() {
        LOG.info("Start getting Json random movies (/movie/random)");
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList = toMovieDtoList(movieService.getRandomMovies());
        String allRandomMovies = toJson(moviesList, JsonConverter.JsonView.EXTENDED);
        LOG.info("Finish getting Json random movies (/movie/random). It took {} ms", System.currentTimeMillis() - startTime);
        return allRandomMovies;
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    @ResponseBody
    public String getMoviesByGenreId(@PathVariable int genreId,
                                     @RequestParam(required = false) Map<String, String> params) {
        LOG.info("Start getting Json movies by genreId ={} /movie/genre/{genreId} with params: ", genreId, params);
        long startTime = System.currentTimeMillis();
        List<MovieDto> moviesList = toMovieDtoList(movieService.getMoviesByGenreId(genreId, isValidParams(params)));
        String moviesByGenreId = toJson(moviesList, JsonConverter.JsonView.BASE);
        LOG.info("Finish getting Json movies with genreId ={} /movie/genre/{genreId} with params: {} . It took {} ms", genreId, params, System.currentTimeMillis() - startTime);
        return moviesByGenreId;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getMovieById(@PathVariable int id,
                               @RequestParam(value = "currency", required = false) String currency) {
        LOG.info("Start getting Json movie by id ={} /movie/{id}: ", id);
        long startTime = System.currentTimeMillis();
        Movie movie = movieService.getMovieById(id);
        if (currency != null) {
            movie = currencyConverter.convertPrice(movie, isValidParams(currency));
        }
        String movieById = toJson(toMovieDto(movie), JsonConverter.JsonView.REVIEW);
        LOG.info("Finish getting Json movie by id ={} /movie/{id}. It took {} ms", id, System.currentTimeMillis() - startTime);
        return movieById;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @RoleRequired(role = Role.ADMIN)
    public void add(@RequestBody String json) {
        LOG.info("Start saving movie");

        MovieDtoForUpdate movie = toMovieDtoForUpdate(json);
        movieService.add(movie);

        LOG.info("Finish saving movie");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @RoleRequired(role = Role.ADMIN)
    public void update(@PathVariable int id,
                       @RequestBody String json) {
        LOG.info("Start updating movie from /movie/{}", id);

        MovieDtoForUpdate movie = toMovieDtoForUpdate(json);
        movie.setId(id);
        movieService.update(movie);

        LOG.info("Finish updating movie from /movie/{}", id);
    }

    @RequestMapping(value = "/{movieId}/rating", method = RequestMethod.POST)
    @ResponseBody
    @RoleRequired(role = Role.USER)
    public void addRating(@PathVariable int movieId,
                          @RequestBody MovieRating movieRating) {
        LOG.info("Start adding rating for movieId = {}", movieId);

        movieRating.setMovieId(movieId);
        movieRating.setUserId(authService.getCurrentUser().getUserId());
        movieService.addRating(isValidParams(movieRating));

        LOG.info("Finish adding rating for movieId = {}", movieId);
    }

    @RequestMapping(value = "/{movieId}/rating", method = RequestMethod.GET)
    @ResponseBody
    public String getRating(@PathVariable int movieId) {
        LOG.info("Start getting rating for movie id = {} /movie/{movieId}/rating", movieId);
        long startTime = System.currentTimeMillis();

        Double rating = movieService.getRating(movieId);

        LOG.info("Finish getting rating for movie id = {} /movie/{movieId}/rating. It took {} ms", movieId, System.currentTimeMillis() - startTime);
        return rating.toString();
    }
}
