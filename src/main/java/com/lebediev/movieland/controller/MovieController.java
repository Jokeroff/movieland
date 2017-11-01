package com.lebediev.movieland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lebediev.movieland.controller.dto.movie.MovieDTO;
import com.lebediev.movieland.controller.dto.movie.MovieDTOConverter;
import com.lebediev.movieland.controller.utils.JsonConverter;
import com.lebediev.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value = "/v1", produces = "text/plain;charset=UTF-8")
public class MovieController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieDTOConverter movieDTOConverter;
    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping("/movie")
    @ResponseBody
    public String getAllMovies() throws JsonProcessingException {
        log.info("Start getting Json movie from controller");
        long startTime = System.currentTimeMillis();
        List <MovieDTO> moviesList = movieDTOConverter.toMovieDTOList(movieService.getAllMovies());
        log.info("Finish getting Json movie from controller. It took {} ms", System.currentTimeMillis() - startTime);
        return jsonConverter.toJson(moviesList);
    }

    @RequestMapping("/movie/random")
    @ResponseBody
    public String getRandomMovies() throws JsonProcessingException {
       // log.info("Start getting Json movie from controller");
      //  long startTime = System.currentTimeMillis();
         movieService.getRandomMovies();
     //   log.info("Finish getting Json movie from controller. It took {} ms", System.currentTimeMillis() - startTime);
        return movieService.getRandomMovies().toString();
    }
}
